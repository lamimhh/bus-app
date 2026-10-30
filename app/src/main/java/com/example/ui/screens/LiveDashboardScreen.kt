package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AirlineSeatReclineNormal
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.graphics.StrokeJoin
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
import com.example.R
import com.example.model.AppScreen
import com.example.model.BusStatus
import com.example.model.BusStop
import com.example.model.UserProfile
import com.example.viewmodel.BusViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Color Palette strictly matching 2_131a6d51.jpeg
private val DashboardBg = Color(0xFFF9F6F0)       // Warm ivory background
private val DashboardBgEnd = Color(0xFFF4EEE6)    // Subtle ivory shade
private val BurgundyDark = Color(0xFF28050B)      // Deepest burgundy for monitor
private val BurgundyMid = Color(0xFF4C0A15)       // Monitor gradient mid
private val BurgundyLight = Color(0xFF6B1120)     // Accent burgundy
private val CrimsonAccent = Color(0xFFE53935)     // Bright red >> chevrons & live dot
private val BurgundyText = Color(0xFF38060F)      // Header serif & title text
private val TextDark = Color(0xFF1F1618)          // Primary card text
private val TextGrey = Color(0xFF7A686D)          // Subtitle and label grey
private val CardBorderColor = Color(0xFFEDE4DC)   // Subtle warm border
private val RouteGreen = Color(0xFF10B981)        // Completed route green
private val RouteRemaining = Color(0xFF7E0E1C)    // Remaining route burgundy
private val RiverColor = Color(0xFFC7E2F2)        // Subtle river on map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveDashboardScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val busStatus by viewModel.busStatus.collectAsState()
  val userProfile by viewModel.userProfile.collectAsState()
  val allStops by viewModel.allStops.collectAsState()
  val isOperatorModalOpen by viewModel.isOperatorModalOpen.collectAsState()
  val operatorError by viewModel.operatorErrorMessage.collectAsState()
  val isViewAllStopsOpen by viewModel.isViewAllStopsOpen.collectAsState()
  val isServiceUpdateSheetOpen by viewModel.isServiceUpdateSheetOpen.collectAsState()
  val mapZoom by viewModel.mapZoom.collectAsState()

  val context = LocalContext.current
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  // Infinite pulsing transition for live dot
  val infiniteTransition = rememberInfiniteTransition(label = "LivePulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.35f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(900, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "alpha"
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(DashboardBg, DashboardBgEnd)
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
      // 1. TOP HEADER (Original S Logo, SADA SHUNDORI brand, and Profile Capsule)
      item {
        Spacer(modifier = Modifier.height(6.dp))
        DashboardHeader(
          userProfile = userProfile,
          onProfileClick = { viewModel.navigateTo(AppScreen.PROFILE) }
        )
      }

      // 2. MAIN LIVE JOURNEY MONITOR (Previous, Current, Next Stops, Node Track)
      item {
        MainLiveJourneyMonitor(
          busStatus = busStatus,
          pulseAlpha = pulseAlpha,
          onMonitorClick = { viewModel.openOperatorModal() }
        )
      }

      // 3. THREE METRIC CARDS (Next Stop, Journey Complete %, Current Speed)
      item {
        JourneyStatisticsRow(
          busStatus = busStatus,
          onNextStopClick = { viewModel.setViewAllStopsOpen(true) },
          onJourneyClick = { viewModel.setViewAllStopsOpen(true) },
          onSpeedClick = { viewModel.openOperatorModal() }
        )
      }

      // 4. AUTHENTIC INTERACTIVE LIVE MAP
      item {
        InteractiveMapCard(
          busStatus = busStatus,
          allStops = allStops,
          zoomLevel = mapZoom,
          onZoomIn = { viewModel.zoomIn() },
          onZoomOut = { viewModel.zoomOut() },
          onRecenter = { viewModel.recenterMap() },
          onBusClick = { viewModel.openOperatorModal() }
        )
      }

      // 5. "I'M ON THE BUS" ACTION BUTTON + HELPER TEXT
      item {
        BoardingActionButton(
          busStatus = busStatus,
          onClick = { viewModel.openOperatorModal() }
        )
      }

      // 6. UPCOMING STOPS VERTICAL TIMELINE
      item {
        UpcomingStopsSection(
          busStatus = busStatus,
          allStops = allStops,
          onViewAllClick = { viewModel.setViewAllStopsOpen(true) },
          onStopSelected = { stop -> viewModel.selectStop(stop.id) }
        )
      }

      // 7. SERVICE UPDATE CARD
      item {
        ServiceUpdateCard(
          busStatus = busStatus,
          onClick = { viewModel.setServiceUpdateSheetOpen(true) }
        )
      }

      // Spacer before floating bottom navigation
      item {
        Spacer(modifier = Modifier.height(84.dp))
      }
    }

    // 8. EXACT BOTTOM NAVIGATION BAR (Live Location, Boarding, Insights)
    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .navigationBarsPadding()
    ) {
      DashboardBottomNavigation(
        activeScreen = AppScreen.DASHBOARD,
        onNavigate = { screen -> viewModel.navigateTo(screen) },
        onBoardingClick = { viewModel.openOperatorModal() }
      )
    }

    // OPERATOR & PASSENGER BROADCAST MODAL
    if (isOperatorModalOpen) {
      ModalBottomSheet(
        onDismissRequest = { viewModel.closeOperatorModal() },
        sheetState = sheetState,
        containerColor = Color(0xFFFCFAF7),
        tonalElevation = 6.dp
      ) {
        OperatorAndBoardingSheetContent(
          busStatus = busStatus,
          errorMessage = operatorError,
          onVerifyOperator = { passcode ->
            viewModel.verifyAndStartOperatorBroadcast(passcode)
          },
          onStopBroadcast = { viewModel.stopOperatorTransmission() },
          onTogglePassengerOnboard = { viewModel.toggleOnboard() },
          onNextStop = { viewModel.advanceToNextStop() },
          onPrevStop = { viewModel.rewindToPreviousStop() },
          onDismiss = { viewModel.closeOperatorModal() }
        )
      }
    }

    // VIEW ALL 18 STOPS MODAL
    if (isViewAllStopsOpen) {
      ModalBottomSheet(
        onDismissRequest = { viewModel.setViewAllStopsOpen(false) },
        sheetState = sheetState,
        containerColor = Color(0xFFFCFAF7)
      ) {
        AllStopsSheetContent(
          allStops = allStops,
          currentStopIndex = busStatus.currentStopIndex,
          onSelectStop = { stop ->
            viewModel.selectStop(stop.id)
            viewModel.setViewAllStopsOpen(false)
          },
          onClose = { viewModel.setViewAllStopsOpen(false) }
        )
      }
    }

    // SERVICE UPDATE EDIT SHEET
    if (isServiceUpdateSheetOpen) {
      ModalBottomSheet(
        onDismissRequest = { viewModel.setServiceUpdateSheetOpen(false) },
        sheetState = sheetState,
        containerColor = Color(0xFFFCFAF7)
      ) {
        ServiceUpdateSheetContent(
          busStatus = busStatus,
          onToggleBusFull = { viewModel.setBusFull(it) },
          onToggleElevated = { viewModel.setElevatedRoute(it) },
          onClose = { viewModel.setServiceUpdateSheetOpen(false) }
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 1. TOP HEADER COMPONENT
// -------------------------------------------------------------------------------------------------
@Composable
private fun DashboardHeader(
  userProfile: UserProfile,
  onProfileClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Left: Official S Logo & Brand Name
    Row(verticalAlignment = Alignment.CenterVertically) {
      Image(
        painter = painterResource(id = R.drawable.sada_shundori_icon),
        contentDescription = "Sada Shundori Official Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .size(46.dp)
          .clip(RoundedCornerShape(12.dp))
      )
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(
          text = "SADA",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Serif,
          letterSpacing = 1.2.sp,
          color = BurgundyText,
          lineHeight = 18.sp
        )
        Text(
          text = "SHUNDORI",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Serif,
          letterSpacing = 1.2.sp,
          color = BurgundyText,
          lineHeight = 18.sp
        )
        Text(
          text = "LIVE BUS COMPANION",
          fontSize = 9.sp,
          fontWeight = FontWeight.SemiBold,
          letterSpacing = 1.5.sp,
          color = TextGrey,
          lineHeight = 11.sp
        )
      }
    }

    // Right: Student Profile Pill
    Surface(
      onClick = onProfileClick,
      shape = RoundedCornerShape(22.dp),
      color = Color.White,
      shadowElevation = 2.dp,
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      modifier = Modifier
        .height(40.dp)
        .testTag("header_profile_pill")
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(24.dp)
            .background(Color(0xFFF3ECE6), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = BurgundyText,
            modifier = Modifier.size(16.dp)
          )
        }
        Spacer(modifier = Modifier.width(6.dp))
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
// 2. MAIN LIVE JOURNEY MONITOR COMPONENT
// -------------------------------------------------------------------------------------------------
@Composable
private fun MainLiveJourneyMonitor(
  busStatus: BusStatus,
  pulseAlpha: Float,
  onMonitorClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, Color(0xFF6B1B2A), RoundedCornerShape(22.dp))
      .clickable { onMonitorClick() }
      .testTag("main_journey_monitor")
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(BurgundyDark, BurgundyMid, BurgundyDark)
          )
        )
        .padding(18.dp)
    ) {
      Column {
        // Top status pill row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Left: Live Indicator + Label
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .background(CrimsonAccent.copy(alpha = pulseAlpha), CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = CrimsonAccent,
              modifier = Modifier.padding(end = 6.dp)
            ) {
              Text(
                text = "LIVE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 0.8.sp,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            Text(
              text = "BUS LOCATION STATUS",
              fontSize = 10.sp,
              fontWeight = FontWeight.Medium,
              letterSpacing = 1.1.sp,
              color = Color(0xFFD6A5AE)
            )
          }

          // Right: SADA pill
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0x33FFFFFF),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22FFFFFF))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.DirectionsBus,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(12.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "SADA",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 0.5.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Stop Flow Row (Previous Stop >> CURRENT STOP >> Next Stop)
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Previous Stop (Left)
          Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
          ) {
            Text(
              text = "PREVIOUS STOP",
              fontSize = 9.sp,
              fontWeight = FontWeight.SemiBold,
              letterSpacing = 0.8.sp,
              color = Color(0xFFB88B94)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = busStatus.previousStopName,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }

          // Red Chevrons >>
          Text(
            text = ">>",
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = CrimsonAccent,
            modifier = Modifier.padding(horizontal = 4.dp)
          )

          // Current Stop (Center - Highlighted)
          Column(
            modifier = Modifier.weight(1.3f),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "CURRENT STOP",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp,
              color = Color(0xFFF7B5C0)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = busStatus.currentStopName,
              fontSize = 20.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color.White,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              textAlign = TextAlign.Center
            )
          }

          // Red Chevrons >>
          Text(
            text = ">>",
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = CrimsonAccent,
            modifier = Modifier.padding(horizontal = 4.dp)
          )

          // Next Stop (Right)
          Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
          ) {
            Text(
              text = "NEXT STOP",
              fontSize = 9.sp,
              fontWeight = FontWeight.SemiBold,
              letterSpacing = 0.8.sp,
              color = Color(0xFFB88B94)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = busStatus.nextStopName,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              textAlign = TextAlign.End
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Horizontal Node Track
        HorizontalRouteTrack(pulseAlpha = pulseAlpha)
      }
    }
  }
}

