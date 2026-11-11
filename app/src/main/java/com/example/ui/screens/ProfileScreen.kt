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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import coil.compose.AsyncImage
import com.example.model.AppLanguage
import com.example.model.BusRuleItem
import com.example.ui.util.AppStrings
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
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
import android.view.HapticFeedbackConstants
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
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
import com.example.ui.theme.SadaTheme
import com.example.viewmodel.BusViewModel
import com.example.viewmodel.ThemeViewModel

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
  themeViewModel: ThemeViewModel? = null,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val userProfile by viewModel.userProfile.collectAsState()
  val currentLanguage by viewModel.currentLanguage.collectAsState()

  // Photo Picker launcher for immediate device image selection
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    if (uri != null) {
      try {
        context.contentResolver.takePersistableUriPermission(
          uri,
          Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
      } catch (_: Exception) {}
      viewModel.updateAvatarUri(uri.toString(), context)
      Toast.makeText(
        context,
        if (currentLanguage.isBangla) "প্রোফাইল ছবি সফলভাবে আপডেট হয়েছে!" else "Profile photo updated successfully!",
        Toast.LENGTH_SHORT
      ).show()
    }
  }

  // 1. Dark Mode toggle state bound to global persistent ThemeViewModel
  val isDarkMode by (themeViewModel?.isDarkMode ?: viewModel.isDarkMode).collectAsState()

  val onThemeToggle: (Boolean) -> Unit = { enabled ->
    if (themeViewModel != null) {
      themeViewModel.setDarkMode(enabled)
    } else {
      viewModel.setDarkMode(enabled, context)
    }
    Toast.makeText(
      context,
      if (enabled) "Dark theme enabled" else "Light theme enabled",
      Toast.LENGTH_SHORT
    ).show()
  }

  // Modal / Sheet Dialog states
  var isControlPanelModalOpen by remember { mutableStateOf(false) }
  var isCommunityModalOpen by remember { mutableStateOf(false) }
  var isAboutModalOpen by remember { mutableStateOf(false) }
  var isSettingsSheetOpen by remember { mutableStateOf(false) }
  var isProblemReportOpen by remember { mutableStateOf(false) }
  var isHelpSearchOpen by remember { mutableStateOf(false) }
  var isLanguageDialogOpen by remember { mutableStateOf(false) }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = if (isDarkMode) listOf(Color(0xFF120A0D), Color(0xFF0A0507)) else listOf(ScreenBg, ScreenBgEnd)
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
      // 1. TOP BAR: Brand Logo + Typography on left, Primary Dark Mode Switch on right
      item {
        Spacer(modifier = Modifier.height(2.dp))
        ProfileTopBar(
          isDarkMode = isDarkMode,
          language = currentLanguage,
          onDarkModeToggle = onThemeToggle
        )
      }

      // 2. PROFILE CARD: User profile picture beside name at top, directly clickable to upload/change photo
      item {
        DynamicUserProfileCard(
          userProfile = userProfile,
          language = currentLanguage,
          onPhotoClick = {
            photoPickerLauncher.launch(
              PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
          }
        )
      }

      // 3. BUS SERVICES: 4 Tiles -> Notice Box, Control Panel, Complaint Box, Crew
      item {
        BusServicesSection(
          language = currentLanguage,
          onNoticeClick = { viewModel.navigateTo(AppScreen.NOTICES) },
          onControlPanelClick = { isControlPanelModalOpen = true },
          onComplaintBoxClick = { viewModel.navigateTo(AppScreen.COMPLAINT_BOX) },
          onCrewClick = { viewModel.navigateTo(AppScreen.CREW) }
        )
      }

      // 4. COMMUNITY SECTION: Community and About tiles
      item {
        CommunitySection(
          language = currentLanguage,
          onCommunityClick = { viewModel.navigateTo(AppScreen.COMMUNITY) },
          onAboutClick = { isAboutModalOpen = true }
        )
      }

      // 5. PREFERENCES & SETTINGS: Settings, Language Switcher, Logout (Duplicate Dark Mode Removed)
      item {
        PreferencesSection(
          language = currentLanguage,
          onSettingsClick = { isSettingsSheetOpen = true },
          onLanguageClick = { isLanguageDialogOpen = true },
          onLogoutClick = {
            viewModel.logout()
            Toast.makeText(context, if (currentLanguage.isBangla) "লগ আউট সম্পন্ন হয়েছে" else "Logged out successfully", Toast.LENGTH_SHORT).show()
          }
        )
      }

      // 6. BUS RULES SECTION: Dedicated University Bus Rules directly below Settings
      item {
        BusRulesSection(
          language = currentLanguage
        )
      }

      // Brand Footer
      item {
        BrandFooter(language = currentLanguage)
      }

      // Extra space for the fixed "BACK TO HOME PAGE" button
      item {
        Spacer(modifier = Modifier.height(96.dp))
      }
    }

    // BOTTOM NAVIGATION REPLACEMENT: Single full-width digital button 'BACK TO HOME PAGE'
    val view = LocalView.current
    Surface(
      shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
      color = if (isDarkMode) Color(0xFF14080C) else Color(0xFFFAF7F2),
      shadowElevation = 14.dp,
      border = androidx.compose.foundation.BorderStroke(1.dp, if (isDarkMode) Color(0xFF331119) else Color(0xFFE8DCD5)),
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .navigationBarsPadding()
          .padding(horizontal = 16.dp, vertical = 10.dp)
      ) {
        Button(
          onClick = {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            viewModel.navigateTo(AppScreen.DASHBOARD)
          },
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
              text = AppStrings.backToHome(currentLanguage),
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp,
              color = Color.White
            )
          }
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

    // HELP & SEARCH FAQ DIALOG
    if (isHelpSearchOpen) {
      HelpAndSearchDialog(
        onDismiss = { isHelpSearchOpen = false }
      )
    }

    // LANGUAGE SELECTION DIALOG (Exactly two options: বাংলা and English)
    if (isLanguageDialogOpen) {
      LanguageSelectionDialog(
        currentLanguage = currentLanguage,
        onDismiss = { isLanguageDialogOpen = false },
        onSelectLanguage = { selectedLang ->
          viewModel.setLanguage(selectedLang, context)
          isLanguageDialogOpen = false
          Toast.makeText(
            context,
            if (selectedLang.isBangla) "ভাষা পরিবর্তন করা হয়েছে: বাংলা" else "Language switched to English",
            Toast.LENGTH_SHORT
          ).show()
        }
      )
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 1. TOP BAR (Dark Mode toggle switch instead of profile button)
// -------------------------------------------------------------------------------------------------
@Composable
private fun ProfileTopBar(
  isDarkMode: Boolean,
  language: AppLanguage,
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
          .border(1.5.dp, if (isDarkMode) Color(0xFFEF4444) else MaroonDeep, CircleShape)
      )
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        if (language.isBangla) {
          Text(
            text = "সাদা সুন্দরী",
            fontFamily = FontFamily.Serif,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = if (isDarkMode) Color(0xFFF8FAFC) else MaroonDeep,
            lineHeight = 18.sp
          )
          Text(
            text = "লাইভ বাস কম্প্যানিয়ন",
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            color = if (isDarkMode) Color(0xFFCBD5E1) else TextGrey
          )
        } else {
          Text(
            text = "SADA",
            fontFamily = FontFamily.Serif,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            color = if (isDarkMode) Color(0xFFF8FAFC) else MaroonDeep,
            lineHeight = 16.sp
          )
          Text(
            text = "SHUNDORI",
            fontFamily = FontFamily.Serif,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.5.sp,
            color = if (isDarkMode) Color(0xFFF8FAFC) else MaroonDeep,
            lineHeight = 18.sp
          )
          Text(
            text = "LIVE BUS COMPANION",
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            color = if (isDarkMode) Color(0xFFCBD5E1) else TextGrey
          )
        }
      }
    }

    // Right: Dark Mode Toggle Switch (Requirement 1)
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = if (isDarkMode) Color(0xFF240D12) else Color.White,
      shadowElevation = 2.dp,
      border = androidx.compose.foundation.BorderStroke(1.dp, if (isDarkMode) Color(0xFF4A1E26) else CardBorderColor),
      modifier = Modifier.testTag("top_bar_dark_mode_switch")
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
      ) {
        Icon(
          imageVector = Icons.Default.DarkMode,
          contentDescription = "Dark Mode Toggle",
          tint = if (isDarkMode) Color(0xFFEF4444) else TextGrey,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Switch(
          checked = isDarkMode,
          onCheckedChange = onDarkModeToggle,
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = if (isDarkMode) Color(0xFFEF4444) else MaroonDeep,
            uncheckedThumbColor = TextGrey,
            uncheckedTrackColor = if (isDarkMode) Color(0xFF38131B) else Color(0xFFEDE4DC)
          ),
          modifier = Modifier.padding(start = 2.dp)
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 2. DYNAMIC USER PROFILE CARD (Dynamic Account Sync & Direct Clickable Photo Upload)
// -------------------------------------------------------------------------------------------------
@Composable
private fun DynamicUserProfileCard(
  userProfile: UserProfile,
  language: AppLanguage,
  onPhotoClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isDark = SadaTheme.isDark
  val cardBg = if (isDark) Color(0xFF240D12) else Color.White
  val cardBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey
  val accentColor = if (isDark) Color(0xFFEF4444) else MaroonDeep
  val tileBg = if (isDark) Color(0xFF38131B) else TileIconBg

  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = cardBg),
    border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("dynamic_profile_card")
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // User Profile Picture directly beside Name at top, directly clickable to pick from device
        Box(
          modifier = Modifier
            .size(84.dp)
            .clip(CircleShape)
            .clickable { onPhotoClick() }
            .testTag("btn_change_profile_photo"),
          contentAlignment = Alignment.Center
        ) {
          Surface(
            shape = CircleShape,
            color = tileBg,
            border = androidx.compose.foundation.BorderStroke(2.5.dp, accentColor),
            modifier = Modifier.size(78.dp)
          ) {
            if (!userProfile.avatarUri.isNullOrBlank()) {
              AsyncImage(
                model = userProfile.avatarUri,
                contentDescription = "User Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            } else {
              Image(
                painter = painterResource(id = R.drawable.sada_shundori_icon),
                contentDescription = "User Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
            }
          }

          // Camera Edit overlay badge at bottom-right
          Surface(
            shape = CircleShape,
            color = accentColor,
            shadowElevation = 3.dp,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, cardBg),
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .size(26.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Change Photo",
                tint = Color.White,
                modifier = Modifier.size(13.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Dynamic User Details directly connected to the active account
        Column(modifier = Modifier.weight(1f)) {
          // Display Name
          Text(
            text = userProfile.fullName.ifBlank { userProfile.name },
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = textPrimary,
            lineHeight = 22.sp,
            maxLines = 1
          )

          Spacer(modifier = Modifier.height(4.dp))

          // Department & College
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.School,
              contentDescription = null,
              tint = accentColor,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "${userProfile.department} • ${userProfile.college}",
              fontSize = 12.5.sp,
              fontWeight = FontWeight.Medium,
              color = textPrimary,
              maxLines = 1
            )
          }

          Spacer(modifier = Modifier.height(3.dp))

          // Session & Student ID
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.DateRange,
              contentDescription = null,
              tint = textSecondary,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "${AppStrings.sessionLabel(language)} ${userProfile.session} • ID: ${userProfile.studentId}",
              fontSize = 12.sp,
              color = textSecondary,
              maxLines = 1
            )
          }

          Spacer(modifier = Modifier.height(3.dp))

          // Email Address
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Email,
              contentDescription = null,
              tint = textSecondary,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = userProfile.email,
              fontSize = 11.5.sp,
              color = textSecondary,
              maxLines = 1
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Direct Clickable Prompt Bar for Photo Upload
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = tileBg,
        border = androidx.compose.foundation.BorderStroke(0.8.dp, cardBorder),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onPhotoClick() }
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.PhotoCamera,
              contentDescription = null,
              tint = accentColor,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = AppStrings.tapToChangePhoto(language),
              fontSize = 11.5.sp,
              fontWeight = FontWeight.Medium,
              color = accentColor
            )
          }

          Surface(
            shape = RoundedCornerShape(6.dp),
            color = cardBg,
            border = androidx.compose.foundation.BorderStroke(0.5.dp, cardBorder)
          ) {
            Text(
              text = if (language.isBangla) "আপলোড" else "Upload",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = textSecondary,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 3. BUS SERVICES (4 TILES: Notice Box, Control Panel, Complaint Box, Crew)
// -------------------------------------------------------------------------------------------------
@Composable
private fun BusServicesSection(
  language: AppLanguage,
  onNoticeClick: () -> Unit,
  onControlPanelClick: () -> Unit,
  onComplaintBoxClick: () -> Unit,
  onCrewClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier.fillMaxWidth()) {
    // Section Header
    SectionHeader(
      icon = Icons.Default.DirectionsBus,
      title = AppStrings.busServicesTitle(language)
    )

    Spacer(modifier = Modifier.height(8.dp))

    val isDark = SadaTheme.isDark
    val cardBg = if (isDark) Color(0xFF240D12) else Color.White
    val cardBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor
    val dividerCol = if (isDark) Color(0xFF35151D) else CardBorderColor

    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = cardBg),
      border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        // Tile 1: Notice Box
        ProfileTileItem(
          icon = Icons.Outlined.Notifications,
          title = AppStrings.noticeBoardTitle(language),
          subtitle = AppStrings.noticeBoardSub(language),
          badgeCount = "3",
          onClick = onNoticeClick,
          testTag = "tile_notice_box"
        )

        Divider(color = dividerCol, thickness = 0.8.dp)

        // Tile 2: Sada Bus Control Panel / Moderator
        ProfileTileItem(
          icon = Icons.Default.AdminPanelSettings,
          title = AppStrings.controlPanelTitle(language),
          subtitle = AppStrings.controlPanelSub(language),
          rightLabel = if (language.isBangla) "উন্মুক্ত" else "Open",
          onClick = onControlPanelClick,
          testTag = "tile_moderator_panel"
        )

        Divider(color = dividerCol, thickness = 0.8.dp)

        // Tile 3: Complaint Box
        ProfileTileItem(
          icon = Icons.Default.ChatBubbleOutline,
          title = AppStrings.complaintBoxTitle(language),
          subtitle = AppStrings.complaintBoxSub(language),
          onClick = onComplaintBoxClick,
          testTag = "tile_complaint_box"
        )

        Divider(color = dividerCol, thickness = 0.8.dp)

        // Tile 4: Crew
        ProfileTileItem(
          icon = Icons.Default.DirectionsBus,
          title = AppStrings.crewTitle(language),
          subtitle = AppStrings.crewSub(language),
          onClick = onCrewClick,
          testTag = "tile_crew_details"
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 4. COMMUNITY SECTION (Community & About tiles)
// -------------------------------------------------------------------------------------------------
@Composable
private fun CommunitySection(
  language: AppLanguage,
  onCommunityClick: () -> Unit,
  onAboutClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isDark = SadaTheme.isDark
  val cardBg = if (isDark) Color(0xFF240D12) else Color.White
  val cardBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor
  val dividerCol = if (isDark) Color(0xFF35151D) else CardBorderColor

  Column(modifier = modifier.fillMaxWidth()) {
    // Section Header
    SectionHeader(
      icon = Icons.Default.Groups,
      title = AppStrings.communityTitle(language)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = cardBg),
      border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        // Tile 1: Community
        ProfileTileItem(
          icon = Icons.Outlined.Group,
          title = AppStrings.officialCommunityTitle(language),
          subtitle = AppStrings.officialCommunitySub(language),
          onClick = onCommunityClick,
          testTag = "tile_community"
        )

        Divider(color = dividerCol, thickness = 0.8.dp)

        // Tile 2: About
        ProfileTileItem(
          icon = Icons.Outlined.Info,
          title = AppStrings.aboutTitle(language),
          subtitle = AppStrings.aboutSub(language),
          onClick = onAboutClick,
          testTag = "tile_about"
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 5. PREFERENCES & SETTINGS SECTION (Settings, Language, Logout - Duplicate Dark Mode Removed)
// -------------------------------------------------------------------------------------------------
@Composable
private fun PreferencesSection(
  language: AppLanguage,
  onSettingsClick: () -> Unit,
  onLanguageClick: () -> Unit,
  onLogoutClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isDark = SadaTheme.isDark
  val cardBg = if (isDark) Color(0xFF240D12) else Color.White
  val cardBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor
  val dividerCol = if (isDark) Color(0xFF35151D) else CardBorderColor

  Column(modifier = modifier.fillMaxWidth()) {
    // Section Header
    SectionHeader(
      icon = Icons.Default.Settings,
      title = AppStrings.preferencesTitle(language)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = cardBg),
      border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        // Tile 1: Settings (Opens sheet with Help, Search, Report a Problem)
        ProfileTileItem(
          icon = Icons.Default.Settings,
          title = AppStrings.settingsTileTitle(language),
          subtitle = AppStrings.settingsTileSub(language),
          onClick = onSettingsClick,
          testTag = "tile_preferences_settings"
        )

        Divider(color = dividerCol, thickness = 0.8.dp)

        // Tile 2: App Language Switcher
        ProfileTileItem(
          icon = Icons.Default.Language,
          title = AppStrings.languageTileTitle(language),
          subtitle = if (language.isBangla) "বর্তমান: বাংলা (ট্যাপ করে পরিবর্তন করুন)" else "Current: English (Tap to switch)",
          rightLabel = if (language.isBangla) "বাংলা" else "EN",
          onClick = onLanguageClick,
          testTag = "tile_preferences_language"
        )

        Divider(color = dividerCol, thickness = 0.8.dp)

        // Tile 3: Log Out
        ProfileTileItem(
          icon = Icons.Default.ExitToApp,
          title = AppStrings.logoutTitle(language),
          subtitle = AppStrings.logoutSub(language),
          onClick = onLogoutClick,
          testTag = "tile_preferences_logout"
        )
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 6. BUS RULES SECTION (Dedicated University Bus Rules in Bangla directly below Settings)
// -------------------------------------------------------------------------------------------------
@Composable
private fun BusRulesSection(
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val isDark = SadaTheme.isDark
  val cardBg = if (isDark) Color(0xFF240D12) else Color.White
  val cardBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey
  val accentColor = if (isDark) Color(0xFFEF4444) else MaroonDeep
  val dividerCol = if (isDark) Color(0xFF35151D) else CardBorderColor

  var expandedRuleId by remember { mutableStateOf<Int?>(1) }

  Column(modifier = modifier.fillMaxWidth()) {
    // Section Header
    SectionHeader(
      icon = Icons.Default.Gavel,
      title = AppStrings.busRulesTitle(language)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = cardBg),
      border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("bus_rules_card")
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        // Explanatory Subtitle
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = CircleShape,
            color = if (isDark) Color(0xFF38131B) else TileIconBg,
            modifier = Modifier.size(36.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(18.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = if (language.isBangla) "ঢাকা বিশ্ববিদ্যালয় অধিভুক্ত ৭ কলেজ" else "DU Affiliated 7 Colleges",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = textPrimary
            )
            Text(
              text = AppStrings.busRulesSubtitle(language),
              fontSize = 11.5.sp,
              color = textSecondary
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Divider(color = dividerCol, thickness = 0.8.dp)
        Spacer(modifier = Modifier.height(8.dp))

        // 8 University Bus Rules
        AppStrings.officialBusRules.forEachIndexed { index, rule ->
          val isExpanded = expandedRuleId == rule.id

          Column(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .clickable {
                expandedRuleId = if (isExpanded) null else rule.id
              }
              .padding(vertical = 8.dp, horizontal = 4.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Rule Category Badge
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isDark) Color(0xFF38131B) else BadgeBg,
                border = androidx.compose.foundation.BorderStroke(0.6.dp, if (isDark) Color(0xFF4A1822) else Color(0xFFE2D0D0))
              ) {
                Text(
                  text = if (language.isBangla) rule.categoryBn else rule.categoryEn,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isDark) Color(0xFFEF4444) else MaroonDeep,
                  modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                )
              }

              Spacer(modifier = Modifier.width(10.dp))

              // Rule Title
              Text(
                text = if (language.isBangla) rule.titleBn else rule.titleEn,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = textPrimary,
                modifier = Modifier.weight(1f)
              )

              Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = textSecondary,
                modifier = Modifier.size(20.dp)
              )
            }

            AnimatedVisibility(visible = isExpanded) {
              Column(modifier = Modifier.padding(top = 8.dp, start = 4.dp, end = 4.dp)) {
                Text(
                  text = if (language.isBangla) rule.descBn else rule.descEn,
                  fontSize = 12.5.sp,
                  lineHeight = 19.sp,
                  color = textSecondary
                )
              }
            }
          }

          if (index < AppStrings.officialBusRules.size - 1) {
            Divider(color = dividerCol.copy(alpha = 0.5f), thickness = 0.5.dp, modifier = Modifier.padding(vertical = 2.dp))
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------------------------------------------
// 7. LANGUAGE SELECTION DIALOG (Exactly two options: বাংলা and English)
// -------------------------------------------------------------------------------------------------
@Composable
private fun LanguageSelectionDialog(
  currentLanguage: AppLanguage,
  onDismiss: () -> Unit,
  onSelectLanguage: (AppLanguage) -> Unit
) {
  val isDark = SadaTheme.isDark
  val dialogBg = if (isDark) Color(0xFF1E0B10) else Color.White
  val borderCol = if (isDark) Color(0xFF4A1E26) else CardBorderColor
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey
  val accentColor = if (isDark) Color(0xFFEF4444) else MaroonDeep

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = dialogBg,
      border = androidx.compose.foundation.BorderStroke(1.dp, borderCol),
      shadowElevation = 16.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("dialog_language_selection")
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Language,
              contentDescription = null,
              tint = accentColor,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = if (currentLanguage.isBangla) "ভাষা নির্বাচন করুন" else "Select Language",
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = textPrimary
            )
          }

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = textSecondary,
            modifier = Modifier
              .size(20.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = if (currentLanguage.isBangla) "অ্যাপের সম্পূর্ণ ইন্টারফেসের ভাষা তাৎক্ষণিক পরিবর্তন করুন" else "Switch application-wide interface language instantly",
          fontSize = 12.sp,
          color = textSecondary
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Option 1: বাংলা (Bangla)
        LanguageOptionCard(
          title = "বাংলা (Bangla)",
          subtitle = "জাতীয় ও ক্যাম্পাস ভাষা",
          isSelected = currentLanguage == AppLanguage.BANGLA,
          accentColor = accentColor,
          textColor = textPrimary,
          onClick = { onSelectLanguage(AppLanguage.BANGLA) },
          testTag = "lang_opt_bangla"
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Option 2: English
        LanguageOptionCard(
          title = "English",
          subtitle = "International & standard UI",
          isSelected = currentLanguage == AppLanguage.ENGLISH,
          accentColor = accentColor,
          textColor = textPrimary,
          onClick = { onSelectLanguage(AppLanguage.ENGLISH) },
          testTag = "lang_opt_english"
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          TextButton(onClick = onDismiss) {
            Text(
              text = if (currentLanguage.isBangla) "বাতিল" else "Cancel",
              color = textSecondary,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }
    }
  }
}

@Composable
private fun LanguageOptionCard(
  title: String,
  subtitle: String,
  isSelected: Boolean,
  accentColor: Color,
  textColor: Color,
  onClick: () -> Unit,
  testTag: String
) {
  val isDark = SadaTheme.isDark
  val cardBg = if (isSelected) {
    if (isDark) Color(0xFF38131B) else Color(0xFFFBECEC)
  } else {
    if (isDark) Color(0xFF14080C) else Color(0xFFF8F5F0)
  }
  val borderStroke = if (isSelected) {
    androidx.compose.foundation.BorderStroke(1.5.dp, accentColor)
  } else {
    androidx.compose.foundation.BorderStroke(1.dp, if (isDark) Color(0xFF301018) else Color(0xFFEADBCE))
  }

  Surface(
    shape = RoundedCornerShape(14.dp),
    color = cardBg,
    border = borderStroke,
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag(testTag)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          fontSize = 15.sp,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
          color = textColor
        )
        Text(
          text = subtitle,
          fontSize = 11.5.sp,
          color = if (isDark) Color(0xFF94A3B8) else TextGrey
        )
      }

      Icon(
        imageVector = if (isSelected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
        contentDescription = null,
        tint = if (isSelected) accentColor else if (isDark) Color(0xFF64748B) else TextGrey,
        modifier = Modifier.size(20.dp)
      )
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
  val isDark = SadaTheme.isDark
  val maroonAccent = if (isDark) Color(0xFFEF4444) else MaroonDeep
  val textCol = if (isDark) Color(0xFFF87171) else MaroonDeep

  Column(modifier = modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Surface(
        shape = CircleShape,
        color = maroonAccent,
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
        color = textCol
      )
    }
    Spacer(modifier = Modifier.height(3.dp))
    Box(
      modifier = Modifier
        .padding(start = 32.dp)
        .width(36.dp)
        .height(2.5.dp)
        .background(maroonAccent, RoundedCornerShape(1.dp))
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
  val isDark = SadaTheme.isDark
  val iconBg = if (isDark) Color(0xFF38131B) else TileIconBg
  val iconTint = if (isDark) Color(0xFFEF4444) else MaroonDeep
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey
  val labelBg = if (isDark) Color(0xFF38131B) else TileIconBg
  val labelBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor
  val labelText = if (isDark) Color(0xFFF87171) else MaroonDeep

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
      color = iconBg,
      modifier = Modifier.size(42.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = iconTint,
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
        color = textPrimary
      )
      Text(
        text = subtitle,
        fontSize = 12.sp,
        color = textSecondary
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
        color = labelBg,
        border = androidx.compose.foundation.BorderStroke(0.5.dp, labelBorder)
      ) {
        Text(
          text = rightLabel,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = labelText,
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
private fun BrandFooter(
  modifier: Modifier = Modifier,
  language: AppLanguage = AppLanguage.BANGLA
) {
  val isDark = SadaTheme.isDark
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Surface(
      shape = CircleShape,
      color = if (isDark) Color(0xFF240D12) else Color.White,
      border = androidx.compose.foundation.BorderStroke(1.dp, if (isDark) Color(0xFF4A1E26) else CardBorderColor),
      modifier = Modifier.size(34.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = if (language.isBangla) "স" else "S",
          fontFamily = FontFamily.Serif,
          fontSize = 16.sp,
          fontWeight = FontWeight.Black,
          color = if (isDark) Color(0xFFF87171) else MaroonDeep
        )
      }
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = if (language.isBangla) "নিরাপদ  •  নির্ভরযোগ্য  •  ঐক্যবদ্ধ" else "SAFE  •  RELIABLE  •  TOGETHER",
      fontSize = 9.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.2.sp,
      color = if (isDark) Color(0xFFCBD5E1) else TextGrey
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = if (language.isBangla) "সাদা সুন্দরীর সাথে থাকার জন্য ধন্যবাদ ❤️" else "Thank you for being with Sada Shundori ❤️",
      fontSize = 11.sp,
      color = if (isDark) Color(0xFF94A3B8) else TextGrey
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

  val isDark = SadaTheme.isDark
  val dialogBg = if (isDark) Color(0xFF240D12) else Color.White
  val itemBg = if (isDark) Color(0xFF1E0B10) else TileIconBg
  val itemBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey
  val accentColor = if (isDark) Color(0xFFEF4444) else MaroonDeep

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = dialogBg,
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
              color = accentColor,
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
                color = textPrimary
              )
              Text(
                text = "Unrestricted Committee Roster",
                fontSize = 11.sp,
                color = if (isDark) Color(0xFFF87171) else MaroonDeep,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = textSecondary,
            modifier = Modifier
              .size(22.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "The Sada Shundori operations desk is openly accessible to all verified students and faculty. For schedule adjustments, route safety, or lost & found verification, contact the coordinators below:",
          fontSize = 12.sp,
          color = textSecondary,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          moderators.forEach { (name, role, phone) ->
            Surface(
              shape = RoundedCornerShape(14.dp),
              color = itemBg,
              border = androidx.compose.foundation.BorderStroke(0.8.dp, itemBorder),
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
                    color = textPrimary
                  )
                  Text(
                    text = role,
                    fontSize = 11.sp,
                    color = textSecondary
                  )
                  Text(
                    text = phone,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDark) Color(0xFFF87171) else MaroonDeep
                  )
                }

                Surface(
                  shape = CircleShape,
                  color = accentColor,
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
  val isDark = SadaTheme.isDark
  val dialogBg = if (isDark) Color(0xFF240D12) else Color.White
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey
  val accentColor = if (isDark) Color(0xFFEF4444) else MaroonDeep

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = dialogBg,
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
              color = accentColor,
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
                color = textPrimary
              )
              Text(
                text = "Dhaka University 7 Colleges",
                fontSize = 11.sp,
                color = textSecondary
              )
            }
          }

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = textSecondary,
            modifier = Modifier
              .size(22.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Connect with over 4,500 daily commuters across Dhaka College, Eden Mohila College, Titumir College, and other affiliated campuses for ride sharing, traffic alerts, and community meetups:",
          fontSize = 12.sp,
          color = textSecondary,
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
          colors = ButtonDefaults.buttonColors(containerColor = if (isDark) Color(0xFF881324) else MaroonDeep),
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
  val isDark = SadaTheme.isDark
  val itemBg = if (isDark) Color(0xFF1E0B10) else TileIconBg
  val itemBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey
  val iconTint = if (isDark) Color(0xFFEF4444) else MaroonDeep

  Surface(
    shape = RoundedCornerShape(14.dp),
    color = itemBg,
    border = androidx.compose.foundation.BorderStroke(0.8.dp, itemBorder),
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
        Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = textPrimary)
        Text(text = desc, fontSize = 11.sp, color = textSecondary)
      }
      Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = "Open",
        tint = iconTint,
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
  val isDark = SadaTheme.isDark
  val dialogBg = if (isDark) Color(0xFF240D12) else Color.White
  val itemBg = if (isDark) Color(0xFF1E0B10) else TileIconBg
  val itemBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey
  val accentColor = if (isDark) Color(0xFFEF4444) else MaroonDeep

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = dialogBg,
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
              color = accentColor,
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
                color = textPrimary
              )
              Text(
                text = "Campus Bus Companion • v2.4",
                fontSize = 11.sp,
                color = textSecondary
              )
            }
          }

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = textSecondary,
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
          color = textSecondary,
          lineHeight = 17.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = itemBg,
          border = androidx.compose.foundation.BorderStroke(0.8.dp, itemBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Fleet Identifier: SS-01 / Metro-Ba 15-8824", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = if (isDark) Color(0xFFF87171) else MaroonDeep)
            Text(text = "Operated under DU 7 Colleges Transport Syndicate", fontSize = 10.5.sp, color = textSecondary)
            Text(text = "Lead Developer: Hafiz Hossain Lamim (Finance & Banking)", fontSize = 10.5.sp, color = textSecondary)
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = if (isDark) Color(0xFF881324) else MaroonDeep),
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
  val isDark = SadaTheme.isDark
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val sheetBg = if (isDark) Color(0xFF1E0B10) else Color.White
  val dividerCol = if (isDark) Color(0xFF35151D) else CardBorderColor

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = sheetBg,
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
          color = if (isDark) Color(0xFFF8FAFC) else TextDark
        )

        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Close",
          tint = if (isDark) Color(0xFFCBD5E1) else TextGrey,
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

      Divider(color = dividerCol, thickness = 0.8.dp)

      // 2. Help & Support (Spec 5)
      SettingsOptionRow(
        icon = Icons.Default.HelpOutline,
        title = "Help & Support",
        subtitle = "Frequently asked questions & student handbook",
        onClick = onOpenHelp
      )

      Divider(color = dividerCol, thickness = 0.8.dp)

      // 3. Report a Problem (Spec 5)
      SettingsOptionRow(
        icon = Icons.Default.ReportProblem,
        title = "Report a Problem",
        subtitle = "Submit app diagnostic report or bug notice",
        iconTint = if (isDark) Color(0xFFEF4444) else MaroonDeep,
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
  val isDark = SadaTheme.isDark
  val itemBg = if (isDark) Color(0xFF38131B) else TileIconBg
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Surface(
      shape = CircleShape,
      color = itemBg,
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
      Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textPrimary)
      Text(text = subtitle, fontSize = 11.5.sp, color = textSecondary)
    }

    Icon(
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = null,
      tint = textSecondary,
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
  val isDark = SadaTheme.isDark
  val dialogBg = if (isDark) Color(0xFF240D12) else Color.White
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = dialogBg,
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
              color = if (isDark) Color(0xFF38131B) else BadgeBg,
              modifier = Modifier.size(34.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.ReportProblem,
                  contentDescription = null,
                  tint = if (isDark) Color(0xFFEF4444) else MaroonDeep,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Report a Problem",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = textPrimary
            )
          }

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = textSecondary,
            modifier = Modifier
              .size(20.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Notice inaccurate GPS coordinates, slow updates, or app rendering issues? Let our dev team know:",
          fontSize = 12.sp,
          color = textSecondary
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = problemDescription,
          onValueChange = { problemDescription = it },
          placeholder = { Text("Describe the issue in detail...", fontSize = 12.sp, color = textSecondary) },
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
            colors = ButtonDefaults.buttonColors(containerColor = if (isDark) Color(0xFF38131B) else TileIconBg),
            modifier = Modifier.weight(1f)
          ) {
            Text("Cancel", color = textPrimary, fontWeight = FontWeight.SemiBold)
          }

          Button(
            onClick = { onSubmit(problemDescription) },
            enabled = problemDescription.isNotBlank(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (isDark) Color(0xFF881324) else MaroonDeep),
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
  val isDark = SadaTheme.isDark
  val dialogBg = if (isDark) Color(0xFF240D12) else Color.White
  val textPrimary = if (isDark) Color(0xFFF8FAFC) else TextDark
  val textSecondary = if (isDark) Color(0xFFCBD5E1) else TextGrey
  val itemBg = if (isDark) Color(0xFF1E0B10) else TileIconBg
  val itemBorder = if (isDark) Color(0xFF4A1E26) else CardBorderColor

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
      color = dialogBg,
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
            color = textPrimary
          )

          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = textSecondary,
            modifier = Modifier
              .size(20.dp)
              .clickable { onDismiss() }
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Search questions e.g. ETA, route...", fontSize = 12.sp, color = textSecondary) },
          leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = textSecondary, modifier = Modifier.size(18.dp))
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
              color = itemBg,
              border = androidx.compose.foundation.BorderStroke(0.8.dp, itemBorder),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(text = q, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = if (isDark) Color(0xFFF87171) else MaroonDeep)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = a, fontSize = 11.sp, color = textSecondary, lineHeight = 14.sp)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = if (isDark) Color(0xFF881324) else MaroonDeep),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Done", fontWeight = FontWeight.Bold, color = Color.White)
        }
      }
    }
  }
}
