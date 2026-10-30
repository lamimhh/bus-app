package com.example.ui.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Panorama
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.R
import com.example.model.AppScreen
import com.example.model.BusStatus
import com.example.model.BusStop
import com.example.model.CommunityChatMessage
import com.example.model.UserProfile
import com.example.viewmodel.BusViewModel

// Color palette strictly matching image 2 (3_a93872ed.jpeg) & light cream theme
private val BoardingBg = Color(0xFFFAF7F2)
private val BoardingBgEnd = Color(0xFFF3ECE4)
private val MaroonDeep = Color(0xFF4A0A12)
private val MaroonMid = Color(0xFF6B1120)
private val MaroonLight = Color(0xFF8B1E2F)
private val MaroonBadgeBg = Color(0xFFFCECEF)
private val GoldAccent = Color(0xFFC59B27)
private val TextDark = Color(0xFF1F1618)
private val TextGrey = Color(0xFF7A686D)
private val TextMutedLight = Color(0xFF9E8E92)
private val CardBorderColor = Color(0xFFEDE4DC)
private val RouteGreen = Color(0xFF10B981)
private val RouteRemaining = Color(0xFF7E0E1C)
private val RiverColor = Color(0xFFC7E2F2)
private val UserBlue = Color(0xFF2563EB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickupFinderScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val searchQuery by viewModel.pickupSearchQuery.collectAsState()
  val filteredStops by viewModel.filteredStops.collectAsState()
  val allStops by viewModel.allStops.collectAsState()
  val busStatus by viewModel.busStatus.collectAsState()
  val userProfile by viewModel.userProfile.collectAsState()
  val chatMessages by viewModel.communityChatMessages.collectAsState()
  val isFindingNearest by viewModel.isFindingNearestStop.collectAsState()
  val recommendedStopId by viewModel.recommendedStopId.collectAsState()
  val mapZoom by viewModel.mapZoom.collectAsState()

  var selectedZoneFilter by remember { mutableStateOf("All") }
  var isPanoramaModalOpen by remember { mutableStateOf(false) }
  var hasLocationPermission by remember {
    mutableStateOf(
      ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) == PackageManager.PERMISSION_GRANTED
    )
  }

  val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    hasLocationPermission = isGranted
    if (isGranted) {
      Toast.makeText(context, "Location permission granted. Finding nearest stop...", Toast.LENGTH_SHORT).show()
      viewModel.findNearestPickupPoint()
    } else {
      Toast.makeText(context, "Location permission denied.", Toast.LENGTH_SHORT).show()
    }
  }

  // Active Recommended Stop (defaults to Tongi College Gate or selected target)
  val recommendedStop = remember(allStops, recommendedStopId, busStatus.targetStopId) {
    allStops.find { it.id == recommendedStopId }
      ?: allStops.find { it.id == busStatus.targetStopId }
      ?: allStops.find { it.id == "stop_5_tongi_college" }
      ?: allStops[4]
  }

  // Zone filtered list of stops
  val zoneFilteredStops = remember(filteredStops, selectedZoneFilter) {
    when (selectedZoneFilter) {
      "Gazipur & Tongi" -> filteredStops.filter { it.distanceFromStartKm <= 15.0 }
      "Uttara & Airport" -> filteredStops.filter { it.distanceFromStartKm > 15.0 && it.distanceFromStartKm <= 26.0 }
      "Mohakhali & South" -> filteredStops.filter { it.distanceFromStartKm > 26.0 }
      else -> filteredStops
    }
  }

  // Pulsing animation for active live indicators
  val infiniteTransition = rememberInfiniteTransition(label = "RadarPulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.3f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(900, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseAlpha"
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(BoardingBg, BoardingBgEnd)
        )
      )
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // 1. BRAND HEADER (SADA SHUNDORI + Avatar Pill matching image 2)
      item {
        Spacer(modifier = Modifier.height(6.dp))
        BoardingHeader(
          userProfile = userProfile,
          onProfileClick = { viewModel.navigateTo(AppScreen.PROFILE) }
        )
      }

      // 2. SEARCH BAR & ZONE CHIPS
      item {
        BoardingSearchAndFilters(
          searchQuery = searchQuery,
          onSearchChange = { viewModel.setPickupSearchQuery(it) },
          selectedZone = selectedZoneFilter,
          onZoneSelected = { selectedZoneFilter = it }
        )
      }

      // 3. LIVE ROUTE VECTOR & RADAR PROGRESS BAR (Item 2 Requirement)
      item {
        LiveRouteRadarCard(
          busStatus = busStatus,
          pulseAlpha = pulseAlpha
        )
      }

      // 4. INTERACTIVE TRANSIT MAP (Canvas map with Turag river, routes & legend matching image 2)
      item {
        BoardingTransitMapCard(
          busStatus = busStatus,
          recommendedStop = recommendedStop,
          zoomLevel = mapZoom,
          onZoomIn = { viewModel.zoomIn() },
          onZoomOut = { viewModel.zoomOut() },
          onRecenter = { viewModel.recenterMap() }
        )
      }

      // 5. PROMINENT "FIND MY PICKUP POINT" BUTTON
      item {
        FindMyPickupButton(
          isFinding = isFindingNearest,
          onClick = {
            if (!hasLocationPermission) {
              permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
              viewModel.findNearestPickupPoint()
              Toast.makeText(context, "Nearest pickup located: ${recommendedStop.name}", Toast.LENGTH_SHORT).show()
            }
          }
        )
      }

      // 6. RECOMMENDED PICKUP POINT CARD (Distance, Walking ETA & Get Directions)
      item {
        RecommendedStopCard(
          stop = recommendedStop,
          onGetDirections = {
            openDirectionsIntent(context, recommendedStop)
          }
        )
      }

      // 7. BUS ARRIVAL ESTIMATE CARD (12 min, 07:38 AM, Current Position Tongi)
      item {
        BusArrivalEstimateCard(
          busStatus = busStatus,
          targetStop = recommendedStop
        )
      }

      // 8. 360° STREET VIEW / VIEW PANORAMA CARD
      item {
        StreetView360Card(
          stop = recommendedStop,
          onViewPanorama = {
            // Open real panorama intent with fallback to modal preview
            openStreetViewOrPreview(context, recommendedStop) {
              isPanoramaModalOpen = true
            }
          }
        )
      }

      // 9. OTHER STOPS ON THIS ROUTE (Horizontal scrolling cards)
      item {
        OtherStopsHorizontalSection(
          stops = zoneFilteredStops,
          selectedStopId = recommendedStop.id,
          onSelectStop = { stop ->
            viewModel.selectRecommendedStop(stop.id)
          },
          onViewAllClick = {
            viewModel.setViewAllStopsOpen(true)
          }
        )
      }

      // 10. LOCATION PERMISSION BANNER (Shown if permission is missing)
      if (!hasLocationPermission) {
        item {
          LocationAccessBanner(
            onAllowClick = {
              permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
          )
        }
      }

      // 11. LIVE COMMUNITY CHAT BOX (Real-time student chat & traffic updates)
      item {
        LiveCommunityChatCard(
          chatMessages = chatMessages,
          onSendMessage = { text, category ->
            viewModel.sendCommunityMessage(text, category)
          }
        )
      }

      // Space at bottom for floating bottom navigation bar
      item {
        Spacer(modifier = Modifier.height(96.dp))
      }
    }

    // 12. FLOATING BOTTOM NAVIGATION BAR (Live Location, Boarding [active], Insights)
    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .navigationBarsPadding()
    ) {
      BoardingBottomNavigation(
        activeScreen = AppScreen.BOARDING,
        onNavigate = { screen -> viewModel.navigateTo(screen) }
      )
    }

    // 360 PANORAMA INTERACTIVE MODAL DIALOG
    if (isPanoramaModalOpen) {
      PanoramaPreviewDialog(
        stop = recommendedStop,
        onDismiss = { isPanoramaModalOpen = false },
        onOpenGoogleMaps = {
          openDirectionsIntent(context, recommendedStop)
        }
      )
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 1. BRAND HEADER (SADA SHUNDORI + Avatar Capsule matching Image 2)
// -------------------------------------------------------------------------------------------------
@Composable
private fun BoardingHeader(
  userProfile: UserProfile,
  onProfileClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Left: Brand Logo + Typography
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.clickable { onProfileClick() }
    ) {
      Image(
        painter = painterResource(id = R.drawable.sada_shundori_icon),
        contentDescription = "Sada Shundori Logo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(46.dp)
          .clip(CircleShape)
          .border(1.5.dp, MaroonDeep, CircleShape)
      )
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(
          text = "SADA",
          fontFamily = FontFamily.Serif,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 2.sp,
          color = MaroonDeep,
          lineHeight = 16.sp
        )
        Text(
          text = "SHUNDORI",
          fontFamily = FontFamily.Serif,
          fontSize = 17.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 2.5.sp,
          color = MaroonDeep,
          lineHeight = 18.sp
        )
        Text(
          text = "LIVE BUS COMPANION",
          fontSize = 8.5.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.2.sp,
          color = TextGrey
        )
      }
    }

    // Right: Profile Avatar Capsule
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      shadowElevation = 2.dp,
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      modifier = Modifier
        .clickable { onProfileClick() }
        .testTag("boarding_profile_button")
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Person,
          contentDescription = "Profile",
          tint = MaroonDeep,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
          imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
          contentDescription = "Open Profile",
          tint = TextGrey,
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 2. SEARCH BAR & ZONE CHIPS
// -------------------------------------------------------------------------------------------------
@Composable
private fun BoardingSearchAndFilters(
  searchQuery: String,
  onSearchChange: (String) -> Unit,
  selectedZone: String,
  onZoneSelected: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    OutlinedTextField(
      value = searchQuery,
      onValueChange = onSearchChange,
      placeholder = { Text("Search stop, e.g. Tongi, Airport, Nilkhet...", fontSize = 13.sp, color = TextMutedLight) },
      singleLine = true,
      leadingIcon = {
        Icon(Icons.Default.Search, contentDescription = "Search", tint = MaroonDeep)
      },
      trailingIcon = {
        if (searchQuery.isNotEmpty()) {
          IconButton(onClick = { onSearchChange("") }) {
            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextGrey)
          }
        }
      },
      shape = RoundedCornerShape(14.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaroonDeep,
        unfocusedBorderColor = CardBorderColor,
        focusedTextColor = TextDark,
        unfocusedTextColor = TextDark,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
      ),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("boarding_search_field")
    )

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      val zones = listOf("All", "Gazipur & Tongi", "Uttara & Airport", "Mohakhali & South")
      items(zones) { zone ->
        val isSelected = selectedZone == zone
        Surface(
          color = if (isSelected) MaroonDeep else Color.White,
          shape = RoundedCornerShape(20.dp),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) MaroonDeep else CardBorderColor
          ),
          shadowElevation = if (isSelected) 2.dp else 1.dp,
          modifier = Modifier.clickable { onZoneSelected(zone) }
        ) {
          Text(
            text = zone,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else TextDark,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
          )
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 3. LIVE ROUTE VECTOR & RADAR CARD (Prompt requirement 2)
// -------------------------------------------------------------------------------------------------
@Composable
private fun LiveRouteRadarCard(
  busStatus: BusStatus,
  pulseAlpha: Float,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("boarding_route_radar_card")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Map,
            contentDescription = null,
            tint = MaroonDeep,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Live Route Vector & Radar",
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
        }
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = RouteGreen.copy(alpha = 0.12f)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .background(RouteGreen.copy(alpha = pulseAlpha), CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Bus Position: Near Tongi Bridge",
              fontSize = 10.sp,
              color = RouteGreen,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Canvas Linear Schematic Track
      Canvas(
        modifier = Modifier
          .fillMaxWidth()
          .height(34.dp)
      ) {
        val width = size.width
        val centerY = size.height / 2f
        val startX = 24f
        val endX = width - 24f
        val totalTrack = endX - startX

        // Background Track
        drawLine(
          color = Color(0xFFE5DDD5),
          start = Offset(startX, centerY),
          end = Offset(endX, centerY),
          strokeWidth = 6.dp.toPx(),
          cap = StrokeCap.Round
        )

        // Traveled Route (Green)
        val busProgressX = startX + totalTrack * 0.28f
        drawLine(
          color = RouteGreen,
          start = Offset(startX, centerY),
          end = Offset(busProgressX, centerY),
          strokeWidth = 6.dp.toPx(),
          cap = StrokeCap.Round
        )

        // Remaining Route (Maroon Dashed)
        drawLine(
          color = MaroonDeep.copy(alpha = 0.8f),
          start = Offset(busProgressX, centerY),
          end = Offset(endX, centerY),
          strokeWidth = 5.dp.toPx(),
          pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 8f), 0f)
        )

        // Major Stop Nodes
        val fractions = listOf(0.0f, 0.12f, 0.28f, 0.45f, 0.65f, 0.85f, 1.0f)
        fractions.forEachIndexed { idx, frac ->
          val nodeX = startX + totalTrack * frac
          val isBus = idx == 2
          val isPast = frac < 0.28f

          drawCircle(
            color = when {
              isBus -> MaroonDeep
              isPast -> RouteGreen
              else -> Color(0xFFBDACB0)
            },
            radius = if (isBus) 7.dp.toPx() else 3.5.dp.toPx(),
            center = Offset(nodeX, centerY)
          )

          if (isBus) {
            drawCircle(
              color = MaroonDeep.copy(alpha = 0.35f * pulseAlpha),
              radius = 15.dp.toPx(),
              center = Offset(nodeX, centerY)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(4.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = "Gazipur (0km)", fontSize = 9.sp, color = TextGrey)
        Text(text = "• Tongi (Bus Here)", fontSize = 9.5.sp, color = MaroonDeep, fontWeight = FontWeight.Bold)
        Text(text = "Campus (32.5km)", fontSize = 9.sp, color = TextGrey)
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 4. INTERACTIVE TRANSIT MAP (Canvas Map matching Image 2 reference)
// -------------------------------------------------------------------------------------------------
@Composable
private fun BoardingTransitMapCard(
  busStatus: BusStatus,
  recommendedStop: BusStop,
  zoomLevel: Float,
  onZoomIn: () -> Unit,
  onZoomOut: () -> Unit,
  onRecenter: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "MapPulse")
  val radarWave by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "radarWave"
  )

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    modifier = modifier
      .fillMaxWidth()
      .height(260.dp)
      .testTag("boarding_transit_map_card")
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      // 1. Vector Map Canvas
      Canvas(
        modifier = Modifier
          .fillMaxSize()
          .background(Color(0xFFF7F8FA))
      ) {
        val w = size.width
        val h = size.height

        // Road network grid (Soft grey lines)
        val roadColor = Color(0xFFE9ECF0)
        for (y in 20..h.toInt() step 45) {
          drawLine(roadColor, Offset(0f, y.toFloat()), Offset(w, y.toFloat()), 1.5f)
        }
        for (x in 30..w.toInt() step 55) {
          drawLine(roadColor, Offset(x.toFloat(), 0f), Offset(x.toFloat(), h), 1.5f)
        }

        // Turag River Flow (Gentle soft pastel blue curve)
        val riverPath = Path().apply {
          moveTo(w * 0.35f, 0f)
          cubicTo(
            w * 0.28f, h * 0.25f,
            w * 0.40f, h * 0.45f,
            w * 0.32f, h * 0.65f
          )
          cubicTo(
            w * 0.26f, h * 0.80f,
            w * 0.45f, h * 0.90f,
            w * 0.50f, h
          )
          lineTo(w * 0.56f, h)
          cubicTo(
            w * 0.51f, h * 0.90f,
            w * 0.32f, h * 0.80f,
            w * 0.38f, h * 0.65f
          )
          cubicTo(
            w * 0.46f, h * 0.45f,
            w * 0.34f, h * 0.25f,
            w * 0.41f, 0f
          )
          close()
        }
        drawPath(riverPath, RiverColor)

        // Main Transit Highway Route (Gazipur -> Tongi -> Airport -> Campus)
        val completedRoute = Path().apply {
          moveTo(w * 0.62f, h * 0.08f) // Gazipura
          lineTo(w * 0.54f, h * 0.24f) // Tongi
        }
        drawPath(
          path = completedRoute,
          color = RouteGreen,
          style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
        )

        val remainingRoute = Path().apply {
          moveTo(w * 0.54f, h * 0.24f) // Tongi
          lineTo(w * 0.60f, h * 0.40f)
          lineTo(w * 0.68f, h * 0.62f) // Abdullahpur
          lineTo(w * 0.74f, h * 0.85f) // Airport
          lineTo(w * 0.82f, h * 0.96f) // Campus
        }
        drawPath(
          path = remainingRoute,
          color = RouteRemaining,
          style = Stroke(
            width = 5.dp.toPx(),
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 10f), 0f)
          )
        )

        // Stop Nodes
        // 1. Gazipura 27 (Passed)
        drawCircle(RouteGreen, radius = 5.dp.toPx(), center = Offset(w * 0.62f, h * 0.08f))
        drawCircle(Color.White, radius = 2.5.dp.toPx(), center = Offset(w * 0.62f, h * 0.08f))

        // 2. Tongi (Current Bus Location)
        val busPos = Offset(w * 0.54f, h * 0.24f)
        drawCircle(MaroonDeep, radius = 10.dp.toPx(), center = busPos)
        drawCircle(Color.White, radius = 8.dp.toPx(), center = busPos)
        drawCircle(MaroonDeep, radius = 6.dp.toPx(), center = busPos)

        // 3. Abdullahpur (Next)
        val abdullahpurPos = Offset(w * 0.68f, h * 0.62f)
        drawCircle(RouteRemaining, radius = 5.dp.toPx(), center = abdullahpurPos)
        drawCircle(Color.White, radius = 2.5.dp.toPx(), center = abdullahpurPos)

        // 4. User Location (Blue Radar Circle near Airport/Uttara)
        val userPos = Offset(w * 0.46f, h * 0.35f)
        // Pulsing radar ripple
        drawCircle(
          color = UserBlue.copy(alpha = (1f - radarWave) * 0.4f),
          radius = (10.dp.toPx() + 25.dp.toPx() * radarWave),
          center = userPos
        )
        drawCircle(UserBlue, radius = 6.dp.toPx(), center = userPos)
        drawCircle(Color.White, radius = 2.5.dp.toPx(), center = userPos)

        // 5. Recommended Stop (Tongi College Gate / User Pickup Target)
        val recPos = Offset(w * 0.57f, h * 0.32f)
        drawCircle(GoldAccent, radius = 8.dp.toPx(), center = recPos)
        drawCircle(MaroonDeep, radius = 5.dp.toPx(), center = recPos)
      }

      // 2. Route Direction Capsule (Top-Left overlay matching image 2)
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        modifier = Modifier
          .padding(10.dp)
          .align(Alignment.TopStart)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = MaroonDeep,
            modifier = Modifier.size(15.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Uttara ➔ Gazipur",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
        }
      }

      // 3. Map Controls (Top-Right overlay: Recenter, +, - matching image 2)
      Column(
        modifier = Modifier
          .padding(10.dp)
          .align(Alignment.TopEnd),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Surface(
          shape = CircleShape,
          color = Color.White,
          shadowElevation = 3.dp,
          border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
          modifier = Modifier.size(34.dp).clickable { onRecenter() }
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.Default.MyLocation,
              contentDescription = "Recenter",
              tint = TextDark,
              modifier = Modifier.size(17.dp)
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(10.dp),
          color = Color.White,
          shadowElevation = 3.dp,
          border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor)
        ) {
          Column {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clickable { onZoomIn() },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Zoom In",
                tint = TextDark,
                modifier = Modifier.size(18.dp)
              )
            }
            Box(
              modifier = Modifier
                .width(34.dp)
                .height(1.dp)
                .background(CardBorderColor)
            )
            Box(
              modifier = Modifier
                .size(34.dp)
                .clickable { onZoomOut() },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Zoom Out",
                tint = TextDark,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }

      // 4. Map Labels (Gazipura 27, Tongi, Abdullahpur on map)
      Text(
        text = "Gazipura 27",
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = TextDark,
        modifier = Modifier
          .align(Alignment.TopCenter)
          .padding(top = 18.dp, start = 70.dp)
      )
      Text(
        text = "Tongi",
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = MaroonDeep,
        modifier = Modifier
          .align(Alignment.Center)
          .padding(bottom = 60.dp, start = 50.dp)
      )
      Text(
        text = "Abdullahpur",
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = TextDark,
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(bottom = 45.dp, end = 20.dp)
      )

      // 5. Bottom-Left Map Legend (Matching exact legend in reference image 2)
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White.copy(alpha = 0.94f),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, CardBorderColor),
        modifier = Modifier
          .align(Alignment.BottomStart)
          .padding(8.dp)
      ) {
        Column(
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
          verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
          LegendRowItem(color = RouteGreen, isLine = true, label = "Bus route (completed)")
          LegendRowItem(color = RouteRemaining, isLine = true, isDashed = true, label = "Bus route (remaining)")
          LegendRowItem(color = MaroonDeep, isBus = true, label = "Bus (current location)")
          LegendRowItem(color = RouteGreen, isDot = true, label = "Pickup point")
          LegendRowItem(color = UserBlue, isDot = true, label = "Your location")
          LegendRowItem(color = GoldAccent, isDiamond = true, label = "Recommended pickup")
        }
      }
    }
  }
}

