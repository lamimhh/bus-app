package com.example.data

import android.location.Location
import com.example.model.BottleneckZone
import com.example.model.BusConnectionState
import com.example.model.BusStatus
import com.example.model.BusStop
import com.example.model.Complaint
import com.example.model.CrewMember
import com.example.model.DelayInsight
import com.example.model.Notice
import com.example.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import kotlin.math.roundToInt

class BusRepository {

  companion object {
    const val OPERATOR_AUTH_SECRET = "SADA2026"
    const val GEOFENCE_RADIUS_METERS = 300f
  }

  // Official 18 Stops in strict sequential order from National University Gate to Campus
  private val official18Stops = listOf(
    BusStop(
      id = "stop_1_nu_gate",
      sequenceNumber = 1,
      name = "National University Gate",
      banglaName = "জাতীয় বিশ্ববিদ্যালয় গেট",
      landmark = "Main Gate N3 Highway Crossing",
      latitude = 23.9482,
      longitude = 90.3795,
      distanceFromStartKm = 0.0,
      typicalTransitMinFromPrev = 0,
      estimatedArrivalMin = 0,
      isPassed = true
    ),
    BusStop(
      id = "stop_2_board_bazar",
      sequenceNumber = 2,
      name = "Board Bazar",
      banglaName = "বোর্ড বাজার",
      landmark = "Opposite IUT Campus Intersection",
      latitude = 23.9215,
      longitude = 90.3842,
      distanceFromStartKm = 3.2,
      typicalTransitMinFromPrev = 6,
      estimatedArrivalMin = 0,
      isPassed = true
    ),
    BusStop(
      id = "stop_3_boro_bari",
      sequenceNumber = 3,
      name = "Boro Bari",
      banglaName = "বড় বাড়ি",
      landmark = "Near Boro Bari Highway Footover Bridge",
      latitude = 23.9080,
      longitude = 90.3875,
      distanceFromStartKm = 4.8,
      typicalTransitMinFromPrev = 4,
      estimatedArrivalMin = 0,
      isPassed = true
    ),
    BusStop(
      id = "stop_4_gazipura",
      sequenceNumber = 4,
      name = "Gazipura 27",
      banglaName = "গাজিপুরা ২৭",
      landmark = "27 Number Bus Stand & Traffic Point",
      latitude = 23.8965,
      longitude = 90.3920,
      distanceFromStartKm = 6.2,
      typicalTransitMinFromPrev = 4,
      estimatedArrivalMin = 0,
      isPassed = true
    ),
    BusStop(
      id = "stop_5_tongi_college",
      sequenceNumber = 5,
      name = "Tongi College Gate",
      banglaName = "টঙ্গী কলেজ গেট",
      landmark = "Govt. Tongi College Main Footover Bridge",
      latitude = 23.8872,
      longitude = 90.3985,
      distanceFromStartKm = 7.5,
      typicalTransitMinFromPrev = 4,
      estimatedArrivalMin = 2,
      isCurrent = true,
      isTarget = true
    ),
    BusStop(
      id = "stop_6_cherag_ali",
      sequenceNumber = 6,
      name = "Cherag Ali",
      banglaName = "চেরাগ আলী",
      landmark = "Cherag Ali Market & Bus Stop",
      latitude = 23.8805,
      longitude = 90.4005,
      distanceFromStartKm = 8.4,
      typicalTransitMinFromPrev = 3,
      estimatedArrivalMin = 6
    ),
    BusStop(
      id = "stop_7_station_road",
      sequenceNumber = 7,
      name = "Station Road",
      banglaName = "স্টেশন রোড",
      landmark = "Sena Kalyan Bhaban Intersection, Tongi",
      latitude = 23.8740,
      longitude = 90.4020,
      distanceFromStartKm = 9.3,
      typicalTransitMinFromPrev = 3,
      estimatedArrivalMin = 10
    ),
    BusStop(
      id = "stop_8_abdullahpur",
      sequenceNumber = 8,
      name = "Abdullahpur",
      banglaName = "আব্দুল্লাহপুর",
      landmark = "Polwel Carnation & Turag Bridge Entry",
      latitude = 23.8640,
      longitude = 90.4042,
      distanceFromStartKm = 10.8,
      typicalTransitMinFromPrev = 5,
      estimatedArrivalMin = 16,
      isNext = true
    ),
    BusStop(
      id = "stop_9_bns",
      sequenceNumber = 9,
      name = "BNS",
      banglaName = "বিএনএস",
      landmark = "BNS Center, Sector 7, Uttara",
      latitude = 23.8565,
      longitude = 90.4060,
      distanceFromStartKm = 11.8,
      typicalTransitMinFromPrev = 4,
      estimatedArrivalMin = 21
    ),
    BusStop(
      id = "stop_10_ajompur",
      sequenceNumber = 10,
      name = "Ajompur",
      banglaName = "আজমপুর",
      landmark = "Ajompur Bus Stop & Footover Bridge",
      latitude = 23.8505,
      longitude = 90.4075,
      distanceFromStartKm = 12.6,
      typicalTransitMinFromPrev = 3,
      estimatedArrivalMin = 26
    ),
    BusStop(
      id = "stop_11_rajlokkhi",
      sequenceNumber = 11,
      name = "Rajlokkhi",
      banglaName = "রাজলক্ষ্মী",
      landmark = "Rajlakshmi Complex, Sector 3, Uttara",
      latitude = 23.8445,
      longitude = 90.4090,
      distanceFromStartKm = 13.4,
      typicalTransitMinFromPrev = 3,
      estimatedArrivalMin = 30
    ),
    BusStop(
      id = "stop_12_joshimuddin",
      sequenceNumber = 12,
      name = "Joshimuddin",
      banglaName = "জসীমউদ্দীন",
      landmark = "Joshimuddin Road Crossing, Uttara",
      latitude = 23.8390,
      longitude = 90.4105,
      distanceFromStartKm = 14.1,
      typicalTransitMinFromPrev = 3,
      estimatedArrivalMin = 34
    ),
    BusStop(
      id = "stop_13_airport",
      sequenceNumber = 13,
      name = "Airport",
      banglaName = "বিমানবন্দর",
      landmark = "Hazrat Shahjalal International Airport Roundabout",
      latitude = 23.8260,
      longitude = 90.4140,
      distanceFromStartKm = 15.8,
      typicalTransitMinFromPrev = 5,
      estimatedArrivalMin = 40
    ),
    BusStop(
      id = "stop_14_kawla",
      sequenceNumber = 14,
      name = "Kawla",
      banglaName = "কাওলা",
      landmark = "Near Civil Aviation Headquarters",
      latitude = 23.8160,
      longitude = 90.4165,
      distanceFromStartKm = 17.1,
      typicalTransitMinFromPrev = 4,
      estimatedArrivalMin = 46
    ),
    BusStop(
      id = "stop_15_meredian",
      sequenceNumber = 15,
      name = "Meredian",
      banglaName = "মেরিডিয়ান",
      landmark = "Meredian / Le Meridien Bridge Ramp",
      latitude = 23.8080,
      longitude = 90.4180,
      distanceFromStartKm = 18.2,
      typicalTransitMinFromPrev = 4,
      estimatedArrivalMin = 52
    ),
    BusStop(
      id = "stop_16_khilkhet",
      sequenceNumber = 16,
      name = "Khilkhet",
      banglaName = "খিলক্ষেত",
      landmark = "Khilkhet Footover Bridge & Lake City Link",
      latitude = 23.7995,
      longitude = 90.4195,
      distanceFromStartKm = 19.3,
      typicalTransitMinFromPrev = 4,
      estimatedArrivalMin = 57
    ),
    BusStop(
      id = "stop_17_kuril",
      sequenceNumber = 17,
      name = "Kuril",
      banglaName = "কুড়িল",
      landmark = "Kuril Flyover Base & 300ft Road Link",
      latitude = 23.7890,
      longitude = 90.4220,
      distanceFromStartKm = 21.0,
      typicalTransitMinFromPrev = 5,
      estimatedArrivalMin = 64
    ),
    BusStop(
      id = "stop_18_campus",
      sequenceNumber = 18,
      name = "Campus",
      banglaName = "ক্যাম্পাস (কার্জন / নীলক্ষেত)",
      landmark = "Curzon Hall / Nilkhet / DU Campus Terminal",
      latitude = 23.7265,
      longitude = 90.3980,
      distanceFromStartKm = 32.5,
      typicalTransitMinFromPrev = 25,
      estimatedArrivalMin = 85
    )
  )

