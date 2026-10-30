package com.example.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppScreen
import com.example.ui.screens.BrandSplashScreen
import com.example.ui.screens.CrewSectionScreen
import com.example.ui.screens.InsightsScreen
import com.example.ui.screens.LiveDashboardScreen
import com.example.ui.screens.NoticeAndComplaintScreen
import com.example.ui.screens.PickupFinderScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.WelcomeAuthScreen
import com.example.ui.theme.AccentGold
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.BusWhite
import com.example.ui.theme.MaroonDark
import com.example.ui.theme.MaroonGlow
import com.example.ui.theme.MaroonPrimary
import com.example.ui.theme.OutlineDark
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.BusViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val isLoggedIn by viewModel.isLoggedIn.collectAsState()

  // Handle system back navigation
  BackHandler(enabled = currentScreen != AppScreen.DASHBOARD && isLoggedIn) {
    viewModel.navigateBack()
  }

  val showBottomBar = currentScreen in listOf(
    AppScreen.PICKUP_FINDER,
    AppScreen.INSIGHTS,
    AppScreen.NOTICES,
    AppScreen.PROFILE
  )

  val showTopBar = currentScreen !in listOf(AppScreen.WELCOME, AppScreen.DASHBOARD)

  Scaffold(
    modifier = modifier.fillMaxSize(),
    containerColor = if (currentScreen == AppScreen.DASHBOARD) Color(0xFFF9F6F0) else BackgroundDark,
    topBar = {
      if (showTopBar) {
        TopAppBar(
          title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Image(
                painter = painterResource(id = R.drawable.sada_shundori_icon),
                contentDescription = "Sada Shundori Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .border(1.5.dp, MaroonGlow, CircleShape)
              )
              Spacer(modifier = Modifier.width(10.dp))
              androidx.compose.foundation.layout.Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "SADA SHUNDORI",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    color = BusWhite
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "সাদা সুন্দরী",
                    fontSize = 11.sp,
                    color = MaroonGlow,
                    fontWeight = FontWeight.Bold
                  )
                }
                Text(
                  text = "DU 7 Colleges Fleet SS-01",
                  fontSize = 10.sp,
                  color = TextMuted
                )
              }
            }
          },
          navigationIcon = {
            if (currentScreen in listOf(AppScreen.CREW, AppScreen.BRAND_SPLASH)) {
              IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                  contentDescription = "Back",
                  tint = BusWhite
                )
              }
            }
          },
          actions = {
            // Quick button to Crew & Hotline
            IconButton(
              onClick = { viewModel.navigateTo(AppScreen.CREW) },
              modifier = Modifier.testTag("nav_crew_topbar")
            ) {
              Icon(
                imageVector = Icons.Default.Group,
                contentDescription = "Meet the Crew",
                tint = if (currentScreen == AppScreen.CREW) AccentGold else TextSecondary
              )
            }

            // Quick button to Brand Showcase
            IconButton(
              onClick = { viewModel.navigateTo(AppScreen.BRAND_SPLASH) },
              modifier = Modifier.testTag("nav_splash_topbar")
            ) {
              Icon(
                imageVector = Icons.Default.Stars,
                contentDescription = "Brand Showcase",
                tint = if (currentScreen == AppScreen.BRAND_SPLASH) MaroonGlow else TextSecondary
              )
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SurfaceDark,
            titleContentColor = BusWhite,
            actionIconContentColor = TextSecondary
          )
        )
      }
    },
    bottomBar = {
      if (showBottomBar) {
        NavigationBar(
          containerColor = SurfaceDark,
          contentColor = BusWhite,
          tonalElevation = 8.dp,
          modifier = Modifier.testTag("bottom_nav_bar")
        ) {
          NavigationBarItem(
            selected = currentScreen == AppScreen.DASHBOARD,
            onClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
            icon = {
              Icon(Icons.Default.DirectionsBus, contentDescription = "Dashboard")
            },
            label = { Text("Live Tracker", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = BusWhite,
              selectedTextColor = MaroonGlow,
              indicatorColor = MaroonPrimary,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("bottom_nav_dashboard")
          )

          NavigationBarItem(
            selected = currentScreen == AppScreen.PICKUP_FINDER,
            onClick = { viewModel.navigateTo(AppScreen.PICKUP_FINDER) },
            icon = {
              Icon(Icons.Default.LocationOn, contentDescription = "Pickup Points")
            },
            label = { Text("Pickups", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = BusWhite,
              selectedTextColor = MaroonGlow,
              indicatorColor = MaroonPrimary,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("bottom_nav_pickups")
          )

          NavigationBarItem(
            selected = currentScreen == AppScreen.INSIGHTS,
            onClick = { viewModel.navigateTo(AppScreen.INSIGHTS) },
            icon = {
              Icon(Icons.Default.BarChart, contentDescription = "Insights")
            },
            label = { Text("Insights", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = BusWhite,
              selectedTextColor = MaroonGlow,
              indicatorColor = MaroonPrimary,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("bottom_nav_insights")
          )

          NavigationBarItem(
            selected = currentScreen == AppScreen.NOTICES,
            onClick = { viewModel.navigateTo(AppScreen.NOTICES) },
            icon = {
              Icon(Icons.Default.Assignment, contentDescription = "Notices")
            },
            label = { Text("Notices", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = BusWhite,
              selectedTextColor = MaroonGlow,
              indicatorColor = MaroonPrimary,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("bottom_nav_notices")
          )

          NavigationBarItem(
            selected = currentScreen == AppScreen.PROFILE,
            onClick = { viewModel.navigateTo(AppScreen.PROFILE) },
            icon = {
              Icon(Icons.Default.Person, contentDescription = "Profile Hub")
            },
            label = { Text("Pass Hub", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = BusWhite,
              selectedTextColor = MaroonGlow,
              indicatorColor = MaroonPrimary,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("bottom_nav_profile")
          )
        }
      }
    }
  ) { innerPadding ->
    AnimatedContent(
      targetState = currentScreen,
      transitionSpec = { fadeIn() togetherWith fadeOut() },
      label = "screen_transition",
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) { screen ->
      when (screen) {
        AppScreen.WELCOME -> WelcomeAuthScreen(viewModel = viewModel)
        AppScreen.DASHBOARD -> LiveDashboardScreen(viewModel = viewModel)
        AppScreen.PICKUP_FINDER, AppScreen.BOARDING -> PickupFinderScreen(viewModel = viewModel)
        AppScreen.INSIGHTS -> InsightsScreen(viewModel = viewModel)
        AppScreen.NOTICES -> NoticeAndComplaintScreen(viewModel = viewModel)
        AppScreen.PROFILE -> ProfileScreen(viewModel = viewModel)
        AppScreen.CREW -> CrewSectionScreen(viewModel = viewModel)
        AppScreen.BRAND_SPLASH -> BrandSplashScreen(viewModel = viewModel)
      }
    }
  }
}