@Composable
private fun HorizontalRouteTrack(
  pulseAlpha: Float,
  modifier: Modifier = Modifier
) {
  Canvas(
    modifier = modifier
      .fillMaxWidth()
      .height(16.dp)
  ) {
    val centerY = size.height / 2
    val strokeWidth = 2.dp.toPx()

    // Base horizontal route line
    drawLine(
      color = Color(0x55E53935),
      start = Offset(10f, centerY),
      end = Offset(size.width - 10f, centerY),
      strokeWidth = strokeWidth,
      cap = StrokeCap.Round
    )

    // Completed segment up to center
    drawLine(
      color = CrimsonAccent,
      start = Offset(10f, centerY),
      end = Offset(size.width * 0.5f, centerY),
      strokeWidth = strokeWidth,
      cap = StrokeCap.Round
    )

    // 5 nodes across the line
    val nodePositions = listOf(0.08f, 0.28f, 0.50f, 0.72f, 0.92f)
    nodePositions.forEachIndexed { index, fraction ->
      val x = size.width * fraction

      if (index == 2) {
        // Current Stop Node (Center - glowing double ring)
        drawCircle(
          color = CrimsonAccent.copy(alpha = 0.4f * pulseAlpha),
          radius = 9.dp.toPx(),
          center = Offset(x, centerY)
        )
        drawCircle(
          color = CrimsonAccent,
          radius = 5.dp.toPx(),
          center = Offset(x, centerY)
        )
        drawCircle(
          color = Color.White,
          radius = 2.5.dp.toPx(),
          center = Offset(x, centerY)
        )
      } else if (index < 2) {
        // Passed stops (solid small pink/white nodes)
        drawCircle(
          color = CrimsonAccent,
          radius = 3.dp.toPx(),
          center = Offset(x, centerY)
        )
      } else {
        // Upcoming stops (dim small nodes)
        drawCircle(
          color = Color(0x66B88B94),
          radius = 3.dp.toPx(),
          center = Offset(x, centerY)
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 3. THREE METRIC CARDS ROW
// -------------------------------------------------------------------------------------------------
@Composable
private fun JourneyStatisticsRow(
  busStatus: BusStatus,
  onNextStopClick: () -> Unit,
  onJourneyClick: () -> Unit,
  onSpeedClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    // Card 1: Next Stop
    MetricCard(
      modifier = Modifier
        .weight(1f)
        .clickable { onNextStopClick() }
        .testTag("stat_card_next_stop")
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = null,
              tint = RouteRemaining,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = "NEXT STOP",
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.7.sp,
              color = TextGrey
            )
          }
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextGrey,
            modifier = Modifier.size(12.dp)
          )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = busStatus.currentStopName,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.NearMe,
            contentDescription = null,
            tint = TextGrey,
            modifier = Modifier.size(11.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "${busStatus.distanceToNextKm} km left",
            fontSize = 11.sp,
            color = TextGrey,
            maxLines = 1
          )
        }

        Spacer(modifier = Modifier.height(3.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = null,
            tint = TextGrey,
            modifier = Modifier.size(11.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "ETA ${busStatus.etaToNextMinutes} min",
            fontSize = 11.sp,
            color = TextGrey,
            maxLines = 1
          )
        }
      }
    }

    // Card 2: Journey Complete % (Circular Arc)
    MetricCard(
      modifier = Modifier
        .weight(1f)
        .clickable { onJourneyClick() }
        .testTag("stat_card_journey_complete")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        // Circular gauge
        Box(
          modifier = Modifier.size(54.dp),
          contentAlignment = Alignment.Center
        ) {
          CircularJourneyArc(percentage = busStatus.journeyPercentage)
          Text(
            text = "${busStatus.journeyPercentage}%",
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = BurgundyText
          )
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "JOURNEY COMPLETE",
          fontSize = 8.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp,
          color = TextGrey,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "${busStatus.completedDistanceKm} km / ${busStatus.totalRouteDistanceKm.toInt()} km",
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = BurgundyText,
          textAlign = TextAlign.Center
        )
      }
    }

    // Card 3: Current Speed
    MetricCard(
      modifier = Modifier
        .weight(1f)
        .clickable { onSpeedClick() }
        .testTag("stat_card_speed")
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Speed,
            contentDescription = null,
            tint = RouteRemaining,
            modifier = Modifier.size(14.dp)
          )
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextGrey,
            modifier = Modifier.size(12.dp)
          )
        }

        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "CURRENT SPEED",
          fontSize = 8.5.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp,
          color = TextGrey
        )

        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom) {
          Text(
            text = "${busStatus.currentSpeedKmh}",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = TextDark,
            lineHeight = 30.sp
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "km/h",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextGrey,
            modifier = Modifier.padding(bottom = 3.dp)
          )
        }
      }
    }
  }
}