  private val _stops = MutableStateFlow(official18Stops)
  val stops: StateFlow<List<BusStop>> = _stops.asStateFlow()

  private val _userProfile = MutableStateFlow(UserProfile())
  val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

  private val _busStatus = MutableStateFlow(
    BusStatus(
      busId = "SS-01",
      busName = "SADA SHUNDORI",
      busBengaliName = "সাদা সুন্দরী",
      regNumber = "Dhaka Metro-Ba 15-8824",
      routeName = "National University ➔ Gazipur ➔ Tongi ➔ Campus",
      currentLatitude = 23.8872,
      currentLongitude = 90.3985,
      currentSpeedKmh = 48,
      previousStopName = "Gazipura 27",
      currentStopName = "Tongi",
      nextStopName = "Abdullahpur",
      currentStopIndex = 4,
      nextStopIndex = 5,
      distanceToNextKm = 3.3,
      etaToNextMinutes = 6,
      totalRouteDistanceKm = 32.5,
      completedDistanceKm = 7.5,
      journeyPercentage = 23,
      isOperatorTransmitting = false,
      connectionState = BusConnectionState.LIVE,
      lastUpdatedSecondsAgo = 1,
      lastUpdatedFormatted = "Just now",
      totalSeats = 52,
      onboardCount = 38,
      isUserOnboard = false,
      isBusFull = false,
      isElevatedRoute = false,
      serviceUpdateText = "Bus is running on schedule. Stay tuned for further updates.",
      serviceUpdateTimestamp = "08:15 AM",
      targetStopId = "stop_5_tongi_college"
    )
  )
  val busStatus: StateFlow<BusStatus> = _busStatus.asStateFlow()

