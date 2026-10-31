package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.model.AppScreen
import com.example.model.UserProfile
import com.example.viewmodel.BusViewModel

// Color palette matching 5_3f4a0c72.jpeg
private val ScreenBg = Color(0xFFFAF7F2)
private val ScreenBgEnd = Color(0xFFF3ECE4)
private val MaroonDeep = Color(0xFF4A0A12)
private val MaroonMid = Color(0xFF6B1120)
private val MaroonLight = Color(0xFF8B1E2F)
private val CardBorderColor = Color(0xFFEDE4DC)
private val TextDark = Color(0xFF1F1618)
private val TextGrey = Color(0xFF7A686D)
private val TextMutedLight = Color(0xFF9E8E92)
private val TileIconBg = Color(0xFFF6EFE8)
private val BadgeBg = Color(0xFFF8EBEB)
private val BadgeCountRed = Color(0xFF7E0E1C)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val userProfile by viewModel.userProfile.collectAsState()

  // 1. Dark Mode toggle state (Specification 1)
  var isDarkMode by remember { mutableStateOf(false) }

  // Modal / Sheet Dialog states
  var isControlPanelModalOpen by remember { mutableStateOf(false) }
  var isCommunityModalOpen by remember { mutableStateOf(false) }
  var isAboutModalOpen by remember { mutableStateOf(false) }
  var isSettingsSheetOpen by remember { mutableStateOf(false) }
  var isProblemReportOpen by remember { mutableStateOf(false) }
  var isHelpSearchOpen by remember { mutableStateOf(false) }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = if (isDarkMode) listOf(Color(0xFF1A1315), Color(0xFF110B0D)) else listOf(ScreenBg, ScreenBgEnd)
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
      // 1. TOP BAR: Brand Logo + Typography on left, Dark Mode Switch on right (Spec 1)
      item {
        Spacer(modifier = Modifier.height(4.dp))
        ProfileTopBar(
          isDarkMode = isDarkMode,
          onDarkModeToggle = {
            isDarkMode = it
            Toast.makeText(
              context,
              if (it) "Dark theme enabled" else "Light theme enabled",
              Toast.LENGTH_SHORT
            ).show()
          }
        )
      }

      // 2. PROFILE CARD: User photo, name (Lamim), department, session (2022-23), Gmail as fixed non-editable fields (Spec 2)
      item {
        FixedProfileCard(
          userProfile = userProfile,
          onVerifyInfoClick = {
            Toast.makeText(context, "Student profile verified by Dhaka College transport authority", Toast.LENGTH_SHORT).show()
          }
        )
      }

      // 3. BUS SERVICES: 4 Tiles -> Notice Box, Control Panel / Moderator (unrestricted), Complaint Box, Crew (Spec 3)
      item {
        BusServicesSection(
          onNoticeClick = { viewModel.navigateTo(AppScreen.NOTICES) },
          onControlPanelClick = { isControlPanelModalOpen = true },
          onComplaintBoxClick = { viewModel.navigateTo(AppScreen.COMPLAINT_BOX) },
          onCrewClick = { viewModel.navigateTo(AppScreen.CREW) }
        )
      }

      // 4. COMMUNITY SECTION: Community and About tiles (Spec 4)
      item {
        CommunitySection(
          onCommunityClick = { viewModel.navigateTo(AppScreen.COMMUNITY) },
          onAboutClick = { isAboutModalOpen = true }
        )
      }

      // 5. PREFERENCES & SETTINGS: Settings (with Help, Search, Report a Problem), Dark Mode toggle, no Home item (Spec 5)
      item {
        PreferencesSection(
          isDarkMode = isDarkMode,
          onDarkModeToggle = {
            isDarkMode = it
            Toast.makeText(
              context,
              if (it) "Dark theme enabled" else "Light theme enabled",
              Toast.LENGTH_SHORT
            ).show()
          },
          onSettingsClick = { isSettingsSheetOpen = true }
        )
      }

      // Brand Footer
      item {
        BrandFooter()
      }

      // Extra space for the fixed "BACK TO HOME PAGE" button
      item {
        Spacer(modifier = Modifier.height(84.dp))
      }
    }

    // 6. BOTTOM NAVIGATION REPLACEMENT: Single full-width digital button 'BACK TO HOME PAGE' (Spec 6)
    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
      Button(
        onClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = MaroonDeep
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("btn_back_to_home_page")
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Default.Home,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "BACK TO HOME PAGE",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = Color.White
          )
        }
      }
    }

    // SADA BUS CONTROL PANEL / MODERATOR MODAL (Unrestricted, showing admin & moderator details - Spec 3b)
    if (isControlPanelModalOpen) {
      ModeratorControlPanelDialog(
        onDismiss = { isControlPanelModalOpen = false },
        onCall = { phone ->
          val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
          try { context.startActivity(dialIntent) } catch (_: Exception) {}
        }
      )
    }

    // COMMUNITY MODAL (Spec 4)
    if (isCommunityModalOpen) {
      CommunityDialog(
        onDismiss = { isCommunityModalOpen = false },
        onOpenLink = { url ->
          try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
          } catch (_: Exception) {
            Toast.makeText(context, "Opening link: $url", Toast.LENGTH_SHORT).show()
          }
        }
      )
    }

    // ABOUT SADA SHUNDORI MODAL (Spec 4)
    if (isAboutModalOpen) {
      AboutSadaShundoriDialog(
        onDismiss = { isAboutModalOpen = false }
      )
    }

    // SETTINGS MODAL BOTTOM SHEET (With Help, Search, Report a Problem options - Spec 5)
    if (isSettingsSheetOpen) {
      SettingsBottomSheet(
        onDismiss = { isSettingsSheetOpen = false },
        onOpenHelp = {
          isSettingsSheetOpen = false
          isHelpSearchOpen = true
        },
        onOpenSearch = {
          isSettingsSheetOpen = false
          isHelpSearchOpen = true
        },
        onReportProblem = {
          isSettingsSheetOpen = false
          isProblemReportOpen = true
        }
      )
    }

    // REPORT A PROBLEM DIALOG (Spec 5)
    if (isProblemReportOpen) {
      ReportProblemDialog(
        onDismiss = { isProblemReportOpen = false },
        onSubmit = { issueText ->
          isProblemReportOpen = false
          Toast.makeText(context, "Thank you! Diagnostic issue submitted successfully.", Toast.LENGTH_LONG).show()
        }
      )
    }

    // HELP & SEARCH FAQ DIALOG (Spec 5)
    if (isHelpSearchOpen) {
      HelpAndSearchDialog(
        onDismiss = { isHelpSearchOpen = false }
      )
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 1. TOP BAR (Dark Mode toggle switch instead of profile button - Spec 1)
// -------------------------------------------------------------------------------------------------
@Composable
private fun ProfileTopBar(
  isDarkMode: Boolean,
  onDarkModeToggle: (Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Left: Brand Logo + Serif Title
    Row(verticalAlignment = Alignment.CenterVertically) {
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

    // Right: Dark Mode Toggle Switch (Requirement 1)
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      shadowElevation = 2.dp,
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      modifier = Modifier.testTag("top_bar_dark_mode_switch")
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
      ) {
        Icon(
          imageVector = Icons.Default.DarkMode,
          contentDescription = "Dark Mode Toggle",
          tint = if (isDarkMode) MaroonDeep else TextGrey,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Switch(
          checked = isDarkMode,
          onCheckedChange = onDarkModeToggle,
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = MaroonDeep,
            uncheckedThumbColor = TextGrey,
            uncheckedTrackColor = Color(0xFFEDE4DC)
          ),
          modifier = Modifier.padding(start = 2.dp)
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 2. FIXED PROFILE CARD (Non-editable verified fields - Spec 2)
// -------------------------------------------------------------------------------------------------
@Composable
private fun FixedProfileCard(
  userProfile: UserProfile,
  onVerifyInfoClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("fixed_profile_card")
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // User Photo with Maroon Ring & Live Verified Indicator Dot
        Box(
          modifier = Modifier.size(80.dp),
          contentAlignment = Alignment.Center
        ) {
          // Circular avatar container
          Surface(
            shape = CircleShape,
            color = TileIconBg,
            border = androidx.compose.foundation.BorderStroke(2.dp, MaroonDeep),
            modifier = Modifier.size(76.dp)
          ) {
            Image(
              painter = painterResource(id = R.drawable.sada_shundori_icon),
              contentDescription = "User Photo",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
          }

          // Small maroon dot on right side as in 5_3f4a0c72.jpeg
          Box(
            modifier = Modifier
              .align(Alignment.CenterEnd)
              .size(10.dp)
              .clip(CircleShape)
              .background(MaroonDeep)
              .border(1.5.dp, Color.White, CircleShape)
          )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Fixed Non-editable User Fields (Spec 2)
        Column(modifier = Modifier.weight(1f)) {
          // Name: Lamim
          Text(
            text = userProfile.name, // "Lamim"
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )

          Spacer(modifier = Modifier.height(4.dp))

          // Department: Finance & Banking
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.School,
              contentDescription = null,
              tint = MaroonDeep,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = userProfile.department, // "Finance & Banking"
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium,
              color = TextDark
            )
          }

          Spacer(modifier = Modifier.height(3.dp))

          // Session: Session 2022–23
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.DateRange,
              contentDescription = null,
              tint = TextGrey,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Session ${userProfile.session}", // "Session 2022-23"
              fontSize = 12.5.sp,
              color = TextGrey
            )
          }

          Spacer(modifier = Modifier.height(3.dp))

          // Gmail Account
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Email,
              contentDescription = null,
              tint = TextGrey,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = userProfile.email, // "hafizhossainlamim@gmail.com"
              fontSize = 11.5.sp,
              color = TextGrey
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Verified Badge Pill (Indicating non-editable verified credentials)
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = TileIconBg,
        border = androidx.compose.foundation.BorderStroke(0.8.dp, CardBorderColor),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onVerifyInfoClick() }
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = null,
              tint = MaroonDeep,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Verified Student Pass • DU 7 Colleges",
              fontSize = 11.5.sp,
              fontWeight = FontWeight.Bold,
              color = MaroonDeep
            )
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(0.5.dp, CardBorderColor)
          ) {
            Text(
              text = "Fixed ID",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = TextGrey,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 3. BUS SERVICES (4 TILES: Notice Box, Control Panel / Moderator, Complaint Box, Crew - Spec 3)
// -------------------------------------------------------------------------------------------------
@Composable
private fun BusServicesSection(
  onNoticeClick: () -> Unit,
  onControlPanelClick: () -> Unit,
  onComplaintBoxClick: () -> Unit,
  onCrewClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    // Section Header: Bus icon + BUS SERVICES title + Maroon underline
    SectionHeader(
      icon = Icons.Default.DirectionsBus,
      title = "BUS SERVICES"
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Card with 4 Tile rows matching 5_3f4a0c72.jpeg
    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        // Tile 1: Notice Box
        ProfileTileItem(
          icon = Icons.Outlined.Notifications,
          title = "Notice",
          subtitle = "Official announcements & updates",
          badgeCount = "3",
          onClick = onNoticeClick,
          testTag = "tile_notice_box"
        )

        Divider(color = CardBorderColor, thickness = 0.8.dp)

        // Tile 2: Sada Bus Control Panel / Moderator (Unrestricted - Spec 3b)
        ProfileTileItem(
          icon = Icons.Default.AdminPanelSettings,
          title = "Moderator",
          subtitle = "Management panel & moderators",
          rightLabel = "Open Access",
          onClick = onControlPanelClick,
          testTag = "tile_moderator_panel"
        )

        Divider(color = CardBorderColor, thickness = 0.8.dp)

        // Tile 3: Complaint Box
        ProfileTileItem(
          icon = Icons.Default.ChatBubbleOutline,
          title = "Complaint Box",
          subtitle = "Report issues & give feedback",
          onClick = onComplaintBoxClick,
          testTag = "tile_complaint_box"
        )

        Divider(color = CardBorderColor, thickness = 0.8.dp)

        // Tile 4: Crew
        ProfileTileItem(
          icon = Icons.Default.DirectionsBus,
          title = "Crew",
          subtitle = "Meet the Sada Shundori crew",
          onClick = onCrewClick,
          testTag = "tile_crew_details"
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 4. COMMUNITY SECTION (Community & About tiles - Spec 4)
// -------------------------------------------------------------------------------------------------
@Composable
private fun CommunitySection(
  onCommunityClick: () -> Unit,
  onAboutClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    // Section Header
    SectionHeader(
      icon = Icons.Default.Groups,
      title = "COMMUNITY"
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        // Tile 1: Community
        ProfileTileItem(
          icon = Icons.Outlined.Group,
          title = "Community",
          subtitle = "Join our passenger community",
          onClick = onCommunityClick,
          testTag = "tile_community"
        )

        Divider(color = CardBorderColor, thickness = 0.8.dp)

        // Tile 2: About
        ProfileTileItem(
          icon = Icons.Outlined.Info,
          title = "About",
          subtitle = "About Sada Shundori initiative",
          onClick = onAboutClick,
          testTag = "tile_about"
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 5. PREFERENCES SECTION (Settings, Dark Mode, without redundant Home item - Spec 5)
// -------------------------------------------------------------------------------------------------
@Composable
private fun PreferencesSection(
  isDarkMode: Boolean,
  onDarkModeToggle: (Boolean) -> Unit,
  onSettingsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    // Section Header
    SectionHeader(
      icon = Icons.Default.Settings,
      title = "PREFERENCES"
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        // Tile 1: Settings (Opens sheet with Help, Search, Report a Problem - Spec 5)
        ProfileTileItem(
          icon = Icons.Default.Settings,
          title = "Settings",
          subtitle = "Account & app preferences",
          onClick = onSettingsClick,
          testTag = "tile_preferences_settings"
        )

        Divider(color = CardBorderColor, thickness = 0.8.dp)

        // Tile 2: Dark Mode Row with inline Switch component (Spec 5)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onDarkModeToggle(!isDarkMode) }
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .testTag("tile_dark_mode_row"),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = CircleShape,
            color = TileIconBg,
            modifier = Modifier.size(42.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = null,
                tint = MaroonDeep,
                modifier = Modifier.size(20.dp)
              )
            }
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Dark Mode",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
            Text(
              text = "Switch between light & dark theme",
              fontSize = 12.sp,
              color = TextGrey
            )
          }

          Switch(
            checked = isDarkMode,
            onCheckedChange = onDarkModeToggle,
            colors = SwitchDefaults.colors(
              checkedThumbColor = Color.White,
              checkedTrackColor = MaroonDeep,
              uncheckedThumbColor = TextGrey,
              uncheckedTrackColor = Color(0xFFEDE4DC)
            )
          )
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// REUSABLE SECTION HEADER & TILE ITEM
// -------------------------------------------------------------------------------------------------
@Composable
private fun SectionHeader(
  icon: ImageVector,
  title: String,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Surface(
        shape = CircleShape,
        color = MaroonDeep,
        modifier = Modifier.size(24.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(14.dp)
          )
        }
      }
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = 1.sp,
        color = MaroonDeep
      )
    }
    Spacer(modifier = Modifier.height(3.dp))
    Box(
      modifier = Modifier
        .padding(start = 32.dp)
        .width(36.dp)
        .height(2.5.dp)
        .background(MaroonDeep, RoundedCornerShape(1.dp))
    )
  }
}

@Composable
private fun ProfileTileItem(
  icon: ImageVector,
  title: String,
  subtitle: String,
  badgeCount: String? = null,
  rightLabel: String? = null,
  onClick: () -> Unit,
  testTag: String = ""
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(horizontal = 16.dp, vertical = 14.dp)
      .testTag(testTag),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Surface(
      shape = CircleShape,
      color = TileIconBg,
      modifier = Modifier.size(42.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = MaroonDeep,
          modifier = Modifier.size(20.dp)
        )
      }
    }

    Spacer(modifier = Modifier.width(14.dp))

    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextDark
      )
      Text(
        text = subtitle,
        fontSize = 12.sp,
        color = TextGrey
      )
    }

    // Right accessories (Count badge or label)
    if (badgeCount != null) {
      Surface(
        shape = CircleShape,
        color = BadgeCountRed,
        modifier = Modifier.size(22.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Text(
            text = badgeCount,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      }
      Spacer(modifier = Modifier.width(6.dp))
    }

    if (rightLabel != null) {
      Surface(
        shape = RoundedCornerShape(6.dp),
        color = TileIconBg,
        border = androidx.compose.foundation.BorderStroke(0.5.dp, CardBorderColor)
      ) {
        Text(
          text = rightLabel,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = MaroonDeep,
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
      }
      Spacer(modifier = Modifier.width(6.dp))
    }

    Icon(
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = "Navigate",
      tint = TextGrey,
      modifier = Modifier.size(18.dp)
    )
  }
}

// -------------------------------------------------------------------------------------------------
// BRAND FOOTER (SAFE • RELIABLE • TOGETHER)
// -------------------------------------------------------------------------------------------------
@Composable
private fun BrandFooter(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Surface(
      shape = CircleShape,
      color = Color.White,
      border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
      modifier = Modifier.size(34.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = "S",
          fontFamily = FontFamily.Serif,
          fontSize = 16.sp,
          fontWeight = FontWeight.Black,
          color = MaroonDeep
        )
      }
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = "SAFE  •  RELIABLE  •  TOGETHER",
      fontSize = 9.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.2.sp,
      color = TextGrey
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = "Thank you for being with Sada Shundori ❤️",
      fontSize = 11.sp,
      color = TextGrey
    )
  }
}

// -------------------------------------------------------------------------------------------------
// SADA BUS CONTROL PANEL / MODERATOR MODAL (Unrestricted, showing admin and moderator details)
// -------------------------------------------------------------------------------------------------
@Composable
private fun ModeratorControlPanelDialog(
  onDismiss: () -> Unit,
  onCall: (String) -> Unit
) {
  val moderators = listOf(
    Triple("Prof. Dr. Rafiqul Islam", "Convener, DU 7 Colleges Transport Committee", "+880 1711-223344"),
    Triple("Shakil Ahmed", "Lead Route Controller & Student Convener", "+880 1612-445566"),
    Triple("Tanvir Chowdhury", "Central Fleet Dispatcher & GPS Monitoring", "+880 1819-556677"),
    Triple("Fahim Morshed", "Student Safety & Emergency Response Lead", "+880 1913-778899")
  )

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 16.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("moderator_control_dialog")
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        // Modal Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = CircleShape,
              color = MaroonDeep,
              modifier = Modifier.size(36.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.AdminPanelSettings,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Control Panel & Moderators",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Text(
                text = "Unrestricted Committee Roster",
                fontSize = 11.sp,
                color = MaroonDeep,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = TextGrey,
            modifier = Modifier
              .size(22.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "The Sada Shundori operations desk is openly accessible to all verified students and faculty. For schedule adjustments, route safety, or lost & found verification, contact the coordinators below:",
          fontSize = 12.sp,
          color = TextGrey,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          moderators.forEach { (name, role, phone) ->
            Surface(
              shape = RoundedCornerShape(14.dp),
              color = TileIconBg,
              border = androidx.compose.foundation.BorderStroke(0.8.dp, CardBorderColor),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = name,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                  )
                  Text(
                    text = role,
                    fontSize = 11.sp,
                    color = TextGrey
                  )
                  Text(
                    text = phone,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaroonDeep
                  )
                }

                Surface(
                  shape = CircleShape,
                  color = MaroonDeep,
                  modifier = Modifier
                    .size(34.dp)
                    .clickable { onCall(phone) }
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    Icon(
                      imageVector = Icons.Default.Call,
                      contentDescription = "Call",
                      tint = Color.White,
                      modifier = Modifier.size(16.dp)
                    )
                  }
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaroonDeep),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Done", fontWeight = FontWeight.Bold, color = Color.White)
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// COMMUNITY MODAL (Spec 4)
// -------------------------------------------------------------------------------------------------
@Composable
private fun CommunityDialog(
  onDismiss: () -> Unit,
  onOpenLink: (String) -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 16.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("community_dialog")
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = CircleShape,
              color = MaroonDeep,
              modifier = Modifier.size(36.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.People,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Passenger Community",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Text(
                text = "Dhaka University 7 Colleges",
                fontSize = 11.sp,
                color = TextGrey
              )
            }
          }

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = TextGrey,
            modifier = Modifier
              .size(22.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Connect with over 4,500 daily commuters across Dhaka College, Eden Mohila College, Titumir College, and other affiliated campuses for ride sharing, traffic alerts, and community meetups:",
          fontSize = 12.sp,
          color = TextGrey,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Community Channels
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          CommunityChannelItem(
            name = "Official Telegram Transit Feed",
            desc = "Real-time road warnings, weather alerts & delays",
            onClick = { onOpenLink("https://t.me/sadashundori_du7c") }
          )

          CommunityChannelItem(
            name = "Facebook Passenger Group",
            desc = "Community lost & found, exam special bus requests",
            onClick = { onOpenLink("https://facebook.com/groups/sadashundori") }
          )

          CommunityChannelItem(
            name = "Route WhatsApp Bulletin",
            desc = "Urgent road diversion notices by student marshals",
            onClick = { onOpenLink("https://chat.whatsapp.com/sadashundori") }
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaroonDeep),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Close", fontWeight = FontWeight.Bold, color = Color.White)
        }
      }
    }
  }
}

