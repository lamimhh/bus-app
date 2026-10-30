package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.R
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BusStop
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
import com.example.ui.theme.StatusYellow
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.BusViewModel

@Composable
fun PickupFinderScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val searchQuery by viewModel.pickupSearchQuery.collectAsState()
  val filteredStops by viewModel.filteredStops.collectAsState()
  val busStatus by viewModel.busStatus.collectAsState()

  var selectedZoneFilter by remember { mutableStateOf("All") }

  val zoneFilteredStops = remember(filteredStops, selectedZoneFilter) {
    when (selectedZoneFilter) {
      "Gazipur & Tongi" -> filteredStops.filter { it.distanceFromStartKm <= 15.0 }
      "Uttara & Airport" -> filteredStops.filter { it.distanceFromStartKm > 15.0 && it.distanceFromStartKm <= 26.0 }
      "Mohakhali & South" -> filteredStops.filter { it.distanceFromStartKm > 26.0 }
      else -> filteredStops
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundDark)
      .padding(horizontal = 16.dp)
  ) {
    Spacer(modifier = Modifier.height(10.dp))

    // Header Title
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Boarding & Pickup Finder",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        Text(
          text = "Find stops, real-time arrival estimates & landmarks",
          fontSize = 12.sp,
          color = TextSecondary
        )
      }

      Surface(
        color = MaroonDark,
        shape = RoundedCornerShape(12.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.sada_shundori_icon),
            contentDescription = null,
            modifier = Modifier
              .size(18.dp)
              .clip(CircleShape)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Route SS-01",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = BusWhite
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Search Box
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { viewModel.setPickupSearchQuery(it) },
      placeholder = { Text("Search stop, e.g. Tongi, Airport, Nilkhet...") },
      singleLine = true,
      leadingIcon = {
        Icon(Icons.Default.Search, contentDescription = null, tint = MaroonGlow)
      },
      trailingIcon = {
        if (searchQuery.isNotEmpty()) {
          IconButton(onClick = { viewModel.setPickupSearchQuery("") }) {
            Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = TextMuted)
          }
        }
      },
      shape = RoundedCornerShape(14.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaroonPrimary,
        unfocusedBorderColor = OutlineDark,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        focusedContainerColor = SurfaceDark,
        unfocusedContainerColor = SurfaceDark
      ),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("pickup_search_field")
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Zone Filter Chips
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      val zones = listOf("All", "Gazipur & Tongi", "Uttara & Airport", "Mohakhali & South")
      items(zones) { zone ->
        val isSelected = selectedZoneFilter == zone
        Surface(
          color = if (isSelected) MaroonPrimary else SurfaceElevated,
          shape = RoundedCornerShape(20.dp),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) MaroonGlow else OutlineDark
          ),
          modifier = Modifier.clickable { selectedZoneFilter = zone }
        ) {
          Text(
            text = zone,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) BusWhite else TextSecondary,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Interactive Stylized Route Schematic Map Placeholder Canvas
    Card(
      colors = CardDefaults.cardColors(containerColor = SurfaceDark),
      shape = RoundedCornerShape(16.dp),
      border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
      modifier = Modifier
        .fillMaxWidth()
        .height(130.dp)
        .testTag("schematic_map_card")
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            Brush.verticalGradient(
              listOf(MaroonContainer.copy(alpha = 0.2f), SurfaceDark)
            )
          )
          .padding(12.dp)
      ) {
        Column(modifier = Modifier.fillMaxSize()) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Map,
                contentDescription = null,
                tint = AccentAmber,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Live Route Vector & Radar",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
            }
            Text(
              text = "Bus Position: Near Tongi Bridge",
              fontSize = 10.sp,
              color = StatusGreen,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Canvas Drawing: Animated Schematic Transit Line
          Canvas(
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f)
          ) {
            val canvasWidth = size.width
            val centerY = size.height / 2f

            // Background Route Track line
            drawLine(
              color = OutlineDark,
              start = Offset(20f, centerY),
              end = Offset(canvasWidth - 20f, centerY),
              strokeWidth = 6.dp.toPx()
            )

            // Traveled Section line (Green)
            val busProgressX = 20f + (canvasWidth - 40f) * 0.28f // 28% traveled
            drawLine(
              color = StatusGreen,
              start = Offset(20f, centerY),
              end = Offset(busProgressX, centerY),
              strokeWidth = 6.dp.toPx()
            )

            // Remaining Section line (Maroon)
            drawLine(
              color = MaroonPrimary.copy(alpha = 0.7f),
              start = Offset(busProgressX, centerY),
              end = Offset(canvasWidth - 20f, centerY),
              strokeWidth = 6.dp.toPx(),
              pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
            )

            // Draw Stop Nodes
            val stopFractions = listOf(0.0f, 0.12f, 0.28f, 0.45f, 0.65f, 0.85f, 1.0f)
            val stopNames = listOf("Gazipur", "Boardbazar", "Tongi", "Uttara", "Mohakhali", "Curzon", "Nilkhet")

            stopFractions.forEachIndexed { i, frac ->
              val nodeX = 20f + (canvasWidth - 40f) * frac
              val isBusNode = i == 2 // Tongi
              val isPast = frac < 0.28f

              drawCircle(
                color = when {
                  isBusNode -> MaroonGlow
                  isPast -> StatusGreen
                  else -> OutlineDark
                },
                radius = if (isBusNode) 8.dp.toPx() else 4.dp.toPx(),
                center = Offset(nodeX, centerY)
              )

              if (isBusNode) {
                // Outer glowing pulse ring
                drawCircle(
                  color = MaroonPrimary.copy(alpha = 0.4f),
                  radius = 14.dp.toPx(),
                  center = Offset(nodeX, centerY)
                )
              }
            }
          }

          // Stop Labels under schematic
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "Gazipur (0km)", fontSize = 9.sp, color = TextMuted)
            Text(text = "• Tongi (Bus Here)", fontSize = 9.sp, color = MaroonGlow, fontWeight = FontWeight.Bold)
            Text(text = "Nilkhet (43km)", fontSize = 9.sp, color = TextMuted)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // List of Pickup Stops
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(zoneFilteredStops, key = { it.id }) { stop ->
        val isTarget = stop.id == busStatus.targetStopId
        PickupStopItemCard(
          stop = stop,
          isTarget = isTarget,
          onSelectAsTarget = {
            viewModel.selectStop(stop.id)
          }
        )
      }

      item {
        Spacer(modifier = Modifier.height(20.dp))
      }
    }
  }
}