  // Delay Insights & Bottlenecks
  private val _delayInsights = MutableStateFlow(
    listOf(
      DelayInsight("Sun", 15, 20, 82, "Moderate congestion near Airport flyover"),
      DelayInsight("Mon", 18, 26, 75, "Heavy weekly kick-off bottleneck at Tongi"),
      DelayInsight("Tue", 12, 18, 86, "Smooth flow with minor pause at Farmgate"),
      DelayInsight("Wed", 14, 21, 83, "Standard mid-week traffic patterns"),
      DelayInsight("Thu", 25, 34, 64, "Peak weekend departure rush hour congestion"),
      DelayInsight("Fri", 4, 7, 96, "Express transit for exams and library batches"),
      DelayInsight("Sat", 8, 12, 91, "Light traffic, punctual arrival at Curzon")
    )
  )
  val delayInsights: StateFlow<List<DelayInsight>> = _delayInsights.asStateFlow()

  private val _bottlenecks = MutableStateFlow(
    listOf(
      BottleneckZone("Tongi Bridge Chokepoint", "Turag River Bridge Entrance", 14, "07:20 AM - 08:35 AM", "High"),
      BottleneckZone("Airport Roundabout", "Terminal 1 Connector", 11, "08:00 AM - 09:10 AM", "Medium"),
      BottleneckZone("Mohakhali Flyover U-turn", "Amtoli to Kakoli Link", 10, "08:15 AM - 09:20 AM", "Medium"),
      BottleneckZone("Farmgate Metro Corridor", "Khamarbari turn to Tejgaon", 12, "08:30 AM - 09:40 AM", "High")
    )
  )
  val bottlenecks: StateFlow<List<BottleneckZone>> = _bottlenecks.asStateFlow()