@Composable
private fun MetricCard(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(18.dp),
    color = Color.White,
    shadowElevation = 2.dp,
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    modifier = modifier.height(112.dp)
  ) {
    content()
  }
}

@Composable
private fun CircularJourneyArc(
  percentage: Int,
  modifier: Modifier = Modifier
) {
  Canvas(modifier = modifier.size(54.dp)) {
    val strokeWidth = 5.5.dp.toPx()
    val diameter = size.minDimension - strokeWidth
    val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
    val sweepPct = (percentage.coerceIn(0, 100) / 100f) * 270f

    // Background Arc
    drawArc(
      color = Color(0xFFE8E0D8),
      startAngle = 135f,
      sweepAngle = 270f,
      useCenter = false,
      topLeft = topLeft,
      size = androidx.compose.ui.geometry.Size(diameter, diameter),
      style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )

    // Progress Arc in deep burgundy
    drawArc(
      color = RouteRemaining,
      startAngle = 135f,
      sweepAngle = sweepPct,
      useCenter = false,
      topLeft = topLeft,
      size = androidx.compose.ui.geometry.Size(diameter, diameter),
      style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
  }
}

// -------------------------------------------------------------------------------------------------
// 4. AUTHENTIC INTERACTIVE LIVE MAP COMPONENT
// -------------------------------------------------------------------------------------------------
@Composable
private fun InteractiveMapCard(
  busStatus: BusStatus,
  allStops: List<BusStop>,
  zoomLevel: Float,
  onZoomIn: () -> Unit,
  onZoomOut: () -> Unit,
  onRecenter: () -> Unit,
  onBusClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(22.dp),
    color = Color.White,
    shadowElevation = 3.dp,
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    modifier = modifier
      .fillMaxWidth()
      .height(290.dp)
      .testTag("interactive_map_card")
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      // Vector canvas drawing route and environment
      Canvas(
        modifier = Modifier
          .fillMaxSize()
          .clip(RoundedCornerShape(22.dp))
      ) {
        val w = size.width
        val h = size.height

        // Neutral map base background
        drawRect(color = Color(0xFFF3EFE9))

        // Simulated road grid lines (subtle white tracks)
        val gridPaint = Color(0xFFE8E2D9)
        drawLine(gridPaint, Offset(0f, h * 0.25f), Offset(w, h * 0.25f), strokeWidth = 12f)
        drawLine(gridPaint, Offset(0f, h * 0.65f), Offset(w, h * 0.65f), strokeWidth = 16f)
        drawLine(gridPaint, Offset(w * 0.22f, 0f), Offset(w * 0.22f, h), strokeWidth = 10f)
        drawLine(gridPaint, Offset(w * 0.78f, 0f), Offset(w * 0.78f, h), strokeWidth = 12f)

        // Simulated Turag River Curve (winding light blue path)
        val riverPath = Path().apply {
          moveTo(0f, h * 0.35f)
          cubicTo(w * 0.2f, h * 0.45f, w * 0.35f, h * 0.55f, w * 0.25f, h * 0.85f)
          cubicTo(w * 0.20f, h * 0.95f, w * 0.40f, h, w * 0.45f, h)
        }
        drawPath(
          path = riverPath,
          color = RiverColor,
          style = Stroke(width = 16f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Route Coordinates Mapping on Canvas (National Univ -> Gazipura -> Tongi -> Abdullahpur -> Campus)
        val pGazipura = Offset(w * 0.49f, h * 0.14f)
        val pTongi = Offset(w * 0.51f, h * 0.46f)
        val pAbdullahpur = Offset(w * 0.61f, h * 0.78f)
        val pCampus = Offset(w * 0.75f, h * 0.98f)

        // 1. Completed Route (Green #10B981) from Gazipura to Tongi
        val completedPath = Path().apply {
          moveTo(w * 0.48f, 0f)
          lineTo(pGazipura.x, pGazipura.y)
          cubicTo(w * 0.495f, h * 0.26f, w * 0.505f, h * 0.36f, pTongi.x, pTongi.y)
        }
        drawPath(
          path = completedPath,
          color = RouteGreen,
          style = Stroke(width = 5.dp.toPx() * zoomLevel, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // 2. Remaining Route (Burgundy #7E0E1C) from Tongi onwards to Campus
        val remainingPath = Path().apply {
          moveTo(pTongi.x, pTongi.y)
          cubicTo(w * 0.53f, h * 0.56f, w * 0.57f, h * 0.68f, pAbdullahpur.x, pAbdullahpur.y)
          cubicTo(w * 0.66f, h * 0.86f, w * 0.70f, h * 0.92f, pCampus.x, pCampus.y)
        }
        drawPath(
          path = remainingPath,
          color = RouteRemaining,
          style = Stroke(width = 5.dp.toPx() * zoomLevel, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Draw stop markers
        // Stop: Gazipura 27
        drawCircle(color = Color.White, radius = 5.dp.toPx(), center = pGazipura)
        drawCircle(color = RouteGreen, radius = 5.dp.toPx(), center = pGazipura, style = Stroke(2.5.dp.toPx()))

        // Stop: Abdullahpur
        drawCircle(color = Color.White, radius = 5.dp.toPx(), center = pAbdullahpur)
        drawCircle(color = RouteRemaining, radius = 5.dp.toPx(), center = pAbdullahpur, style = Stroke(2.5.dp.toPx()))

        // Current Bus Marker at Tongi (large circle with pulse)
        drawCircle(
          color = RouteRemaining.copy(alpha = 0.22f),
          radius = 18.dp.toPx(),
          center = pTongi
        )
        drawCircle(
          color = RouteRemaining,
          radius = 11.dp.toPx(),
          center = pTongi
        )
        drawCircle(
          color = Color.White,
          radius = 9.dp.toPx(),
          center = pTongi
        )
        drawCircle(
          color = RouteRemaining,
          radius = 7.dp.toPx(),
          center = pTongi
        )
      }

      // Map Stop Text Labels placed accurately
      // Label: Gazipura 27
      Box(
        modifier = Modifier
          .align(Alignment.TopCenter)
          .padding(top = 28.dp, start = 72.dp)
      ) {
        Text(
          text = "Gazipura 27",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark
        )
      }

      // Label: Tongi (Beside bus marker)
      Box(
        modifier = Modifier
          .align(Alignment.Center)
          .padding(start = 74.dp, bottom = 12.dp)
      ) {
        Text(
          text = "Tongi",
          fontSize = 12.sp,
          fontWeight = FontWeight.ExtraBold,
          color = TextDark
        )
      }

      // Label: Bashundhara / Airport connector
      Box(
        modifier = Modifier
          .align(Alignment.CenterEnd)
          .padding(end = 56.dp, top = 22.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(6.dp)
              .background(Color(0xFF8BA5B5), CircleShape)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Bashundhara",
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF6B7E8C)
          )
        }
      }

      // Label: Abdullahpur
      Box(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = 60.dp, start = 140.dp)
      ) {
        Text(
          text = "Abdullahpur",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark
        )
      }

      // Floating Top-Left Pill: "Sada Shundori >"
      Surface(
        onClick = onBusClick,
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        modifier = Modifier
          .padding(12.dp)
          .align(Alignment.TopStart)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.DirectionsBus,
            contentDescription = null,
            tint = RouteRemaining,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Sada Shundori",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = BurgundyText
          )
          Spacer(modifier = Modifier.width(2.dp))
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextGrey,
            modifier = Modifier.size(14.dp)
          )
        }
      }

      // Floating Top-Right Recenter Button
      Surface(
        onClick = onRecenter,
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        modifier = Modifier
          .padding(12.dp)
          .size(36.dp)
          .align(Alignment.TopEnd)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.Default.MyLocation,
            contentDescription = "Recenter",
            tint = TextDark,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      // Floating Middle-Right Zoom Controls (+ and -)
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        modifier = Modifier
          .padding(end = 12.dp)
          .align(Alignment.CenterEnd)
      ) {
        Column(
          modifier = Modifier.padding(2.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          IconButton(
            onClick = onZoomIn,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Zoom In",
              tint = TextDark,
              modifier = Modifier.size(16.dp)
            )
          }
          HorizontalDivider(
            modifier = Modifier.width(20.dp),
            color = CardBorderColor,
            thickness = 1.dp
          )
          IconButton(
            onClick = onZoomOut,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Remove,
              contentDescription = "Zoom Out",
              tint = TextDark,
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }

      // Floating Bottom-Left Legend Card
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(alpha = 0.94f),
        shadowElevation = 1.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        modifier = Modifier
          .padding(10.dp)
          .align(Alignment.BottomStart)
      ) {
        Column(
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
          verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
          // Remaining route
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .width(12.dp)
                .height(3.dp)
                .background(RouteRemaining, RoundedCornerShape(1.dp))
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Remaining route", fontSize = 9.sp, color = TextDark)
          }
          // Completed route
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .width(12.dp)
                .height(3.dp)
                .background(RouteGreen, RoundedCornerShape(1.dp))
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Completed route", fontSize = 9.sp, color = TextDark)
          }
          // Bus stop
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(7.dp)
                .border(1.5.dp, TextGrey, CircleShape)
            )
            Spacer(modifier = Modifier.width(9.dp))
            Text(text = "Bus stop", fontSize = 9.sp, color = TextDark)
          }
          // Current location
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.DirectionsBus,
              contentDescription = null,
              tint = RouteRemaining,
              modifier = Modifier.size(9.dp)
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(text = "Current location", fontSize = 9.sp, color = TextDark)
          }
        }
      }

      // Bottom Right Caption
      Text(
        text = "Map data (sample) • Real-time GPS",
        fontSize = 8.sp,
        color = Color(0xFF9E8E92),
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(end = 10.dp, bottom = 6.dp)
      )
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 5. "I'M ON THE BUS" ACTION BUTTON
// -------------------------------------------------------------------------------------------------
@Composable
private fun BoardingActionButton(
  busStatus: BusStatus,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Surface(
      onClick = onClick,
      shape = RoundedCornerShape(20.dp),
      color = Color.Transparent,
      shadowElevation = 4.dp,
      modifier = Modifier
        .fillMaxWidth()
        .height(58.dp)
        .testTag("btn_im_on_the_bus")
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            brush = Brush.horizontalGradient(
              colors = listOf(Color(0xFF38050D), Color(0xFF560C17), Color(0xFF3B060F))
            )
          )
          .padding(horizontal = 18.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Left white circular icon badge
          Box(
            modifier = Modifier
              .size(34.dp)
              .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = RouteRemaining,
              modifier = Modifier.size(20.dp)
            )
          }

          // Center Button Text
          Text(
            text = "I'M ON THE BUS",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = 1.2.sp
          )

          // Right chevron
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Helper text with checkmark
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = Icons.Default.CheckCircle,
        contentDescription = null,
        tint = RouteGreen,
        modifier = Modifier.size(15.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "Your boarding status helps us serve you better",
        fontSize = 11.5.sp,
        fontWeight = FontWeight.Medium,
        color = TextGrey
      )
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 6. UPCOMING STOPS TIMELINE SECTION
// -------------------------------------------------------------------------------------------------
@Composable
private fun UpcomingStopsSection(
  busStatus: BusStatus,
  allStops: List<BusStop>,
  onViewAllClick: () -> Unit,
  onStopSelected: (BusStop) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    // Header Row (UPCOMING STOPS and View all >)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "UPCOMING STOPS",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.8.sp,
        color = BurgundyText
      )
      TextButton(
        onClick = onViewAllClick,
        modifier = Modifier.testTag("btn_view_all_stops")
      ) {
        Text(
          text = "View all >",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = RouteRemaining
        )
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    // Vertical Timeline of next stops
    val upcomingItems = remember(busStatus.currentStopIndex, allStops) {
      val currentIndex = busStatus.currentStopIndex
      val list = mutableListOf<Triple<String, String, Boolean>>()

      // Next stop
      list.add(Triple(busStatus.currentStopName, "Next • 18 min", true))

      // Followed by next 3 stops from the sequence
      for (i in (currentIndex + 1)..(currentIndex + 3)) {
        if (i < allStops.size) {
          val stop = allStops[i]
          val est = when (i - currentIndex) {
            1 -> "32 min"
            2 -> "48 min"
            else -> "1 hr 12 min"
          }
          list.add(Triple(stop.name, est, false))
        }
      }
      list
    }

    Surface(
      shape = RoundedCornerShape(18.dp),
      color = Color.White,
      shadowElevation = 2.dp,
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 14.dp)
      ) {
        upcomingItems.forEachIndexed { index, item ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Timeline connector + Node
            Box(
              modifier = Modifier.width(28.dp),
              contentAlignment = Alignment.Center
            ) {
              if (item.third) {
                // Active Next Stop Node (Double ring)
                Box(
                  modifier = Modifier
                    .size(16.dp)
                    .border(3.dp, RouteRemaining, CircleShape)
                    .background(Color.White, CircleShape),
                  contentAlignment = Alignment.Center
                ) {
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .background(RouteRemaining, CircleShape)
                  )
                }
              } else {
                // Secondary stop node (hollow circle)
                Box(
                  modifier = Modifier
                    .size(12.dp)
                    .border(2.dp, Color(0xFFB0A2A5), CircleShape)
                    .background(Color.White, CircleShape)
                )
              }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Stop Name
            Text(
              text = item.first,
              fontSize = 14.sp,
              fontWeight = if (item.third) FontWeight.Bold else FontWeight.Medium,
              color = TextDark,
              modifier = Modifier.weight(1f)
            )

            // Time / Pill
            if (item.third) {
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFFEBEE)
              ) {
                Text(
                  text = item.second,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = RouteRemaining,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
              }
            } else {
              Text(
                text = item.second,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = TextGrey
              )
            }
          }

          if (index < upcomingItems.size - 1) {
            // Vertical timeline guide line
            Box(
              modifier = Modifier
                .padding(start = 13.dp)
                .width(2.dp)
                .height(20.dp)
                .background(Color(0xFFE8E0D8))
            )
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 7. SERVICE UPDATE CARD
// -------------------------------------------------------------------------------------------------
@Composable
private fun ServiceUpdateCard(
  busStatus: BusStatus,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(18.dp),
    color = Color.White,
    shadowElevation = 2.dp,
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    modifier = modifier
      .fillMaxWidth()
      .testTag("card_service_update")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Megaphone Icon
      Box(
        modifier = Modifier
          .size(38.dp)
          .background(Color(0xFFFFEBEE), CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Campaign,
          contentDescription = null,
          tint = RouteRemaining,
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Service Update",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.width(4.dp))
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextGrey,
            modifier = Modifier.size(14.dp)
          )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = busStatus.serviceUpdateText,
          fontSize = 11.5.sp,
          color = TextGrey,
          lineHeight = 15.sp,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 8. EXACT BOTTOM NAVIGATION BAR
// -------------------------------------------------------------------------------------------------
@Composable
private fun DashboardBottomNavigation(
  activeScreen: AppScreen,
  onNavigate: (AppScreen) -> Unit,
  onBoardingClick: () -> Unit,
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
      // 1. Live Location (Active item with red highlight and underline)
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .clickable { onNavigate(AppScreen.DASHBOARD) }
          .padding(horizontal = 8.dp, vertical = 4.dp)
          .testTag("nav_live_location")
      ) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = "Live Location",
          tint = RouteRemaining,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text = "Live Location",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = RouteRemaining
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
          modifier = Modifier
            .width(26.dp)
            .height(3.dp)
            .background(RouteRemaining, RoundedCornerShape(2.dp))
        )
      }

      // 2. Elevated Center Boarding Button
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .clickable { onBoardingClick() }
          .testTag("nav_boarding_center")
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
            imageVector = Icons.Default.Person,
            contentDescription = "Boarding",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
          )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text = "Boarding",
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          color = TextGrey
        )
      }

      // 3. Insights
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .clickable { onNavigate(AppScreen.INSIGHTS) }
          .padding(horizontal = 8.dp, vertical = 4.dp)
          .testTag("nav_insights")
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
// MODAL: OPERATOR GPS BROADCAST & PASSENGER BOARDING SHEET
// -------------------------------------------------------------------------------------------------
@Composable
private fun OperatorAndBoardingSheetContent(
  busStatus: BusStatus,
  errorMessage: String?,
  onVerifyOperator: (String) -> Unit,
  onStopBroadcast: () -> Unit,
  onTogglePassengerOnboard: () -> Unit,
  onNextStop: () -> Unit,
  onPrevStop: () -> Unit,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(if (busStatus.isOperatorTransmitting) 1 else 0) }
  var passcodeInput by remember { mutableStateOf("") }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp)
  ) {
    // Sheet Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Boarding & Fleet Hub",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = BurgundyText
      )
      IconButton(onClick = onDismiss) {
        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextGrey)
      }
    }

    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = Color.Transparent,
      contentColor = RouteRemaining,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
          color = RouteRemaining
        )
      }
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        text = { Text("Passenger Boarding", fontWeight = FontWeight.Bold) }
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = { Text("Operator GPS Transmitter", fontWeight = FontWeight.Bold) }
      )
    }

    Spacer(modifier = Modifier.height(18.dp))

    if (selectedTab == 0) {
      // Passenger Boarding Tab
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = if (busStatus.isUserOnboard) Color(0xFFE8F5E9) else Color.White,
          border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            if (busStatus.isUserOnboard) RouteGreen else CardBorderColor
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .background(
                  if (busStatus.isUserOnboard) RouteGreen else Color(0xFFEDE4DC),
                  CircleShape
                ),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (busStatus.isUserOnboard) Icons.Default.Check else Icons.Default.Person,
                contentDescription = null,
                tint = if (busStatus.isUserOnboard) Color.White else RouteRemaining
              )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
              Text(
                text = if (busStatus.isUserOnboard) "You are marked ON THE BUS" else "Not currently on board",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Text(
                text = "${busStatus.onboardCount} of ${busStatus.totalSeats} seats currently reported",
                fontSize = 12.sp,
                color = TextGrey
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = onTogglePassengerOnboard,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (busStatus.isUserOnboard) Color(0xFFC62828) else RouteRemaining
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("btn_toggle_passenger_onboard")
        ) {
          Text(
            text = if (busStatus.isUserOnboard) "Mark myself OFF THE BUS" else "I'M ON THE BUS NOW",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    } else {
      // Operator GPS Transmitter Tab
      Column(modifier = Modifier.fillMaxWidth()) {
        if (!busStatus.isOperatorTransmitting) {
          Text(
            text = "Authorized Driver/Operator Phone as GPS Device",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = BurgundyText
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Enter fleet secret key (SADA2026) to begin publishing this phone's live GPS telemetry to the campus central tracker.",
            fontSize = 12.sp,
            color = TextGrey,
            lineHeight = 16.sp
          )
          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = passcodeInput,
            onValueChange = { passcodeInput = it },
            label = { Text("Fleet Passcode") },
            placeholder = { Text("SADA2026") },
            singleLine = true,
            leadingIcon = {
              Icon(Icons.Default.Lock, contentDescription = null, tint = RouteRemaining)
            },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = RouteRemaining,
              focusedLabelColor = RouteRemaining
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_operator_passcode")
          )

          if (errorMessage != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = errorMessage,
              fontSize = 12.sp,
              color = CrimsonAccent,
              fontWeight = FontWeight.Medium
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = { onVerifyOperator(passcodeInput) },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RouteRemaining),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("btn_start_operator_broadcast")
          ) {
            Icon(Icons.Default.Radio, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Start GPS Broadcasting", fontWeight = FontWeight.Bold)
          }
        } else {
          // Actively broadcasting state
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFE8F5E9),
            border = androidx.compose.foundation.BorderStroke(1.dp, RouteGreen),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(10.dp)
                    .background(RouteGreen, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "OPERATOR TRANSMITTING LIVE GPS",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF1B5E20)
                )
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "Coordinates: ${String.format("%.4f", busStatus.currentLatitude)}, ${String.format("%.4f", busStatus.currentLongitude)}",
                fontSize = 12.sp,
                color = TextDark
              )
              Text(
                text = "Current Stop: ${busStatus.currentStopName} • Speed: ${busStatus.currentSpeedKmh} km/h",
                fontSize = 12.sp,
                color = TextGrey
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Testing simulation controls for review
          Text(
            text = "Testing Controls (Advance Stop on Route):",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            color = BurgundyText
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = onPrevStop,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B1B2A)),
              modifier = Modifier.weight(1f)
            ) {
              Text("Prev Stop", fontSize = 12.sp)
            }
            Button(
              onClick = onNextStop,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = RouteRemaining),
              modifier = Modifier.weight(1f)
            ) {
              Text("Next Stop", fontSize = 12.sp)
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Button(
            onClick = onStopBroadcast,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
            modifier = Modifier
              .fillMaxWidth()
              .height(46.dp)
          ) {
            Text("Stop Operator GPS Broadcast", fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}

// -------------------------------------------------------------------------------------------------
// MODAL: VIEW ALL 18 STOPS SHEET
// -------------------------------------------------------------------------------------------------
@Composable
private fun AllStopsSheetContent(
  allStops: List<BusStop>,
  currentStopIndex: Int,
  onSelectStop: (BusStop) -> Unit,
  onClose: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Official 18 Bus Stops",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = BurgundyText
        )
        Text(
          text = "National University Gate ➔ Campus Corridor",
          fontSize = 11.sp,
          color = TextGrey
        )
      }
      IconButton(onClick = onClose) {
        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextGrey)
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .height(420.dp)
    ) {
      itemsIndexed(allStops) { index, stop ->
        val isPassed = index < currentStopIndex
        val isCurrent = index == currentStopIndex
        val isNext = index == currentStopIndex + 1

        Surface(
          onClick = { onSelectStop(stop) },
          shape = RoundedCornerShape(12.dp),
          color = when {
            isCurrent -> Color(0xFFFFEBEE)
            isNext -> Color(0xFFFFF7ED)
            else -> Color.White
          },
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isCurrent) RouteRemaining else CardBorderColor
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(26.dp)
                .background(
                  when {
                    isCurrent -> RouteRemaining
                    isPassed -> RouteGreen
                    else -> Color(0xFFEDE4DC)
                  },
                  CircleShape
                ),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "${stop.sequenceNumber}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (isCurrent || isPassed) Color.White else TextDark
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = stop.name,
                fontSize = 14.sp,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                color = if (isCurrent) BurgundyText else TextDark
              )
              Text(
                text = "${stop.banglaName} • ${stop.distanceFromStartKm} km",
                fontSize = 11.sp,
                color = TextGrey
              )
            }

            if (isCurrent) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = RouteRemaining
              ) {
                Text(
                  text = "BUS HERE",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            } else if (isNext) {
              Text(
                text = "NEXT",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = CrimsonAccent
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))
  }
}