@Composable
private fun PickupStopItemCard(
  stop: BusStop,
  isTarget: Boolean,
  onSelectAsTarget: () -> Unit
) {
  Card(
    colors = CardDefaults.cardColors(
      containerColor = if (isTarget) MaroonContainer.copy(alpha = 0.4f) else SurfaceDark
    ),
    shape = RoundedCornerShape(16.dp),
    border = androidx.compose.foundation.BorderStroke(
      1.5.dp,
      if (isTarget) MaroonPrimary else OutlineDark
    ),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("stop_card_${stop.id}")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(
                when {
                  isTarget -> MaroonPrimary
                  stop.isPassed -> StatusGreen.copy(alpha = 0.2f)
                  else -> SurfaceElevated
                }
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (isTarget) Icons.Default.Star else Icons.Default.LocationOn,
              contentDescription = null,
              tint = when {
                isTarget -> BusWhite
                stop.isPassed -> StatusGreen
                else -> TextSecondary
              },
              modifier = Modifier.size(18.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = stop.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isTarget) AccentGold else TextPrimary
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = stop.banglaName,
                fontSize = 11.sp,
                color = TextMuted
              )
            }
            Text(
              text = stop.landmark,
              fontSize = 11.sp,
              color = TextSecondary,
              maxLines = 1
            )
          }
        }

        // Arrival Estimate Pill
        Surface(
          color = when {
            stop.isPassed -> SurfaceElevated
            isTarget -> MaroonPrimary
            else -> SurfaceElevated
          },
          shape = RoundedCornerShape(10.dp),
          border = androidx.compose.foundation.BorderStroke(
            0.5.dp,
            if (isTarget) MaroonGlow else OutlineDark
          )
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = if (stop.isPassed) "Passed" else "${stop.estimatedArrivalMin} min",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = when {
                stop.isPassed -> TextMuted
                isTarget -> BusWhite
                else -> MaroonGlow
              }
            )
            Text(
              text = "${stop.distanceFromStartKm} km",
              fontSize = 9.sp,
              color = TextMuted
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Timing Info and Target Stop Setter
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Schedule,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Morning: ${stop.morningSchedule}  •  Return: ${stop.afternoonSchedule}",
            fontSize = 10.sp,
            color = TextMuted
          )
        }

        if (!stop.isPassed) {
          Surface(
            color = if (isTarget) MaroonDark else SurfaceElevated,
            shape = RoundedCornerShape(8.dp),
            border = androidx.compose.foundation.BorderStroke(
              0.5.dp,
              if (isTarget) MaroonPrimary else OutlineDark
            ),
            modifier = Modifier.clickable { onSelectAsTarget() }
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              if (isTarget) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = null,
                  tint = AccentGold,
                  modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "My Target Stop",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = AccentGold
                )
              } else {
                Text(
                  text = "Set as My Stop",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Medium,
                  color = TextSecondary
                )
              }
            }
          }
        }
      }
    }
  }
}