  private val _notices = MutableStateFlow(
    listOf(
      Notice(
        id = "not_1",
        title = "Special Exam Schedule: DU 7 Colleges Honours Finals",
        category = "Schedule",
        date = "Today, 06:30 AM",
        summary = "SADA SHUNDORI departure operating with strict corridor punctuality for examinees.",
        fullBody = "Attention examinees of Dhaka College, Eden Mohila College, Titumir College, and other affiliated institutions. SADA SHUNDORI is operating on its exact morning schedule.",
        isUrgent = true,
        author = "Central Transport Sub-Committee"
      ),
      Notice(
        id = "not_2",
        title = "Elevated Expressway Routing Advisory",
        category = "Route",
        date = "Yesterday",
        summary = "When Elevated Route is active, passenger drop-offs between Airport and Farmgate use designated ramps.",
        fullBody = "Passengers boarding after Airport please monitor the Service Update card for real-time elevation alerts.",
        isUrgent = false,
        author = "Fleet Operations Desk"
      )
    )
  )
  val notices: StateFlow<List<Notice>> = _notices.asStateFlow()

  private val _complaints = MutableStateFlow(
    listOf(
      Complaint(
        id = "comp_101",
        category = "Lost Item",
        busNumber = "SS-01 (SADA SHUNDORI)",
        title = "Lost Calculator in Blue Pouch",
        description = "Left on window seat during the morning trip to Campus.",
        contactPhone = "+880 1712-345678",
        status = "Resolved",
        timestamp = "Oct 28, 11:20 AM",
        officialResponse = "Item recovered by Conductor Kalam Hossain! Handed over at terminal counter."
      )
    )
  )
  val complaints: StateFlow<List<Complaint>> = _complaints.asStateFlow()

  private val _crewMembers = MutableStateFlow(
    listOf(
      CrewMember(
        id = "crew_1",
        name = "Rahim Mia",
        role = "Lead Bus Captain (Driver)",
        designation = "Senior Transport Pilot",
        phone = "+880 1711-223344",
        experienceYears = 12,
        collegeAffiliation = "DU Central Transport Pool",
        rating = 4.9,
        bio = "Serving the National University to Campus route for over a decade with zero safety citations."
      ),
      CrewMember(
        id = "crew_2",
        name = "Kalam Hossain",
        role = "Bus Conductor & Manager",
        designation = "Fleet Attendant & Safety Helper",
        phone = "+880 1819-556677",
        experienceYears = 8,
        collegeAffiliation = "DU 7 Colleges Fleet Support",
        rating = 4.8,
        bio = "Manages boarding queues, student welfare, luggage safety, and onboard lost & found."
      ),
      CrewMember(
        id = "crew_3",
        name = "Shakil Ahmed",
        role = "Student Route Convener",
        designation = "Route Coordinator",
        phone = "+880 1612-445566",
        experienceYears = 3,
        collegeAffiliation = "Dhaka College (Dept of Political Science)",
        rating = 4.9,
        bio = "Coordinates timetables and communication between college principals and transport authority."
      )
    )
  )
  val crewMembers: StateFlow<List<CrewMember>> = _crewMembers.asStateFlow()

  // Track consecutive location readings to filter GPS noise
  private var consecutiveGeofenceHits = 0
  private var candidateStopIndex = -1