// -------------------------------------------------------------------------------------------------
// MODAL: SERVICE UPDATE MANAGEMENT SHEET
// -------------------------------------------------------------------------------------------------
@Composable
private fun ServiceUpdateSheetContent(
  busStatus: BusStatus,
  onToggleBusFull: (Boolean) -> Unit,
  onToggleElevated: (Boolean) -> Unit,
  onClose: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Fleet Service Status",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = BurgundyText
      )
      IconButton(onClick = onClose) {
        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextGrey)
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Toggle 1: Bus is Full
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = Color.White,
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "BUS IS FULL",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = BurgundyText
          )
          Text(
            text = "Notify waiting students along next stops that all 52 seats are occupied.",
            fontSize = 11.5.sp,
            color = TextGrey
          )
        }
        Switch(
          checked = busStatus.isBusFull,
          onCheckedChange = onToggleBusFull,
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = RouteRemaining
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Toggle 2: Bus in Elevated Route
    Surface(
      shape = RoundedCornerShape(14.dp),
      color = Color.White,
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "BUS IN ELEVATED ROUTE",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = BurgundyText
          )
          Text(
            text = "Broadcast that the bus is taking the Dhaka Elevated Expressway to bypass bottlenecks.",
            fontSize = 11.5.sp,
            color = TextGrey
          )
        }
        Switch(
          checked = busStatus.isElevatedRoute,
          onCheckedChange = onToggleElevated,
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = RouteRemaining
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}