@Composable
private fun LegendRowItem(
  color: Color,
  label: String,
  isLine: Boolean = false,
  isDashed: Boolean = false,
  isBus: Boolean = false,
  isDot: Boolean = false,
  isDiamond: Boolean = false
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Box(
      modifier = Modifier.size(12.dp),
      contentAlignment = Alignment.Center
    ) {
      when {
        isLine -> {
          Box(
            modifier = Modifier
              .width(10.dp)
              .height(3.dp)
              .background(color, RoundedCornerShape(1.dp))
          )
        }
        isBus -> {
          Box(
            modifier = Modifier
              .size(8.dp)
              .background(color, CircleShape)
          )
        }
        isDiamond -> {
          Box(
            modifier = Modifier
              .size(6.dp)
              .background(color, RoundedCornerShape(1.dp))
          )
        }
        else -> {
          Box(
            modifier = Modifier
              .size(6.dp)
              .background(color, CircleShape)
          )
        }
      }
    }
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = label,
      fontSize = 8.5.sp,
      color = TextDark,
      fontWeight = FontWeight.Medium
    )
  }
}

// -------------------------------------------------------------------------------------------------
// 5. PROMINENT "FIND MY PICKUP POINT" BUTTON (Matching Image 2)
// -------------------------------------------------------------------------------------------------
@Composable
private fun FindMyPickupButton(
  isFinding: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Surface(
      shape = RoundedCornerShape(26.dp),
      color = MaroonDeep,
      shadowElevation = 5.dp,
      modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .testTag("find_my_pickup_button")
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .background(Color.White.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = if (isFinding) "LOCATING NEAREST STOP..." else "FIND MY PICKUP POINT",
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
            color = Color.White
          )
        }

        if (isFinding) {
          CircularProgressIndicator(
            color = Color.White,
            strokeWidth = 2.dp,
            modifier = Modifier.size(20.dp)
          )
        } else {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Find",
            tint = Color.White,
            modifier = Modifier.size(22.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = "Allow location access to find the nearest pickup point.",
      fontSize = 11.sp,
      color = TextGrey,
      textAlign = TextAlign.Center
    )
  }
}