@Composable
private fun CommunityChannelItem(
  name: String,
  desc: String,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = TileIconBg,
    border = androidx.compose.foundation.BorderStroke(0.8.dp, CardBorderColor),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Text(text = desc, fontSize = 11.sp, color = TextGrey)
      }
      Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = "Open",
        tint = MaroonDeep,
        modifier = Modifier.size(18.dp)
      )
    }
  }
}

// -------------------------------------------------------------------------------------------------
// ABOUT SADA SHUNDORI MODAL (Spec 4)
// -------------------------------------------------------------------------------------------------
@Composable
private fun AboutSadaShundoriDialog(
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 16.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("about_dialog")
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = CircleShape,
              color = MaroonDeep,
              modifier = Modifier.size(36.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.Info,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "About Sada Shundori",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Text(
                text = "Campus Bus Companion • v2.4",
                fontSize = 11.sp,
                color = TextGrey
              )
            }
          }

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = TextGrey,
            modifier = Modifier
              .size(22.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "“Sada Shundori” (সাদা সুন্দরী) is the signature campus transit service dedicated to students of Dhaka University Affiliated 7 Government Colleges. " +
              "Spanning 18 essential stops from Gazipur National University Gate through Tongi, Uttara, and Airport down to the DU Curzon Hall & Nilkhet Campus.\n\n" +
              "Designed with love to ensure punctual, secure, and stress-free academic commutes with real-time GPS tracking and community updates.",
          fontSize = 12.sp,
          color = TextGrey,
          lineHeight = 17.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = TileIconBg,
          border = androidx.compose.foundation.BorderStroke(0.8.dp, CardBorderColor),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Fleet Identifier: SS-01 / Metro-Ba 15-8824", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = MaroonDeep)
            Text(text = "Operated under DU 7 Colleges Transport Syndicate", fontSize = 10.5.sp, color = TextGrey)
            Text(text = "Lead Developer: Hafiz Hossain Lamim (Finance & Banking)", fontSize = 10.5.sp, color = TextGrey)
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaroonDeep),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Got It", fontWeight = FontWeight.Bold, color = Color.White)
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// SETTINGS BOTTOM SHEET (With Help, Search, Report a Problem - Spec 5)
// -------------------------------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsBottomSheet(
  onDismiss: () -> Unit,
  onOpenHelp: () -> Unit,
  onOpenSearch: () -> Unit,
  onReportProblem: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 12.dp)
        .navigationBarsPadding()
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Preferences & Settings",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark
        )

        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Close",
          tint = TextGrey,
          modifier = Modifier
            .size(22.dp)
            .clickable { onDismiss() }
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 1. Search FAQs & Topics (Spec 5)
      SettingsOptionRow(
        icon = Icons.Outlined.Search,
        title = "Search",
        subtitle = "Search transit guides, stops & FAQ database",
        onClick = onOpenSearch
      )

      Divider(color = CardBorderColor, thickness = 0.8.dp)

      // 2. Help & Support (Spec 5)
      SettingsOptionRow(
        icon = Icons.Default.HelpOutline,
        title = "Help & Support",
        subtitle = "Frequently asked questions & student handbook",
        onClick = onOpenHelp
      )

      Divider(color = CardBorderColor, thickness = 0.8.dp)

      // 3. Report a Problem (Spec 5)
      SettingsOptionRow(
        icon = Icons.Default.ReportProblem,
        title = "Report a Problem",
        subtitle = "Submit app diagnostic report or bug notice",
        iconTint = MaroonDeep,
        onClick = onReportProblem
      )

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun SettingsOptionRow(
  icon: ImageVector,
  title: String,
  subtitle: String,
  iconTint: Color = MaroonDeep,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Surface(
      shape = CircleShape,
      color = TileIconBg,
      modifier = Modifier.size(38.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = iconTint,
          modifier = Modifier.size(18.dp)
        )
      }
    }

    Spacer(modifier = Modifier.width(14.dp))

    Column(modifier = Modifier.weight(1f)) {
      Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
      Text(text = subtitle, fontSize = 11.5.sp, color = TextGrey)
    }

    Icon(
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = null,
      tint = TextGrey,
      modifier = Modifier.size(18.dp)
    )
  }
}