  /**
   * Process incoming GPS coordinates (from Operator Transmitter phone or Simulation).
   * Intelligently detects stops, updates progress percentage, speed, and ETA.
   */
  fun processLocationUpdate(latitude: Double, longitude: Double, speedKmh: Int, accuracyMeters: Float = 5.0f) {
    val stopsList = _stops.value
    var closestStopIndex = -1
    var minDistanceMeters = Float.MAX_VALUE

    val busLoc = Location("gps").apply {
      this.latitude = latitude
      this.longitude = longitude
    }

    for (i in stopsList.indices) {
      val stop = stopsList[i]
      val stopLoc = Location("stop").apply {
        this.latitude = stop.latitude
        this.longitude = stop.longitude
      }
      val dist = busLoc.distanceTo(stopLoc)
      if (dist < minDistanceMeters) {
        minDistanceMeters = dist
        closestStopIndex = i
      }
    }

    val currentStatus = _busStatus.value
    var newCurrentIndex = currentStatus.currentStopIndex

    // Check geofence entry with consecutive reading verification
    if (minDistanceMeters <= GEOFENCE_RADIUS_METERS && closestStopIndex >= currentStatus.currentStopIndex) {
      if (closestStopIndex == candidateStopIndex) {
        consecutiveGeofenceHits++
        if (consecutiveGeofenceHits >= 2) {
          newCurrentIndex = closestStopIndex
        }
      } else {
        candidateStopIndex = closestStopIndex
        consecutiveGeofenceHits = 1
      }
    }

    val prevIndex = (newCurrentIndex - 1).coerceAtLeast(0)
    val nextIndex = (newCurrentIndex + 1).coerceAtMost(stopsList.size - 1)

    val currentStop = stopsList[newCurrentIndex]
    val nextStop = stopsList[nextIndex]

    // Calculate actual distance along route
    val totalRouteKm = 32.5
    val completedKm = currentStop.distanceFromStartKm
    val journeyPct = ((completedKm / totalRouteKm) * 100).roundToInt().coerceIn(0, 100)

    val distToNextKm = (nextStop.distanceFromStartKm - completedKm).coerceAtLeast(0.3)
    val effectiveSpeed = if (speedKmh > 5) speedKmh else 30
    val etaMin = ((distToNextKm / effectiveSpeed) * 60).roundToInt().coerceAtLeast(1)

    // Update stops state
    _stops.update { list ->
      list.mapIndexed { idx, stop ->
        stop.copy(
          isPassed = idx < newCurrentIndex,
          isCurrent = idx == newCurrentIndex,
          isNext = idx == nextIndex
        )
      }
    }

    // Update bus telemetry state
    _busStatus.update { current ->
      current.copy(
        currentLatitude = latitude,
        currentLongitude = longitude,
        currentSpeedKmh = speedKmh,
        speedAccuracyMps = accuracyMeters,
        previousStopName = if (prevIndex != newCurrentIndex) stopsList[prevIndex].name else "National University",
        currentStopName = currentStop.name,
        nextStopName = if (newCurrentIndex < stopsList.size - 1) nextStop.name else "Campus Terminal",
        currentStopIndex = newCurrentIndex,
        nextStopIndex = nextIndex,
        distanceToNextKm = distToNextKm,
        etaToNextMinutes = etaMin,
        completedDistanceKm = completedKm,
        journeyPercentage = journeyPct,
        connectionState = BusConnectionState.LIVE,
        lastUpdatedSecondsAgo = 0,
        lastUpdatedFormatted = "Just now"
      )
    }
  }

  // Operator Verification and Live Transmission
  fun verifyOperatorPassword(password: String): Boolean {
    return password.trim() == OPERATOR_AUTH_SECRET
  }

  fun startOperatorTransmission(operatorPhone: String = "+880 1711-223344") {
    _busStatus.update { current ->
      current.copy(
        isOperatorTransmitting = true,
        operatorPhone = operatorPhone,
        connectionState = BusConnectionState.LIVE,
        lastUpdatedFormatted = "Broadcasting Live"
      )
    }
  }

  fun stopOperatorTransmission() {
    _busStatus.update { current ->
      current.copy(
        isOperatorTransmitting = false,
        lastUpdatedFormatted = "Shift Ended"
      )
    }
  }

  // Service Updates Management (Bus is full, Elevated Route)
  fun setBusFullStatus(isFull: Boolean) {
    _busStatus.update { current ->
      current.copy(
        isBusFull = isFull,
        serviceUpdateText = when {
          isFull && current.isElevatedRoute -> "BUS IS FULL & RUNNING ON ELEVATED EXPRESSWAY."
          isFull -> "BUS IS FULL: All 52 seats occupied. Boarding at next stops may be limited."
          current.isElevatedRoute -> "BUS ON ELEVATED EXPRESSWAY: Skipping local traffic from Airport ramp."
          else -> "Bus is running on schedule. Stay tuned for further updates."
        },
        serviceUpdateTimestamp = "Just now"
      )
    }
  }

  fun setElevatedRouteStatus(isElevated: Boolean) {
    _busStatus.update { current ->
      current.copy(
        isElevatedRoute = isElevated,
        serviceUpdateText = when {
          current.isBusFull && isElevated -> "BUS IS FULL & RUNNING ON ELEVATED EXPRESSWAY."
          isElevated -> "BUS ON ELEVATED ROUTE: Taking expressway to avoid traffic. Stops after Airport bypass local road."
          current.isBusFull -> "BUS IS FULL: All 52 seats occupied."
          else -> "Bus is running on schedule. Stay tuned for further updates."
        },
        serviceUpdateTimestamp = "Just now"
      )
    }
  }

