package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import coil.compose.AsyncImage
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import android.view.HapticFeedbackConstants
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppScreen
import com.example.model.UserProfile
import com.example.ui.components.SadaBottomNavigation
import com.example.ui.theme.SadaTheme
import com.example.ui.util.HapticHelper
import com.example.ui.util.HapticType
import com.example.viewmodel.BusViewModel

// Color palette matching 4_cd522351.jpeg with dual-theme adaptation
private val ScreenBg: Color @Composable get() = SadaTheme.colors.background
private val ScreenBgEnd: Color @Composable get() = SadaTheme.colors.backgroundEnd
private val MaroonDeep: Color @Composable get() = if (SadaTheme.isDark) Color(0xFFEF4444) else Color(0xFF4A0A12)
private val MaroonPeak: Color @Composable get() = if (SadaTheme.isDark) Color(0xFFEF4444) else Color(0xFF7E0E1C)
private val RoseBar: Color @Composable get() = if (SadaTheme.isDark) Color(0xFF881324) else Color(0xFFD67D78)
private val RoseBarSelected: Color @Composable get() = if (SadaTheme.isDark) Color(0xFFEF4444) else Color(0xFFC46A65)
private val CardBorderColor: Color @Composable get() = SadaTheme.colors.cardBorder
private val TextDark: Color @Composable get() = SadaTheme.colors.textPrimary
private val TextGrey: Color @Composable get() = SadaTheme.colors.textSecondary
private val TextMutedLight: Color @Composable get() = SadaTheme.colors.textMuted
private val GuidelineColor: Color @Composable get() = if (SadaTheme.isDark) Color(0xFF260D13) else Color(0xFFECE3DC)
private val BadgeBg: Color @Composable get() = if (SadaTheme.isDark) Color(0xFF260D13) else Color(0xFFF8EBEB)
private val NoticeBg: Color @Composable get() = if (SadaTheme.isDark) Color(0xFF1E0C12) else Color(0xFFF6EFE8)
private val CardBg: Color @Composable get() = SadaTheme.colors.cardBackground
private val SurfaceBg: Color @Composable get() = SadaTheme.colors.cardSurface

data class DayDelayItem(
  val day: String,
  val date: String,
  val delayMin: Int,
  val isPeak: Boolean = false,
  val detailsNote: String = "Normal traffic along Dhaka-Mymensingh Highway."
)