// -------------------------------------------------------------------------------------------------
// REPORT A PROBLEM DIALOG (Spec 5)
// -------------------------------------------------------------------------------------------------
@Composable
private fun ReportProblemDialog(
  onDismiss: () -> Unit,
  onSubmit: (String) -> Unit
) {
  var problemDescription by remember { mutableStateOf("") }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 16.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = CircleShape,
              color = BadgeBg,
              modifier = Modifier.size(34.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.ReportProblem,
                  contentDescription = null,
                  tint = MaroonDeep,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Report a Problem",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
          }

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = TextGrey,
            modifier = Modifier
              .size(20.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Notice inaccurate GPS coordinates, slow updates, or app rendering issues? Let our dev team know:",
          fontSize = 12.sp,
          color = TextGrey
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = problemDescription,
          onValueChange = { problemDescription = it },
          placeholder = { Text("Describe the issue in detail...", fontSize = 12.sp) },
          minLines = 3,
          maxLines = 5,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = onDismiss,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TileIconBg),
            modifier = Modifier.weight(1f)
          ) {
            Text("Cancel", color = TextDark, fontWeight = FontWeight.SemiBold)
          }

          Button(
            onClick = { onSubmit(problemDescription) },
            enabled = problemDescription.isNotBlank(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaroonDeep),
            modifier = Modifier.weight(1f)
          ) {
            Text("Submit", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// HELP & SEARCH FAQ DIALOG (Spec 5)
// -------------------------------------------------------------------------------------------------
@Composable
private fun HelpAndSearchDialog(
  onDismiss: () -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }

  val faqs = listOf(
    "How is the live bus ETA calculated?" to "ETA is calculated directly using actual device GPS speed deltas and Google road network distances.",
    "Can other students broadcast bus locations?" to "Only authorized operators with password LAMIM01 can stream live driver telemetry.",
    "Where is the campus terminal?" to "Final stops are DU Curzon Hall (for science batches) and Nilkhet terminal.",
    "What is the Elevated Route toggle?" to "Used past Airport when taking the expressway directly to bypass Mohakhali traffic."
  )

  val filteredFaqs = remember(searchQuery) {
    if (searchQuery.isBlank()) faqs
    else faqs.filter { it.first.contains(searchQuery, ignoreCase = true) || it.second.contains(searchQuery, ignoreCase = true) }
  }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 16.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Help & FAQ Search",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = TextGrey,
            modifier = Modifier
              .size(20.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Search questions e.g. ETA, route...", fontSize = 12.sp) },
          leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = TextGrey, modifier = Modifier.size(18.dp))
          },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        Column(
          modifier = Modifier.height(240.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          filteredFaqs.forEach { (q, a) ->
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = TileIconBg,
              border = androidx.compose.foundation.BorderStroke(0.8.dp, CardBorderColor),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(text = q, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = MaroonDeep)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = a, fontSize = 11.sp, color = TextGrey, lineHeight = 14.sp)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaroonDeep),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Done", fontWeight = FontWeight.Bold, color = Color.White)
        }
      }
    }
  }
}
