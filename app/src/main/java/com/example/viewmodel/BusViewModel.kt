package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BusRepository
import com.example.model.AppScreen
import com.example.model.BottleneckZone
import com.example.model.BusStatus
import com.example.model.BusStop
import com.example.model.CommunityChatMessage
import com.example.model.Complaint
import com.example.model.CrewMember
import com.example.model.DelayInsight
import com.example.model.Notice
import com.example.model.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BusViewModel(
  private val repository: BusRepository = BusRepository()
) : ViewModel() {

  private val _currentScreen = MutableStateFlow(AppScreen.DASHBOARD)
  val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

  private val _screenHistory = mutableListOf<AppScreen>()

  private val _isLoggedIn = MutableStateFlow(true)
  val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

  private val _pickupSearchQuery = MutableStateFlow("")
  val pickupSearchQuery: StateFlow<String> = _pickupSearchQuery.asStateFlow()

  private val _selectedNoticeCategory = MutableStateFlow("All")
  val selectedNoticeCategory: StateFlow<String> = _selectedNoticeCategory.asStateFlow()

  private val _selectedNoticeDetail = MutableStateFlow<Notice?>(null)
  val selectedNoticeDetail: StateFlow<Notice?> = _selectedNoticeDetail.asStateFlow()

  private val _isComplaintSheetOpen = MutableStateFlow(false)
  val isComplaintSheetOpen: StateFlow<Boolean> = _isComplaintSheetOpen.asStateFlow()

  private val _complaintSuccessMessage = MutableStateFlow<String?>(null)
  val complaintSuccessMessage: StateFlow<String?> = _complaintSuccessMessage.asStateFlow()

  private val _isEmergencyDialogOpen = MutableStateFlow(false)
  val isEmergencyDialogOpen: StateFlow<Boolean> = _isEmergencyDialogOpen.asStateFlow()

  // Dynamic live ticker seconds
  private val _countdownSeconds = MutableStateFlow(720) // 12 mins
  val countdownSeconds: StateFlow<Int> = _countdownSeconds.asStateFlow()

  // Dashboard modal and view states
  private val _isOperatorModalOpen = MutableStateFlow(false)
  val isOperatorModalOpen: StateFlow<Boolean> = _isOperatorModalOpen.asStateFlow()

  private val _operatorErrorMessage = MutableStateFlow<String?>(null)
  val operatorErrorMessage: StateFlow<String?> = _operatorErrorMessage.asStateFlow()

  private val _isViewAllStopsOpen = MutableStateFlow(false)
  val isViewAllStopsOpen: StateFlow<Boolean> = _isViewAllStopsOpen.asStateFlow()

  private val _isServiceUpdateSheetOpen = MutableStateFlow(false)
  val isServiceUpdateSheetOpen: StateFlow<Boolean> = _isServiceUpdateSheetOpen.asStateFlow()

  private val _isBoardingConfirmationOpen = MutableStateFlow(false)
  val isBoardingConfirmationOpen: StateFlow<Boolean> = _isBoardingConfirmationOpen.asStateFlow()

  private val _mapZoom = MutableStateFlow(1.0f)
  val mapZoom: StateFlow<Float> = _mapZoom.asStateFlow()

  // Finding nearest pickup state & recommended stop
  private val _isFindingNearestStop = MutableStateFlow(false)
  val isFindingNearestStop: StateFlow<Boolean> = _isFindingNearestStop.asStateFlow()

  private val _recommendedStopId = MutableStateFlow("stop_5_tongi_college")
  val recommendedStopId: StateFlow<String> = _recommendedStopId.asStateFlow()

  // Real-time Community Chat
  private val _communityChatMessages = MutableStateFlow<List<CommunityChatMessage>>(
    listOf(
      CommunityChatMessage(
        id = "chat_1",
        senderName = "Hafiz Lamim",
        senderCollege = "Dhaka College",
        message = "Bus Gazipura 27 cross korche, speed 48 km/h. Smooth run hocche.",
        timestamp = "07:22 AM",
        category = "Location"
      ),
      CommunityChatMessage(
        id = "chat_2",
        senderName = "Tanvir Ahmed",
        senderCollege = "Govt. Titumir College",
        message = "Tongi Bridge er age kono jam ache kina keu bolte parben?",
        timestamp = "07:24 AM",
        category = "Traffic"
      ),
      CommunityChatMessage(
        id = "chat_3",
        senderName = "Rahim Mia (Pilot)",
        senderCollege = "Sada Shundori Crew",
        message = "Bridge clear ache. InshaAllah 5 min er moddhe Tongi College Gate thakbo.",
        timestamp = "07:25 AM",
        category = "Location"
      ),
      CommunityChatMessage(
        id = "chat_4",
        senderName = "Sumaiya Khan",
        senderCollege = "Eden Mohila College",
        message = "Airport 3rd gate e kon pashe wait korbo? Overbridge er niche?",
        timestamp = "07:27 AM",
        category = "General"
      ),
      CommunityChatMessage(
        id = "chat_5",
        senderName = "Sayeed Hasan",
        senderCollege = "Dhaka College",
        message = "Hae overbridge er thik shamne bus stop e darao. Seat praay full, tobe standing space ache.",
        timestamp = "07:28 AM",
        category = "General"
      )
    )
  )
  val communityChatMessages: StateFlow<List<CommunityChatMessage>> = _communityChatMessages.asStateFlow()

  val userProfile: StateFlow<UserProfile> = repository.userProfile
  val busStatus: StateFlow<BusStatus> = repository.busStatus
  val allStops: StateFlow<List<BusStop>> = repository.stops
  val delayInsights: StateFlow<List<DelayInsight>> = repository.delayInsights
  val bottlenecks: StateFlow<List<BottleneckZone>> = repository.bottlenecks
  val allNotices: StateFlow<List<Notice>> = repository.notices
  val complaints: StateFlow<List<Complaint>> = repository.complaints
  val crewMembers: StateFlow<List<CrewMember>> = repository.crewMembers

  // Filtered stops based on search query
  val filteredStops: StateFlow<List<BusStop>> = combine(allStops, _pickupSearchQuery) { stops, query ->
    if (query.isBlank()) {
      stops
    } else {
      stops.filter {
        it.name.contains(query, ignoreCase = true) ||
        it.banglaName.contains(query, ignoreCase = true) ||
        it.landmark.contains(query, ignoreCase = true)
      }
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Filtered notices based on category
  val filteredNotices: StateFlow<List<Notice>> = combine(allNotices, _selectedNoticeCategory) { notices, category ->
    if (category == "All") notices else notices.filter { it.category.equals(category, ignoreCase = true) }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  init {
    // Live countdown simulation ticker
    viewModelScope.launch {
      while (true) {
        delay(1000)
        _countdownSeconds.update { current ->
          if (current > 5) current - 1 else 720
        }
      }
    }

    // Gentle speed variation simulation
    viewModelScope.launch {
      var speedDirection = 1
      while (true) {
        delay(4000)
        repository.updateSimulatedSpeed(speedDirection * 2)
        if (Math.random() > 0.6) speedDirection = -speedDirection
      }
    }
  }

  fun navigateTo(screen: AppScreen) {
    if (_currentScreen.value != screen) {
      _screenHistory.add(_currentScreen.value)
      _currentScreen.value = screen
    }
  }

  fun navigateBack(): Boolean {
    if (_screenHistory.isNotEmpty()) {
      _currentScreen.value = _screenHistory.removeAt(_screenHistory.lastIndex)
      return true
    }
    if (_currentScreen.value != AppScreen.DASHBOARD && _isLoggedIn.value) {
      _currentScreen.value = AppScreen.DASHBOARD
      return true
    }
    return false
  }

  fun login(asGuest: Boolean = false, email: String = "") {
    if (!asGuest && email.isNotBlank()) {
      repository.loginUser(email)
    }
    _isLoggedIn.value = true
    _screenHistory.clear()
    _currentScreen.value = AppScreen.DASHBOARD
  }

  fun registerAndLogin(
    fullName: String,
    department: String,
    session: String,
    email: String
  ) {
    repository.registerUser(
      fullName = fullName,
      department = department,
      session = session,
      email = email
    )
    _isLoggedIn.value = true
    _screenHistory.clear()
    _currentScreen.value = AppScreen.DASHBOARD
  }

  fun logout() {
    _isLoggedIn.value = false
    _screenHistory.clear()
    _currentScreen.value = AppScreen.WELCOME
  }

  fun toggleOnboard() {
    repository.toggleOnboardStatus()
  }

  fun selectStop(stopId: String) {
    repository.selectTargetStop(stopId)
    val target = allStops.value.find { it.id == stopId }
    if (target != null) {
      _countdownSeconds.value = target.estimatedArrivalMin * 60
    }
  }

  fun setPickupSearchQuery(query: String) {
    _pickupSearchQuery.value = query
  }

  fun setNoticeCategory(category: String) {
    _selectedNoticeCategory.value = category
  }

  fun openNoticeDetail(notice: Notice) {
    _selectedNoticeDetail.value = notice
  }

  fun closeNoticeDetail() {
    _selectedNoticeDetail.value = null
  }

  fun setComplaintSheetOpen(isOpen: Boolean) {
    _isComplaintSheetOpen.value = isOpen
    if (!isOpen) {
      _complaintSuccessMessage.value = null
    }
  }

  fun setEmergencyDialogOpen(isOpen: Boolean) {
    _isEmergencyDialogOpen.value = isOpen
  }

  fun submitComplaint(
    category: String,
    title: String,
    description: String,
    contactPhone: String
  ) {
    repository.submitComplaint(
      category = category,
      title = title,
      description = description,
      contactPhone = contactPhone
    )
    _complaintSuccessMessage.value = "Report logged! Route team will update your ticket within 2 hours."
  }

  fun formatEtaCountdown(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", mins, secs)
  }

  fun openOperatorModal() {
    _operatorErrorMessage.value = null
    _isOperatorModalOpen.value = true
  }

  fun closeOperatorModal() {
    _operatorErrorMessage.value = null
    _isOperatorModalOpen.value = false
  }

  fun verifyAndStartOperatorBroadcast(passcode: String, phone: String = "+880 1711-223344"): Boolean {
    val isValid = repository.verifyOperatorPassword(passcode)
    if (isValid) {
      repository.startOperatorTransmission(phone)
      _operatorErrorMessage.value = null
      _isOperatorModalOpen.value = false
      return true
    } else {
      _operatorErrorMessage.value = "Incorrect passcode. Enter official fleet key (SADA2026)."
      return false
    }
  }

  fun stopOperatorTransmission() {
    repository.stopOperatorTransmission()
  }

  fun setBusFull(isFull: Boolean) {
    repository.setBusFullStatus(isFull)
  }

  fun setElevatedRoute(isElevated: Boolean) {
    repository.setElevatedRouteStatus(isElevated)
  }

  fun advanceToNextStop() {
    val current = busStatus.value.currentStopIndex
    repository.advanceToStopIndex(current + 1)
  }

  fun rewindToPreviousStop() {
    val current = busStatus.value.currentStopIndex
    repository.advanceToStopIndex((current - 1).coerceAtLeast(0))
  }

  fun jumpToStop(index: Int) {
    repository.advanceToStopIndex(index)
  }

  fun setViewAllStopsOpen(isOpen: Boolean) {
    _isViewAllStopsOpen.value = isOpen
  }

  fun setServiceUpdateSheetOpen(isOpen: Boolean) {
    _isServiceUpdateSheetOpen.value = isOpen
  }

  fun setBoardingConfirmationOpen(isOpen: Boolean) {
    _isBoardingConfirmationOpen.value = isOpen
  }

  fun zoomIn() {
    _mapZoom.update { (it + 0.25f).coerceAtMost(2.5f) }
  }

  fun zoomOut() {
    _mapZoom.update { (it - 0.25f).coerceAtLeast(0.6f) }
  }

  fun recenterMap() {
    _mapZoom.value = 1.0f
  }

  fun processGpsReading(lat: Double, lng: Double, speedKmh: Int, accuracyMeters: Float = 5.0f) {
    repository.processLocationUpdate(lat, lng, speedKmh, accuracyMeters)
  }

  fun sendCommunityMessage(text: String, category: String = "Traffic") {
    if (text.isBlank()) return
    val user = userProfile.value
    val newMsg = CommunityChatMessage(
      id = "msg_${System.currentTimeMillis()}",
      senderName = user.name.ifBlank { "Lamim" },
      senderCollege = user.college.ifBlank { "Dhaka College" },
      message = text.trim(),
      timestamp = "Just now",
      isMe = true,
      category = category
    )
    _communityChatMessages.update { listOf(newMsg) + it }
  }

  fun selectRecommendedStop(stopId: String) {
    _recommendedStopId.value = stopId
    selectStop(stopId)
  }

  fun findNearestPickupPoint(userLat: Double? = null, userLon: Double? = null) {
    viewModelScope.launch {
      _isFindingNearestStop.value = true
      delay(750)
      val stops = allStops.value
      val target = if (userLat != null && userLon != null) {
        stops.minByOrNull { stop ->
          val dLat = stop.latitude - userLat
          val dLon = stop.longitude - userLon
          dLat * dLat + dLon * dLon
        } ?: stops.find { it.id == "stop_5_tongi_college" } ?: stops[4]
      } else {
        stops.find { it.id == "stop_5_tongi_college" } ?: stops.firstOrNull { !it.isPassed } ?: stops[4]
      }
      _recommendedStopId.value = target.id
      selectStop(target.id)
      _isFindingNearestStop.value = false
    }
  }
}
