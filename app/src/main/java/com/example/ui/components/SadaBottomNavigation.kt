package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppScreen
import com.example.ui.theme.SadaTheme
import com.example.ui.util.HapticHelper
import com.example.ui.util.HapticType

/**
 * Centralized, standardized application-wide bottom navigation bar.
 *
 * Guarantees rock-solid consistency across every screen:
 * - Exactly identical icon choices, shapes, sizes (24dp), and weights.
 * - Perfectly aligned 48dp container heights and centered horizontal baselines.
 * - Identical vertical spacing and typography across all states.
 * - Non-shifting 3dp indicator bar (transparent in inactive state, preventing layout jitter).
 * - Flush attachment to bottom system bounds with internal navigationBarsPadding.
 * - Unified native tactile haptic feedback on genuine user interaction.
 */
@Composable
fun SadaBottomNavigation(
  activeScreen: AppScreen,
  onNavigate: (AppScreen) -> Unit,
  modifier: Modifier = Modifier
) {
  val isDark = SadaTheme.isDark
  val colors = SadaTheme.colors
  val view = LocalView.current

  // Standardized dynamic colors
  val cardBg = colors.cardBackground
  val cardBorder = colors.cardBorder
  val activeColor = if (isDark) Color(0xFFEF4444) else colors.maroonPrimary
  val inactiveColor = colors.textMuted
  val indicatorActiveColor = if (isDark) Color(0xFFEF4444) else colors.maroonPrimary

  Surface(
    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    color = cardBg,
    shadowElevation = if (isDark) 16.dp else 14.dp,
    border = BorderStroke(1.dp, cardBorder),
    modifier = modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // 1. LIVE LOCATION (Standardized Tab 1)
        NavigationTabItem(
          icon = Icons.Default.LocationOn,
          label = "Live Location",
          isSelected = activeScreen == AppScreen.DASHBOARD,
          activeColor = activeColor,
          inactiveColor = inactiveColor,
          indicatorColor = indicatorActiveColor,
          testTag = "nav_live_location",
          modifier = Modifier.weight(1f),
          onClick = {
            if (activeScreen != AppScreen.DASHBOARD) {
              HapticHelper.trigger(view, HapticType.MEDIUM)
              onNavigate(AppScreen.DASHBOARD)
            }
          }
        )

        // 2. BOARDING (Standardized Center Elevated Button)
        CenterBoardingTabItem(
          icon = Icons.Default.DirectionsBus,
          label = "Boarding",
          isSelected = activeScreen == AppScreen.BOARDING || activeScreen == AppScreen.PICKUP_FINDER,
          isDark = isDark,
          activeColor = activeColor,
          inactiveColor = inactiveColor,
          indicatorColor = indicatorActiveColor,
          testTag = "nav_boarding_center",
          modifier = Modifier.weight(1f),
          onClick = {
            if (activeScreen != AppScreen.BOARDING && activeScreen != AppScreen.PICKUP_FINDER) {
              HapticHelper.trigger(view, HapticType.MEDIUM)
              onNavigate(AppScreen.PICKUP_FINDER)
            }
          }
        )

        // 3. INSIGHTS (Standardized Tab 3)
        NavigationTabItem(
          icon = Icons.Default.BarChart,
          label = "Insights",
          isSelected = activeScreen == AppScreen.INSIGHTS,
          activeColor = activeColor,
          inactiveColor = inactiveColor,
          indicatorColor = indicatorActiveColor,
          testTag = "nav_insights",
          modifier = Modifier.weight(1f),
          onClick = {
            if (activeScreen != AppScreen.INSIGHTS) {
              HapticHelper.trigger(view, HapticType.MEDIUM)
              onNavigate(AppScreen.INSIGHTS)
            }
          }
        )
      }
    }
  }
}

/**
 * Standard regular navigation item (Live Location and Insights).
 * Strictly maintains 48dp top container height to align with center button.
 */
@Composable
private fun NavigationTabItem(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  activeColor: Color,
  inactiveColor: Color,
  indicatorColor: Color,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val contentColor = if (isSelected) activeColor else inactiveColor
  val interactionSource = remember { MutableInteractionSource() }

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = modifier
      .clickable(
        interactionSource = interactionSource,
        indication = ripple(bounded = false, radius = 28.dp),
        role = Role.Tab,
        onClick = onClick
      )
      .padding(vertical = 4.dp)
      .testTag(testTag)
  ) {
    // 48dp Container: perfectly aligns centerline with the center 48dp button
    Box(
      modifier = Modifier.size(48.dp),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = contentColor,
        modifier = Modifier.size(24.dp)
      )
    }

    Spacer(modifier = Modifier.height(2.dp))

    // Label
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      color = contentColor,
      maxLines = 1
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Non-shifting active indicator pill
    Box(
      modifier = Modifier
        .width(26.dp)
        .height(3.dp)
        .background(
          color = if (isSelected) indicatorColor else Color.Transparent,
          shape = RoundedCornerShape(2.dp)
        )
    )
  }
}

/**
 * Standard center elevated boarding button.
 * Uses exact 48dp circle, matching icon size (24dp), identical typography and non-shifting indicator.
 */
@Composable
private fun CenterBoardingTabItem(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  isDark: Boolean,
  activeColor: Color,
  inactiveColor: Color,
  indicatorColor: Color,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }
  val labelColor = if (isSelected) activeColor else inactiveColor

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = modifier
      .clickable(
        interactionSource = interactionSource,
        indication = ripple(bounded = false, radius = 28.dp),
        role = Role.Tab,
        onClick = onClick
      )
      .padding(vertical = 4.dp)
      .testTag(testTag)
  ) {
    // Exactly 48dp circular elevated button
    Box(
      modifier = Modifier
        .size(48.dp)
        .shadow(
          elevation = if (isSelected) 8.dp else 4.dp,
          shape = CircleShape
        )
        .border(
          width = 1.5.dp,
          color = if (isSelected) {
            if (isDark) Color(0xFFEF4444).copy(alpha = 0.9f) else Color.White
          } else {
            if (isDark) Color(0xFF4A1822) else Color(0xFF8B2535)
          },
          shape = CircleShape
        )
        .background(
          brush = Brush.verticalGradient(
            colors = if (isSelected) {
              listOf(Color(0xFF8B1425), Color(0xFF4A0A14))
            } else {
              listOf(Color(0xFF560C17), Color(0xFF38050D))
            }
          ),
          shape = CircleShape
        ),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = Color.White,
        modifier = Modifier.size(24.dp)
      )
    }

    Spacer(modifier = Modifier.height(2.dp))

    // Label
    Text(
      text = label,
      fontSize = 11.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      color = labelColor,
      maxLines = 1
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Non-shifting active indicator pill
    Box(
      modifier = Modifier
        .width(26.dp)
        .height(3.dp)
        .background(
          color = if (isSelected) indicatorColor else Color.Transparent,
          shape = RoundedCornerShape(2.dp)
        )
    )
  }
}
