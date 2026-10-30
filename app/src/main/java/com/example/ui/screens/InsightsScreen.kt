package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DelayInsight
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentGold
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.BusWhite
import com.example.ui.theme.MaroonContainer
import com.example.ui.theme.MaroonDark
import com.example.ui.theme.MaroonGlow
import com.example.ui.theme.MaroonPrimary
import com.example.ui.theme.OutlineDark
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.StatusRed
import com.example.ui.theme.StatusYellow
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.BusViewModel

@Composable
fun InsightsScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val insights by viewModel.delayInsights.collectAsState()
  val bottlenecks by viewModel.bottlenecks.collectAsState()
  var selectedDay by remember { mutableStateOf<DelayInsight?>(insights.getOrNull(1)) } // Monday default

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundDark)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))

      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Transit Insights & Delay History",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Text(
            text = "Weekly commute analytics & chokepoint trends",
            fontSize = 12.sp,
            color = TextSecondary
          )
        }

        Surface(
          color = SurfaceDark,
          shape = RoundedCornerShape(12.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Timeline,
              contentDescription = null,
              tint = MaroonGlow,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Fleet SS-01",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = AccentGold
            )
          }
        }
      }
    }

    // Key Stat Metrics (4 Cards)
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Punctuality Card
        Card(
          colors = CardDefaults.cardColors(containerColor = SurfaceDark),
          shape = RoundedCornerShape(16.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "Punctuality",
              fontSize = 11.sp,
              color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "86.4%",
              fontSize = 22.sp,
              fontWeight = FontWeight.Black,
              color = StatusGreen
            )
            Text(
              text = "+4.2% vs last term",
              fontSize = 9.sp,
              color = TextMuted
            )
          }
        }

        // Avg Trip Duration Card
        Card(
          colors = CardDefaults.cardColors(containerColor = SurfaceDark),
          shape = RoundedCornerShape(16.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "Avg Trip Duration",
              fontSize = 11.sp,
              color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "54 min",
              fontSize = 22.sp,
              fontWeight = FontWeight.Black,
              color = AccentGold
            )
            Text(
              text = "Gazipur to Curzon",
              fontSize = 9.sp,
              color = TextMuted
            )
          }
        }
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Average Delay Card
        Card(
          colors = CardDefaults.cardColors(containerColor = SurfaceDark),
          shape = RoundedCornerShape(16.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "Avg Delay Impact",
              fontSize = 11.sp,
              color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "+14.8 m",
              fontSize = 22.sp,
              fontWeight = FontWeight.Black,
              color = MaroonGlow
            )
            Text(
              text = "Peak Thursday rush",
              fontSize = 9.sp,
              color = TextMuted
            )
          }
        }

        // Logged Student Rides Card
        Card(
          colors = CardDefaults.cardColors(containerColor = SurfaceDark),
          shape = RoundedCornerShape(16.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "Your Rides Taken",
              fontSize = 11.sp,
              color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "64 rides",
              fontSize = 22.sp,
              fontWeight = FontWeight.Black,
              color = BusWhite
            )
            Text(
              text = "Spring Semester '26",
              fontSize = 9.sp,
              color = TextMuted
            )
          }
        }
      }
    }

    // Weekly Delay Canvas Chart Card
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaroonDark),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("weekly_delay_chart_card")
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.BarChart,
                contentDescription = null,
                tint = MaroonGlow,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Weekly Delay Breakdown",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
            }

            // Legend
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(MaroonPrimary)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Morning", fontSize = 10.sp, color = TextSecondary)
              Spacer(modifier = Modifier.width(10.dp))
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(AccentAmber)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "Evening", fontSize = 10.sp, color = TextSecondary)
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Canvas Bar Chart
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(180.dp)
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val width = size.width
              val height = size.height
              val bottomPadding = 30.dp.toPx()
              val topPadding = 10.dp.toPx()
              val usableHeight = height - bottomPadding - topPadding
              val maxDelay = 40f // scale reference in minutes

              val barGroupWidth = width / insights.size

              // Baseline horizontal axis line
              drawLine(
                color = OutlineDark,
                start = Offset(0f, height - bottomPadding),
                end = Offset(width, height - bottomPadding),
                strokeWidth = 1.dp.toPx()
              )

              // Draw bars for each day
              insights.forEachIndexed { i, item ->
                val groupCenterX = i * barGroupWidth + barGroupWidth / 2f
                val barWidth = 10.dp.toPx()
                val gap = 4.dp.toPx()

                // Morning Bar (MaroonPrimary)
                val morningHeight = (item.morningDelayMin / maxDelay) * usableHeight
                val morningX = groupCenterX - barWidth - (gap / 2f)
                val morningY = height - bottomPadding - morningHeight

                drawRoundRect(
                  color = MaroonPrimary,
                  topLeft = Offset(morningX, morningY),
                  size = Size(barWidth, morningHeight),
                  cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )

                // Evening Bar (AccentAmber)
                val eveningHeight = (item.eveningDelayMin / maxDelay) * usableHeight
                val eveningX = groupCenterX + (gap / 2f)
                val eveningY = height - bottomPadding - eveningHeight

                drawRoundRect(
                  color = AccentAmber,
                  topLeft = Offset(eveningX, eveningY),
                  size = Size(barWidth, eveningHeight),
                  cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )
              }
            }

            // Labels below the bars
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp),
              horizontalArrangement = Arrangement.SpaceAround
            ) {
              insights.forEach { item ->
                val isSelected = selectedDay?.day == item.day
                Text(
                  text = item.day,
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) AccentGold else TextSecondary,
                  modifier = Modifier.clickable { selectedDay = item }
                )
              }
            }
          }

          // Active Selected Day Detail Banner
          selectedDay?.let { dayInsight ->
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
              color = MaroonContainer.copy(alpha = 0.35f),
              shape = RoundedCornerShape(12.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, MaroonDark),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = "${dayInsight.day}: Morning ${dayInsight.morningDelayMin}m • Evening ${dayInsight.eveningDelayMin}m delay",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = BusWhite
                  )
                  Text(
                    text = dayInsight.trafficNote,
                    fontSize = 11.sp,
                    color = TextSecondary
                  )
                }

                Surface(
                  color = if (dayInsight.punctualityPercent >= 85) StatusGreen else StatusYellow,
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = "${dayInsight.punctualityPercent}% on-time",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = BackgroundDark,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }
            }
          }
        }
      }
    }

    // Top Bottleneck Zones
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = null,
              tint = AccentAmber,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Major Corridor Bottlenecks",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          bottlenecks.forEachIndexed { index, zone ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = zone.name,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = TextPrimary
                )
                Text(
                  text = "${zone.location} • Rush: ${zone.peakHours}",
                  fontSize = 11.sp,
                  color = TextMuted
                )
              }

              Surface(
                color = when (zone.severity) {
                  "High" -> StatusRed.copy(alpha = 0.2f)
                  "Medium" -> AccentAmber.copy(alpha = 0.2f)
                  else -> StatusGreen.copy(alpha = 0.2f)
                },
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(
                  0.5.dp,
                  when (zone.severity) {
                    "High" -> StatusRed
                    "Medium" -> AccentAmber
                    else -> StatusGreen
                  }
                )
              ) {
                Text(
                  text = "+${zone.typicalDelayMin} min",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = when (zone.severity) {
                    "High" -> StatusRed
                    "Medium" -> AccentAmber
                    else -> StatusGreen
                  },
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }

            if (index < bottlenecks.size - 1) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(0.5.dp)
                  .background(OutlineDark)
              )
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}