// -------------------------------------------------------------------------------------------------
// 6. RECOMMENDED STOP CARD (Matching Image 2)
// -------------------------------------------------------------------------------------------------
@Composable
private fun RecommendedStopCard(
  stop: BusStop,
  onGetDirections: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("recommended_pickup_card")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // Badge: Recommended Pickup Point
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaroonBadgeBg
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = MaroonDeep,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Recommended Pickup Point",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaroonDeep
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Thumbnail & Details
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Image(
            painter = painterResource(id = R.drawable.sada_shundori_art),
            contentDescription = "Stop Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .size(62.dp)
              .clip(RoundedCornerShape(12.dp))
              .border(1.dp, CardBorderColor, RoundedCornerShape(12.dp))
          )
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = stop.name,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = RouteRemaining,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "1.2 km away",
                fontSize = 12.sp,
                color = TextGrey,
                fontWeight = FontWeight.Medium
              )
              Spacer(modifier = Modifier.width(8.dp))
              Icon(
                imageVector = Icons.Default.Directions,
                contentDescription = null,
                tint = TextGrey,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "~ 16 min (walk)",
                fontSize = 12.sp,
                color = TextGrey,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }

        // Action: Get Directions
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .clickable { onGetDirections() }
            .padding(start = 8.dp)
            .testTag("btn_get_directions")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFF7F4F0),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
            modifier = Modifier.size(42.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.Navigation,
                contentDescription = "Get Directions",
                tint = MaroonDeep,
                modifier = Modifier.size(20.dp)
              )
            }
          }
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = "Get Directions",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
          )
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 7. BUS ARRIVAL ESTIMATE CARD (Matching Image 2)
// -------------------------------------------------------------------------------------------------
@Composable
private fun BusArrivalEstimateCard(
  busStatus: BusStatus,
  targetStop: BusStop,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("bus_arrival_estimate_card")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // Header: Bus icon + BUS ARRIVAL ESTIMATE >
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = CircleShape,
            color = MaroonDeep,
            modifier = Modifier.size(26.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.DirectionsBus,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(15.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "BUS ARRIVAL ESTIMATE",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
            color = TextDark
          )
        }
        Icon(
          imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
          contentDescription = null,
          tint = TextGrey,
          modifier = Modifier.size(18.dp)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // ETA Big Minutes
        Column {
          Text(
            text = "12 min",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = MaroonDeep,
            lineHeight = 30.sp
          )
          Text(
            text = "to reach this stop",
            fontSize = 11.sp,
            color = TextGrey,
            fontWeight = FontWeight.Medium
          )
        }

        // Expected Morning Arrival
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = null,
            tint = TextGrey,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Column {
            Text(
              text = "Expected arrival",
              fontSize = 10.sp,
              color = TextGrey
            )
            Text(
              text = "07:38 AM",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
          }
        }

        // Current Position & Last Update
        Column(horizontalAlignment = Alignment.End) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = null,
              tint = RouteRemaining,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Column {
              Text(text = "Current position", fontSize = 9.sp, color = TextGrey)
              Text(
                text = busStatus.currentStopName,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Schedule,
              contentDescription = null,
              tint = TextMutedLight,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Column {
              Text(text = "Last update", fontSize = 9.sp, color = TextGrey)
              Text(
                text = "2 min ago",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
              )
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 8. 360° STREET VIEW CARD (Matching Image 2)
// -------------------------------------------------------------------------------------------------
@Composable
private fun StreetView360Card(
  stop: BusStop,
  onViewPanorama: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("street_view_360_card")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // Badge: 360° VIEW
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaroonBadgeBg
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Icon(
            imageVector = Icons.Default.ViewInAr,
            contentDescription = null,
            tint = MaroonDeep,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "360° VIEW",
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Bold,
            color = MaroonDeep
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Thumbnail with 360 badge
        Box(
          modifier = Modifier
            .size(76.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onViewPanorama() }
        ) {
          Image(
            painter = painterResource(id = R.drawable.transit_wave_hero),
            contentDescription = "Stop 360 View",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = Color.Black.copy(alpha = 0.65f),
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(4.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Panorama,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(10.dp)
              )
              Spacer(modifier = Modifier.width(2.dp))
              Text(
                text = "360°",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            }
          }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Title, Subtitle and Action Button
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Explore 360° View",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Get a real look at the pickup point and nearby landmarks.",
            fontSize = 11.5.sp,
            color = TextGrey,
            lineHeight = 15.sp
          )
          Spacer(modifier = Modifier.height(8.dp))

          Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaroonDeep,
            modifier = Modifier
              .clickable { onViewPanorama() }
              .testTag("btn_view_panorama")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Panorama,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "View Panorama",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Spacer(modifier = Modifier.width(4.dp))
              Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
              )
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 9. OTHER STOPS ON THIS ROUTE (Matching Image 2)
// -------------------------------------------------------------------------------------------------
@Composable
private fun OtherStopsHorizontalSection(
  stops: List<BusStop>,
  selectedStopId: String,
  onSelectStop: (BusStop) -> Unit,
  onViewAllClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.ShareLocation,
          contentDescription = null,
          tint = MaroonDeep,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Other Stops on This Route",
          fontSize = 13.5.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark
        )
      }
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clickable { onViewAllClick() }
          .testTag("boarding_view_all_stops")
      ) {
        Text(
          text = "View all",
          fontSize = 11.5.sp,
          color = MaroonDeep,
          fontWeight = FontWeight.Bold
        )
        Icon(
          imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
          contentDescription = null,
          tint = MaroonDeep,
          modifier = Modifier.size(16.dp)
        )
      }
    }

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(stops, key = { it.id }) { stop ->
        val isSelected = stop.id == selectedStopId
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = if (isSelected) MaroonDeep else Color.White,
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) MaroonDeep else CardBorderColor
          ),
          shadowElevation = if (isSelected) 3.dp else 1.dp,
          modifier = Modifier
            .clickable { onSelectStop(stop) }
            .testTag("other_stop_${stop.id}")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .background(
                  if (isSelected) Color.White.copy(alpha = 0.2f) else Color(0xFFF7F4F0),
                  CircleShape
                ),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = if (isSelected) Color.White else MaroonDeep,
                modifier = Modifier.size(14.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = stop.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else TextDark
              )
              Text(
                text = "${stop.distanceFromStartKm} km • ${stop.estimatedArrivalMin} min",
                fontSize = 10.sp,
                color = if (isSelected) Color.White.copy(alpha = 0.8f) else TextGrey
              )
            }
            if (isSelected) {
              Spacer(modifier = Modifier.width(6.dp))
              Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 10. LOCATION ACCESS BANNER (Matching Image 2)
// -------------------------------------------------------------------------------------------------
@Composable
private fun LocationAccessBanner(
  onAllowClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Surface(
          shape = CircleShape,
          color = Color(0xFFFDE8E8),
          modifier = Modifier.size(30.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = MaroonDeep,
              modifier = Modifier.size(16.dp)
            )
          }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "No location access",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Text(
            text = "Enable location permission to see the nearest pickup point.",
            fontSize = 10.5.sp,
            color = TextGrey,
            lineHeight = 13.sp
          )
        }
      }

      Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaroonDeep,
        modifier = Modifier
          .clickable { onAllowClick() }
          .padding(start = 6.dp)
          .testTag("btn_allow_location_banner")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = "Allow Location",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
          Spacer(modifier = Modifier.width(3.dp))
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(14.dp)
          )
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 11. LIVE COMMUNITY CHAT BOX (Prompt requirement 6)
// -------------------------------------------------------------------------------------------------
@Composable
private fun LiveCommunityChatCard(
  chatMessages: List<CommunityChatMessage>,
  onSendMessage: (String, String) -> Unit,
  modifier: Modifier = Modifier
) {
  var chatInput by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf("All") }

  val filteredMessages = remember(chatMessages, selectedCategory) {
    if (selectedCategory == "All") chatMessages
    else chatMessages.filter { it.category.equals(selectedCategory, ignoreCase = true) }
  }

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("live_community_chat_card")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // Header: Live Community Chat
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = CircleShape,
            color = MaroonDeep,
            modifier = Modifier.size(28.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.ChatBubbleOutline,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "Student Transit Community",
              fontSize = 13.5.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
            Text(
              text = "Live traffic & location discussions",
              fontSize = 10.sp,
              color = TextGrey
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(10.dp),
          color = RouteGreen.copy(alpha = 0.12f)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Box(modifier = Modifier.size(6.dp).background(RouteGreen, CircleShape))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "42 Active",
              fontSize = 10.sp,
              color = RouteGreen,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Category Chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        val categories = listOf("All", "Traffic", "Location", "General")
        items(categories) { cat ->
          val isSel = selectedCategory == cat
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isSel) MaroonDeep else Color(0xFFF7F4F0),
            border = androidx.compose.foundation.BorderStroke(
              0.5.dp,
              if (isSel) MaroonDeep else CardBorderColor
            ),
            modifier = Modifier.clickable { selectedCategory = cat }
          ) {
            Text(
              text = cat,
              fontSize = 10.5.sp,
              fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
              color = if (isSel) Color.White else TextDark,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Message Feed List
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .height(210.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFFF9F7F4))
          .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        filteredMessages.take(4).forEach { msg ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (msg.isMe) Arrangement.End else Arrangement.Start
          ) {
            Surface(
              shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = if (msg.isMe) 12.dp else 2.dp,
                bottomEnd = if (msg.isMe) 2.dp else 12.dp
              ),
              color = if (msg.isMe) MaroonDeep else Color.White,
              border = androidx.compose.foundation.BorderStroke(
                0.5.dp,
                if (msg.isMe) MaroonDeep else CardBorderColor
              ),
              shadowElevation = 1.dp,
              modifier = Modifier.fillMaxWidth(0.92f)
            ) {
              Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = if (msg.isMe) "${msg.senderName} (You)" else msg.senderName,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (msg.isMe) GoldAccent else MaroonDeep
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "• ${msg.senderCollege}",
                      fontSize = 9.sp,
                      color = if (msg.isMe) Color.White.copy(alpha = 0.7f) else TextGrey
                    )
                  }
                  Text(
                    text = msg.timestamp,
                    fontSize = 9.sp,
                    color = if (msg.isMe) Color.White.copy(alpha = 0.7f) else TextMutedLight
                  )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = msg.message,
                  fontSize = 11.5.sp,
                  color = if (msg.isMe) Color.White else TextDark,
                  lineHeight = 15.sp
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Input field + Send button
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = chatInput,
          onValueChange = { chatInput = it },
          placeholder = { Text("Share traffic or location note...", fontSize = 12.sp, color = TextMutedLight) },
          singleLine = true,
          shape = RoundedCornerShape(20.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaroonDeep,
            unfocusedBorderColor = CardBorderColor,
            focusedTextColor = TextDark,
            unfocusedTextColor = TextDark,
            focusedContainerColor = Color(0xFFFAF7F2),
            unfocusedContainerColor = Color(0xFFFAF7F2)
          ),
          modifier = Modifier
            .weight(1f)
            .testTag("chat_input_field")
        )
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
          shape = CircleShape,
          color = MaroonDeep,
          shadowElevation = 3.dp,
          modifier = Modifier
            .size(44.dp)
            .clickable {
              if (chatInput.isNotBlank()) {
                onSendMessage(chatInput, "Traffic")
                chatInput = ""
              }
            }
            .testTag("chat_send_button")
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.Send,
              contentDescription = "Send",
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 12. FLOATING BOTTOM NAVIGATION BAR (Live Location, Boarding [active], Insights)
// -------------------------------------------------------------------------------------------------
@Composable
private fun BoardingBottomNavigation(
  activeScreen: AppScreen,
  onNavigate: (AppScreen) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    color = Color.White,
    shadowElevation = 12.dp,
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // 1. Live Location Tab (Inactive)
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .clickable { onNavigate(AppScreen.DASHBOARD) }
          .padding(horizontal = 8.dp, vertical = 4.dp)
          .testTag("boarding_nav_live_location")
      ) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = "Live Location",
          tint = TextGrey,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text = "Live Location",
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          color = TextGrey
        )
        Spacer(modifier = Modifier.height(7.dp))
      }

      // 2. Center Boarding Tab (ACTIVE - Red elevated button matching image 2)
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.testTag("boarding_nav_boarding_center")
      ) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .shadow(4.dp, CircleShape)
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF560C17), Color(0xFF38050D))
              ),
              shape = CircleShape
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.DirectionsBus,
            contentDescription = "Boarding",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
          )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text = "Boarding",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = RouteRemaining
        )
        Spacer(modifier = Modifier.height(4.dp))
        // Red indicator pill underneath
        Box(
          modifier = Modifier
            .width(26.dp)
            .height(3.dp)
            .background(RouteRemaining, RoundedCornerShape(2.dp))
        )
      }

      // 3. Insights Tab (Inactive)
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .clickable { onNavigate(AppScreen.INSIGHTS) }
          .padding(horizontal = 8.dp, vertical = 4.dp)
          .testTag("boarding_nav_insights")
      ) {
        Icon(
          imageVector = Icons.Default.BarChart,
          contentDescription = "Insights",
          tint = TextGrey,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text = "Insights",
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          color = TextGrey
        )
        Spacer(modifier = Modifier.height(7.dp))
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 360 PANORAMA INTERACTIVE PREVIEW DIALOG
// -------------------------------------------------------------------------------------------------
@Composable
private fun PanoramaPreviewDialog(
  stop: BusStop,
  onDismiss: () -> Unit,
  onOpenGoogleMaps: () -> Unit
) {
  var panOffset by remember { mutableFloatStateOf(0f) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 16.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .testTag("panorama_dialog")
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = CircleShape,
              color = MaroonDeep,
              modifier = Modifier.size(28.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.Panorama,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "${stop.name} 360°",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Text(
                text = "Drag left/right to look around",
                fontSize = 10.sp,
                color = TextGrey
              )
            }
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextGrey)
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Interactive simulated panoramic viewport
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
            .pointerInput(Unit) {
              detectDragGestures { _, dragAmount ->
                panOffset = (panOffset + dragAmount.x).coerceIn(-120f, 120f)
              }
            }
        ) {
          Image(
            painter = painterResource(id = R.drawable.transit_wave_hero),
            contentDescription = "Panorama View",
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .fillMaxSize()
              .padding(start = panOffset.dp)
          )

          // 360 compass overlay
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.Black.copy(alpha = 0.6f),
            modifier = Modifier
              .align(Alignment.BottomCenter)
              .padding(8.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.ViewInAr,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "360° Spherical View Active • ${stop.landmark}",
                fontSize = 10.sp,
                color = Color.White
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
          onClick = {
            onDismiss()
            onOpenGoogleMaps()
          },
          colors = ButtonDefaults.buttonColors(containerColor = MaroonDeep),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Icon(Icons.Default.Navigation, contentDescription = null, tint = Color.White)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Get Full Navigation & Street View", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// INTENT HELPERS
// -------------------------------------------------------------------------------------------------
private fun openDirectionsIntent(context: Context, stop: BusStop) {
  val uri = Uri.parse("google.navigation:q=${stop.latitude},${stop.longitude}&mode=w")
  val intent = Intent(Intent.ACTION_VIEW, uri)
  try {
    context.startActivity(intent)
  } catch (e: Exception) {
    val webUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${stop.latitude},${stop.longitude}&travelmode=walking")
    try {
      context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
    } catch (e2: Exception) {
      Toast.makeText(context, "Cannot open map browser", Toast.LENGTH_SHORT).show()
    }
  }
}

private fun openStreetViewOrPreview(context: Context, stop: BusStop, onFallbackModal: () -> Unit) {
  val streetViewUri = Uri.parse("google.streetview:cbll=${stop.latitude},${stop.longitude}")
  val intent = Intent(Intent.ACTION_VIEW, streetViewUri)
  intent.setPackage("com.google.android.apps.maps")
  try {
    context.startActivity(intent)
  } catch (e: Exception) {
    // If native Google Maps street view app is not installed in emulator/container, open interactive in-app modal
    onFallbackModal()
  }
}