@Composable
fun InsightsScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val userProfile by viewModel.userProfile.collectAsState()
  val currentLanguage by viewModel.currentLanguage.collectAsState()

  // Delay records matching exact image values (MON +42, TUE +67, WED +28, THU +93, FRI +55, SAT +108, SUN +71)
  val delayRecords = remember {
    listOf(
      DayDelayItem("MON", "13 Oct", 42, isPeak = false, "Tongi College Gate bottleneck +15m, Airport signal +12m"),
      DayDelayItem("TUE", "14 Oct", 67, isPeak = false, "Heavy rain and waterlogging near Abdullahpur +25m"),
      DayDelayItem("WED", "15 Oct", 28, isPeak = false, "Smooth transit across Mohakhali flyover, minimal delays"),
      DayDelayItem("THU", "16 Oct", 93, isPeak = false, "Thursday evening weekend rush & gridlock past Gazipura"),
      DayDelayItem("FRI", "17 Oct", 55, isPeak = false, "Jumma prayer congestion at Cherag Ali & Station Road"),
      DayDelayItem("SAT", "18 Oct", 108, isPeak = true, "VIP movement & road repairs at Airport intersection"),
      DayDelayItem("SUN", "19 Oct", 71, isPeak = false, "Morning student rush and office traffic jam at Kuril")
    )
  }

  var selectedDateRange by remember { mutableStateOf("PREV. 7 DAYS") }
  var isDateRangeMenuExpanded by remember { mutableStateOf(false) }
  var selectedDay by remember { mutableStateOf<DayDelayItem?>(delayRecords.find { it.isPeak }) }
  var showInfoDialog by remember { mutableStateOf(false) }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(ScreenBg, ScreenBgEnd)
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
      // 1. BRAND HEADER (SADA SHUNDORI + Avatar Capsule)
      item {
        Spacer(modifier = Modifier.height(2.dp))
        InsightsHeader(
          userProfile = userProfile,
          onProfileClick = { viewModel.navigateTo(AppScreen.PROFILE) }
        )
      }

      // 2. DELAY HISTORY CARD (Matching Card 1 in reference image)
      item {
        DelayHistoryCard(
          records = delayRecords,
          selectedDay = selectedDay,
          onSelectDay = { selectedDay = it },
          selectedDateRange = selectedDateRange,
          onOpenDateRangeMenu = { isDateRangeMenuExpanded = true },
          isDateRangeMenuExpanded = isDateRangeMenuExpanded,
          onDismissDateRangeMenu = { isDateRangeMenuExpanded = false },
          onSelectDateRange = {
            selectedDateRange = it
            isDateRangeMenuExpanded = false
          },
          onInfoClick = { showInfoDialog = true }
        )
      }

      // 3. JOURNEY DETAILS CARD (Matching Card 2 in reference image)
      item {
        JourneyDetailsCard(
          selectedDay = selectedDay
        )
      }

      // 4. NOTICE CARD (Matching Card 3 in reference image)
      item {
        RealTimeNoticeCard()
      }

      // Bottom padding so items are not hidden by bottom navigation bar
      item {
        Spacer(modifier = Modifier.height(100.dp))
      }
    }

    // 6. STANDARDIZED CENTRALIZED BOTTOM NAVIGATION BAR
    SadaBottomNavigation(
      activeScreen = AppScreen.INSIGHTS,
      onNavigate = { screen -> viewModel.navigateTo(screen) },
      language = currentLanguage,
      modifier = Modifier.align(Alignment.BottomCenter)
    )

    // Explanatory Modal Dialog
    if (showInfoDialog) {
      AlertDialog(
        onDismissRequest = { showInfoDialog = false },
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = MaroonDeep,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Delay Metric Calculation",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
          }
        },
        text = {
          Text(
            text = "Delay is measured at the final campus stop (Dhaka University Curzon Hall / Nilkhet). " +
                "It represents the exact difference between the official scheduled arrival time and the actual recorded GPS arrival time. " +
                "Daily stats update automatically after each completed shift.",
            fontSize = 13.sp,
            color = TextGrey,
            lineHeight = 18.sp
          )
        },
        confirmButton = {
          TextButton(onClick = { showInfoDialog = false }) {
            Text("Got it", color = MaroonDeep, fontWeight = FontWeight.Bold)
          }
        },
        containerColor = CardBg,
        shape = RoundedCornerShape(20.dp)
      )
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 1. BRAND HEADER (SADA SHUNDORI + Avatar Capsule matching 4_cd522351.jpeg)
// -------------------------------------------------------------------------------------------------
@Composable
private fun InsightsHeader(
  userProfile: UserProfile,
  onProfileClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Left: Brand Logo + Serif Title
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
    val view = LocalView.current
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = CardBg,
      shadowElevation = 2.dp,
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      modifier = Modifier
        .clickable {
          view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
          onProfileClick()
        }
        .testTag("insights_profile_button")
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
      ) {
        if (!userProfile.avatarUri.isNullOrBlank()) {
          Box(
            modifier = Modifier
              .size(22.dp)
              .clip(CircleShape),
            contentAlignment = Alignment.Center
          ) {
            AsyncImage(
              model = userProfile.avatarUri,
              contentDescription = "Profile",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
          }
        } else {
          Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = MaroonDeep,
            modifier = Modifier.size(20.dp)
          )
        }
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
// 2. DELAY HISTORY CARD (Matching Card 1 in 4_cd522351.jpeg)
// -------------------------------------------------------------------------------------------------
@Composable
private fun DelayHistoryCard(
  records: List<DayDelayItem>,
  selectedDay: DayDelayItem?,
  onSelectDay: (DayDelayItem) -> Unit,
  selectedDateRange: String,
  onOpenDateRangeMenu: () -> Unit,
  isDateRangeMenuExpanded: Boolean,
  onDismissDateRangeMenu: () -> Unit,
  onSelectDateRange: (String) -> Unit,
  onInfoClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Compute average and highest
  val averageDelay = remember(records) {
    if (records.isNotEmpty()) (records.sumOf { it.delayMin } / records.size) else 0
  }
  val highestRecord = remember(records) {
    records.maxByOrNull { it.delayMin }
  }

  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = CardBg),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("delay_history_card")
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      // Header Row: Title + Date Filter Dropdown
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column {
          Text(
            text = "Delay History",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(2.dp))
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onInfoClick() }
          ) {
            Text(
              text = "Minutes late at final stop",
              fontSize = 12.sp,
              color = TextGrey
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = "Info",
              tint = TextMutedLight,
              modifier = Modifier.size(14.dp)
            )
          }
        }

        // Date Range Selector Pill with Dropdown Menu
        Box {
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = NoticeBg,
            border = androidx.compose.foundation.BorderStroke(0.8.dp, CardBorderColor),
            modifier = Modifier
              .clickable { onOpenDateRangeMenu() }
              .testTag("date_range_picker")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = TextDark,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(5.dp))
              Text(
                text = selectedDateRange,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                letterSpacing = 0.5.sp
              )
              Spacer(modifier = Modifier.width(3.dp))
              Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Expand",
                tint = TextDark,
                modifier = Modifier.size(16.dp)
              )
            }
          }

          DropdownMenu(
            expanded = isDateRangeMenuExpanded,
            onDismissRequest = onDismissDateRangeMenu
          ) {
            listOf("PREV. 7 DAYS", "LAST 14 DAYS", "OCTOBER 2026", "SEMESTER ARCHIVE").forEach { option ->
              DropdownMenuItem(
                text = { Text(option, fontSize = 12.sp) },
                onClick = { onSelectDateRange(option) }
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Bar Chart Container
      DelayBarChart(
        records = records,
        selectedDay = selectedDay,
        onSelectDay = onSelectDay
      )

      // Selected Day Banner (if clicked)
      selectedDay?.let { day ->
        Spacer(modifier = Modifier.height(14.dp))
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = if (day.isPeak) BadgeBg else NoticeBg,
          border = androidx.compose.foundation.BorderStroke(
            0.8.dp,
            if (day.isPeak) MaroonPeak.copy(alpha = 0.3f) else CardBorderColor
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(if (day.isPeak) MaroonPeak else RoseBar)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "${day.day}, ${day.date} — +${day.delayMin} min delay",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (day.isPeak) MaroonPeak else TextDark
              )
              Text(
                text = day.detailsNote,
                fontSize = 10.5.sp,
                color = TextGrey,
                lineHeight = 14.sp
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Average Delay & Highest Delay Stat Cards (Two side-by-side pills)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Average delay card
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = NoticeBg,
          border = androidx.compose.foundation.BorderStroke(0.8.dp, CardBorderColor),
          modifier = Modifier.weight(1f)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = CircleShape,
              color = Color.White,
              border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
              modifier = Modifier.size(36.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.Schedule,
                  contentDescription = null,
                  tint = TextDark,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Average delay",
                fontSize = 11.sp,
                color = TextGrey
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "$averageDelay min",
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                color = TextDark
              )
            }
          }
        }

        // Highest delay card
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = NoticeBg,
          border = androidx.compose.foundation.BorderStroke(0.8.dp, CardBorderColor),
          modifier = Modifier.weight(1f)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = CircleShape,
              color = Color.White,
              border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
              modifier = Modifier.size(36.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.TrendingUp,
                  contentDescription = null,
                  tint = MaroonPeak,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Highest delay",
                fontSize = 11.sp,
                color = TextGrey
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "${highestRecord?.delayMin ?: 108} min",
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                color = TextDark
              )
              Text(
                text = "${highestRecord?.day ?: "Sat"}, ${highestRecord?.date ?: "18 Oct"}",
                fontSize = 9.5.sp,
                color = TextGrey
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Footnote
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp)
      ) {
        Box(
          modifier = Modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(TextMutedLight)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Delays are measured at final stop arrival. Data updates daily.",
          fontSize = 11.sp,
          color = TextGrey
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// CUSTOM DELAY BAR CHART (Canvas with Guidelines & Exact Pill Bars matching 4_cd522351.jpeg)
// -------------------------------------------------------------------------------------------------
@Composable
private fun DelayBarChart(
  records: List<DayDelayItem>,
  selectedDay: DayDelayItem?,
  onSelectDay: (DayDelayItem) -> Unit,
  modifier: Modifier = Modifier
) {
  val yLabels = listOf("120 min", "90 min", "60 min", "30 min", "0 min")
  val maxDelayScale = 120f

  Column(modifier = modifier.fillMaxWidth()) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(190.dp)
    ) {
      // 1. Background Grid Lines & Y-axis labels
      Row(modifier = Modifier.fillMaxSize()) {
        // Y-axis label column
        Column(
          modifier = Modifier
            .width(50.dp)
            .height(160.dp),
          verticalArrangement = Arrangement.SpaceBetween
        ) {
          yLabels.forEach { label ->
            Text(
              text = label,
              fontSize = 9.5.sp,
              color = TextGrey,
              textAlign = TextAlign.Start
            )
          }
        }

        // Guidelines & Bars Canvas Area
        Box(
          modifier = Modifier
            .weight(1f)
            .height(160.dp)
        ) {
          val guideCol = GuidelineColor
          // Horizontal guideline lines
          Canvas(modifier = Modifier.fillMaxSize()) {
            val steps = 4 // 120, 90, 60, 30, 0
            val rowHeight = size.height / steps
            for (i in 0..steps) {
              val y = i * rowHeight
              drawLine(
                color = guideCol,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
              )
            }
          }

          // Bars Row
          Row(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
          ) {
            records.forEach { item ->
              val isSelected = selectedDay?.day == item.day
              val barFraction = (item.delayMin / maxDelayScale).coerceIn(0.08f, 1f)

              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                  .weight(1f)
                  .clickable { onSelectDay(item) }
                  .testTag("bar_${item.day}")
              ) {
                // Number label on top of bar (e.g. +42, +108)
                Text(
                  text = "+${item.delayMin}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (item.isPeak) MaroonPeak else TextDark
                )
                Spacer(modifier = Modifier.height(4.dp))

                // The Pill Bar
                Box(
                  modifier = Modifier
                    .width(28.dp)
                    .height((130 * barFraction).dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(
                      when {
                        item.isPeak -> MaroonPeak
                        isSelected -> RoseBarSelected
                        else -> RoseBar
                      }
                    )
                )
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    // X-axis Day & Date labels below the chart
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = 50.dp, end = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      records.forEach { item ->
        val isSelected = selectedDay?.day == item.day
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .weight(1f)
            .clickable { onSelectDay(item) }
        ) {
          Text(
            text = item.day,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (item.isPeak) MaroonPeak else if (isSelected) MaroonDeep else TextDark
          )
          Text(
            text = item.date,
            fontSize = 9.5.sp,
            color = TextGrey
          )
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 3. JOURNEY DETAILS CARD (Matching Card 2 in 4_cd522351.jpeg)
// -------------------------------------------------------------------------------------------------
@Composable
private fun JourneyDetailsCard(
  selectedDay: DayDelayItem?,
  modifier: Modifier = Modifier
) {
  val todayDateText = remember {
    java.text.SimpleDateFormat("dd MMM yyyy (EEE)", java.util.Locale.ENGLISH).format(java.util.Date())
  }
  val delayText = selectedDay?.let { "+${it.delayMin} min" } ?: "+71 min"

  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = CardBg),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("journey_details_card")
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      // Header: "Journey Details" + "✔ LATEST COMPLETED" Pill Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Journey Details",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark
        )

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = BadgeBg,
          border = androidx.compose.foundation.BorderStroke(1.dp, MaroonPeak.copy(alpha = 0.4f))
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              tint = MaroonPeak,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "LATEST COMPLETED",
              fontSize = 9.5.sp,
              fontWeight = FontWeight.Bold,
              color = MaroonPeak,
              letterSpacing = 0.5.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 2-Column Table of Details with Dividers
      // Row 1: Route & Journey Date
      Row(modifier = Modifier.fillMaxWidth()) {
        DetailColumnItem(
          icon = Icons.Default.LocationOn,
          label = "Route",
          value = "Boardbazar to Campus",
          modifier = Modifier.weight(1f)
        )
        DetailColumnItem(
          icon = Icons.Default.DateRange,
          label = "Journey Date",
          value = todayDateText,
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(0.8.dp)
          .background(CardBorderColor)
      )
      Spacer(modifier = Modifier.height(10.dp))

      // Row 2: Departure Time & Total Distance (spec: '06:55 AM' and '28 km')
      Row(modifier = Modifier.fillMaxWidth()) {
        DetailColumnItem(
          icon = Icons.Default.Schedule,
          label = "Departure Time",
          value = "06:55 AM",
          modifier = Modifier.weight(1f)
        )
        DetailColumnItem(
          icon = Icons.Default.DirectionsBus,
          label = "Total Distance",
          value = "28 km",
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(0.8.dp)
          .background(CardBorderColor)
      )
      Spacer(modifier = Modifier.height(10.dp))

      // Row 3: Total Duration & Delay (keeps delay history and minutes late statistics active)
      Row(modifier = Modifier.fillMaxWidth()) {
        DetailColumnItem(
          icon = Icons.Default.HourglassBottom,
          label = "Total Duration",
          value = "1 h 52 min",
          modifier = Modifier.weight(1f)
        )
        DetailColumnItem(
          icon = Icons.Default.Speed,
          label = "Delay",
          value = delayText,
          valueColor = MaroonPeak,
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

@Composable
private fun DetailColumnItem(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  value: String,
  valueColor: Color = TextDark,
  modifier: Modifier = Modifier
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.padding(vertical = 4.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = TextGrey,
      modifier = Modifier.size(16.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column {
      Text(
        text = label,
        fontSize = 11.sp,
        color = TextGrey
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        fontSize = 13.5.sp,
        fontWeight = FontWeight.Bold,
        color = valueColor
      )
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 4. NOTICE CARD (Matching Card 3 in 4_cd522351.jpeg)
// -------------------------------------------------------------------------------------------------
@Composable
private fun RealTimeNoticeCard(modifier: Modifier = Modifier) {
  Surface(
    shape = RoundedCornerShape(18.dp),
    color = NoticeBg,
    border = androidx.compose.foundation.BorderStroke(0.8.dp, CardBorderColor),
    modifier = modifier
      .fillMaxWidth()
      .testTag("real_time_tracking_notice_card")
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Surface(
        shape = CircleShape,
        color = CardBg,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        modifier = Modifier.size(32.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = TextGrey,
            modifier = Modifier.size(16.dp)
          )
        }
      }
      Spacer(modifier = Modifier.width(12.dp))
      Text(
        text = "This app shows real-time tracking only. Historical journey records are not stored yet.",
        fontSize = 12.sp,
        color = TextGrey,
        lineHeight = 16.sp
      )
    }
  }
}