  // Passenger Onboarding
  fun toggleOnboardStatus() {
    _busStatus.update { current ->
      val newOnboard = !current.isUserOnboard
      val newCount = if (newOnboard) {
        (current.onboardCount + 1).coerceAtMost(current.totalSeats)
      } else {
        (current.onboardCount - 1).coerceAtLeast(0)
      }
      current.copy(
        isUserOnboard = newOnboard,
        onboardCount = newCount
      )
    }
  }

  fun selectTargetStop(stopId: String) {
    _stops.update { list ->
      list.map { stop ->
        stop.copy(isTarget = stop.id == stopId)
      }
    }
    val target = _stops.value.find { it.id == stopId }
    if (target != null) {
      _busStatus.update { it.copy(targetStopId = stopId) }
      _userProfile.update { it.copy(favoriteStopId = stopId) }
    }
  }

  fun registerUser(fullName: String, department: String, session: String, email: String) {
    val shortName = fullName.trim().split(" ").firstOrNull()?.ifBlank { fullName.trim() } ?: fullName.trim()
    _userProfile.update { current ->
      current.copy(
        fullName = fullName.trim(),
        name = shortName,
        department = department.trim(),
        session = session.trim(),
        email = email.trim()
      )
    }
  }

  fun loginUser(email: String) {
    _userProfile.update { current ->
      current.copy(email = email.trim())
    }
  }

  fun updateSimulatedSpeed(delta: Int) {
    _busStatus.update { current ->
      val newSpeed = (current.currentSpeedKmh + delta).coerceIn(28, 62)
      current.copy(currentSpeedKmh = newSpeed)
    }
  }

  fun advanceToStopIndex(index: Int) {
    val stopsList = _stops.value
    val validIndex = index.coerceIn(0, stopsList.size - 1)
    val prevIndex = (validIndex - 1).coerceAtLeast(0)
    val nextIndex = (validIndex + 1).coerceAtMost(stopsList.size - 1)
    val currentStop = stopsList[validIndex]
    val nextStop = stopsList[nextIndex]

    val totalRouteKm = 32.5
    val completedKm = currentStop.distanceFromStartKm
    val journeyPct = ((completedKm / totalRouteKm) * 100).roundToInt().coerceIn(0, 100)
    val distToNextKm = (nextStop.distanceFromStartKm - completedKm).coerceAtLeast(0.5)
    val etaMin = ((distToNextKm / 42.0) * 60).roundToInt().coerceAtLeast(2)

    _stops.update { list ->
      list.mapIndexed { idx, stop ->
        stop.copy(
          isPassed = idx < validIndex,
          isCurrent = idx == validIndex,
          isNext = idx == nextIndex
        )
      }
    }

    _busStatus.update { current ->
      current.copy(
        currentLatitude = currentStop.latitude,
        currentLongitude = currentStop.longitude,
        previousStopName = if (validIndex > 0) stopsList[prevIndex].name else "National University Gate",
        currentStopName = currentStop.name,
        nextStopName = if (validIndex < stopsList.size - 1) nextStop.name else "Campus Terminal",
        currentStopIndex = validIndex,
        nextStopIndex = nextIndex,
        distanceToNextKm = distToNextKm,
        etaToNextMinutes = etaMin,
        completedDistanceKm = completedKm,
        journeyPercentage = journeyPct,
        lastUpdatedFormatted = "Just now"
      )
    }
  }

  fun submitComplaint(category: String, title: String, description: String, contactPhone: String, busNumber: String = "SS-01"): Complaint {
    val newComplaint = Complaint(
      id = "comp_${UUID.randomUUID().toString().take(6)}",
      category = category,
      busNumber = busNumber,
      title = title,
      description = description,
      contactPhone = contactPhone,
      status = "Submitted",
      timestamp = "Just now",
      officialResponse = "Acknowledged by Route Convener team. We are investigating."
    )
    _complaints.update { listOf(newComplaint) + it }
    return newComplaint
  }
}
