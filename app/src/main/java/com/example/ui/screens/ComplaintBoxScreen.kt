package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.R
import com.example.model.AppScreen
import com.example.viewmodel.BusViewModel

// Dedicated luxury color palette matching reference design
private val CreamBackground = Color(0xFFFAF7F2)
private val DeepMaroon = Color(0xFF4A0E17)
private val DeepMaroonDark = Color(0xFF38080F)
private val SoftBlushCircle = Color(0xFFFBECEE)
private val BorderSoft = Color(0xFFEADFD3)
private val InputBorderColor = Color(0xFFDDD6CE)
private val BannerBackground = Color(0xFFF7ECE6)
private val BannerBorder = Color(0xFFE8D7CE)
private val TaglineColor = Color(0xFF7A202A)
private val DarkText = Color(0xFF1E1E1E)
private val SubtitleText = Color(0xFF555555)
private val MutedNoteText = Color(0xFF757575)

private val complaintCategories = listOf(
  "Bus Timing & Punctuality",
  "Overcrowding & Seating Availability",
  "Driver / Conductor Conduct",
  "Route & Bypass / Elevated Highway Issue",
  "Lost & Found Property",
  "Safety & Reckless Driving",
  "Bus Cleanliness & Facilities",
  "Student Pass & ID Dispute",
  "General Suggestion / Feedback"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComplaintBoxScreen(
  viewModel: BusViewModel,
  onBackToProfile: () -> Unit,
  modifier: Modifier = Modifier
) {
  val userProfile by viewModel.userProfile.collectAsState()
  val context = LocalContext.current

  // Form Fields State
  var fullName by remember { mutableStateOf(userProfile.fullName.ifBlank { "Hafiz Hossain Lamim" }) }
  var department by remember { mutableStateOf(userProfile.department.ifBlank { "Finance & Banking" }) }
  var contactInfo by remember { mutableStateOf(userProfile.emergencyContact.ifBlank { userProfile.email }) }

  var selectedCategory by remember { mutableStateOf("") }
  var subject by remember { mutableStateOf("") }
  var details by remember { mutableStateOf("") }
  var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
  var isConsentGiven by remember { mutableStateOf(true) }

  // Validation & Dialog States
  var showValidationErrors by remember { mutableStateOf(false) }
  var isCategoryDropdownOpen by remember { mutableStateOf(false) }
  var isMenuSheetOpen by remember { mutableStateOf(false) }
  var submittedTicketId by remember { mutableStateOf<String?>(null) }

  // Android Photo Picker
  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    if (uri != null) {
      selectedImageUri = uri
      Toast.makeText(context, "Photo attached successfully", Toast.LENGTH_SHORT).show()
    }
  }

  val scrollState = rememberScrollState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(CreamBackground)
      .testTag("complaint_box_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(bottom = 36.dp)
    ) {
      // 1. TOP HEADER BAR matching reference design
      TopHeaderBar(
        onMenuClick = { isMenuSheetOpen = true }
      )

      // 2. BACK TO PROFILE NAVIGATION ACTION
      BackToProfileButton(
        onClick = onBackToProfile
      )

      Spacer(modifier = Modifier.height(14.dp))

      // 3. CENTER HERO (Avatar, Title, Subtitle, Reviewed Banner)
      HeroComplaintSection()

      Spacer(modifier = Modifier.height(20.dp))

      // 4. MAIN FORM CARD (White rounded container)
      Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BorderSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          // SECTION A: YOUR DETAILS
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(
              imageVector = Icons.Outlined.Person,
              contentDescription = null,
              tint = DeepMaroon,
              modifier = Modifier.size(19.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "YOUR DETAILS",
              fontWeight = FontWeight.Bold,
              fontSize = 12.5.sp,
              letterSpacing = 1.1.sp,
              color = DeepMaroon
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Full Name *
          FormLabel(label = "Full name *")
          Spacer(modifier = Modifier.height(5.dp))
          OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = { Text("Enter your full name", color = Color(0xFF9E9E9E), fontSize = 13.5.sp) },
            singleLine = true,
            isError = showValidationErrors && fullName.isBlank(),
            shape = RoundedCornerShape(10.dp),
            colors = outlinedFieldColors(),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_full_name")
          )
          if (showValidationErrors && fullName.isBlank()) {
            ValidationErrorText("Full name is required.")
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Department (optional)
          FormLabel(label = "Department (optional)")
          Spacer(modifier = Modifier.height(5.dp))
          OutlinedTextField(
            value = department,
            onValueChange = { department = it },
            placeholder = { Text("Enter your department", color = Color(0xFF9E9E9E), fontSize = 13.5.sp) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = outlinedFieldColors(),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_department")
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Contact (phone or email) (optional)
          FormLabel(label = "Contact (phone or email) (optional)")
          Spacer(modifier = Modifier.height(5.dp))
          OutlinedTextField(
            value = contactInfo,
            onValueChange = { contactInfo = it },
            placeholder = { Text("Enter phone number or email address", color = Color(0xFF9E9E9E), fontSize = 13.5.sp) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = outlinedFieldColors(),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_contact")
          )

          Spacer(modifier = Modifier.height(24.dp))

          // SECTION B: ABOUT YOUR COMPLAINT
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(
              imageVector = Icons.Outlined.ChatBubbleOutline,
              contentDescription = null,
              tint = DeepMaroon,
              modifier = Modifier.size(19.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "ABOUT YOUR COMPLAINT",
              fontWeight = FontWeight.Bold,
              fontSize = 12.5.sp,
              letterSpacing = 1.1.sp,
              color = DeepMaroon
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Complaint category *
          FormLabel(label = "Complaint category *")
          Spacer(modifier = Modifier.height(5.dp))
          Box(modifier = Modifier.fillMaxWidth()) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .fillMaxWidth()
                .border(
                  width = 1.dp,
                  color = if (showValidationErrors && selectedCategory.isBlank()) Color(0xFFB00020) else InputBorderColor,
                  shape = RoundedCornerShape(10.dp)
                )
                .background(Color.White, RoundedCornerShape(10.dp))
                .clickable { isCategoryDropdownOpen = true }
                .padding(horizontal = 14.dp, vertical = 15.dp)
                .testTag("dropdown_complaint_category")
            ) {
              Text(
                text = if (selectedCategory.isBlank()) "Choose a category" else selectedCategory,
                color = if (selectedCategory.isBlank()) Color(0xFF9E9E9E) else DarkText,
                fontSize = 13.5.sp,
                modifier = Modifier.weight(1f)
              )
              Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select Category",
                tint = Color(0xFF666666)
              )
            }

            DropdownMenu(
              expanded = isCategoryDropdownOpen,
              onDismissRequest = { isCategoryDropdownOpen = false },
              modifier = Modifier
                .background(Color.White)
                .border(1.dp, BorderSoft, RoundedCornerShape(8.dp))
            ) {
              complaintCategories.forEach { category ->
                DropdownMenuItem(
                  text = {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.SpaceBetween,
                      modifier = Modifier.fillMaxWidth()
                    ) {
                      Text(
                        text = category,
                        fontSize = 13.5.sp,
                        color = if (category == selectedCategory) DeepMaroon else DarkText,
                        fontWeight = if (category == selectedCategory) FontWeight.Bold else FontWeight.Normal
                      )
                      if (category == selectedCategory) {
                        Icon(
                          imageVector = Icons.Default.Check,
                          contentDescription = null,
                          tint = DeepMaroon,
                          modifier = Modifier.size(16.dp)
                        )
                      }
                    }
                  },
                  onClick = {
                    selectedCategory = category
                    isCategoryDropdownOpen = false
                  }
                )
              }
            }
          }
          if (showValidationErrors && selectedCategory.isBlank()) {
            ValidationErrorText("Please choose a complaint category.")
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Subject *
          FormLabel(label = "Subject *")
          Spacer(modifier = Modifier.height(5.dp))
          OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            placeholder = { Text("Enter a short subject", color = Color(0xFF9E9E9E), fontSize = 13.5.sp) },
            singleLine = true,
            isError = showValidationErrors && subject.isBlank(),
            shape = RoundedCornerShape(10.dp),
            colors = outlinedFieldColors(),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_subject")
          )
          if (showValidationErrors && subject.isBlank()) {
            ValidationErrorText("Subject is required.")
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Details *
          FormLabel(label = "Details *")
          Spacer(modifier = Modifier.height(5.dp))
          OutlinedTextField(
            value = details,
            onValueChange = { details = it },
            placeholder = {
              Text(
                "Describe your complaint or suggestion in detail...",
                color = Color(0xFF9E9E9E),
                fontSize = 13.5.sp
              )
            },
            minLines = 4,
            maxLines = 8,
            isError = showValidationErrors && details.isBlank(),
            shape = RoundedCornerShape(10.dp),
            colors = outlinedFieldColors(),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_details")
          )
          if (showValidationErrors && details.isBlank()) {
            ValidationErrorText("Details description is required.")
          }

          Spacer(modifier = Modifier.height(5.dp))
          Text(
            text = "Include the stop, route, date or time if relevant.",
            fontSize = 11.5.sp,
            color = MutedNoteText,
            modifier = Modifier.padding(start = 2.dp)
          )

          Spacer(modifier = Modifier.height(18.dp))

          // SECTION C: ATTACH PHOTO (OPTIONAL)
          Surface(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, BorderSoft),
            color = CreamBackground,
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                photoPickerLauncher.launch(
                  PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
              }
              .testTag("button_attach_photo")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp)
            ) {
              if (selectedImageUri != null) {
                // Show thumbnail preview of attached photo
                Box(
                  modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, DeepMaroon, RoundedCornerShape(8.dp))
                ) {
                  AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Attached photo preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                  )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "Photo attached",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = DeepMaroon
                  )
                  Text(
                    text = "Tap to replace supporting image",
                    fontSize = 11.5.sp,
                    color = MutedNoteText
                  )
                }
                IconButton(
                  onClick = { selectedImageUri = null },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove photo",
                    tint = Color(0xFF888888),
                    modifier = Modifier.size(18.dp)
                  )
                }
              } else {
                Icon(
                  imageVector = Icons.Default.AttachFile,
                  contentDescription = null,
                  tint = DeepMaroon,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "Attach photo (optional)",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = DarkText
                  )
                  Text(
                    text = "Add supporting image (optional)",
                    fontSize = 11.5.sp,
                    color = MutedNoteText
                  )
                }
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                  contentDescription = null,
                  tint = Color(0xFF888888),
                  modifier = Modifier.size(20.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // SECTION D: CONSENT CHECKBOX
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .fillMaxWidth()
              .clickable { isConsentGiven = !isConsentGiven }
              .padding(vertical = 4.dp)
          ) {
            Checkbox(
              checked = isConsentGiven,
              onCheckedChange = { isConsentGiven = it },
              colors = CheckboxDefaults.colors(
                checkedColor = DeepMaroon,
                checkmarkColor = Color.White,
                uncheckedColor = Color(0xFF757575)
              ),
              modifier = Modifier.testTag("checkbox_consent")
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "I'm okay with the team contacting me about this report",
              fontSize = 12.5.sp,
              lineHeight = 17.sp,
              color = Color(0xFF333333)
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          // SECTION E: SUBMIT BUTTON
          Button(
            onClick = {
              if (fullName.isBlank() || selectedCategory.isBlank() || subject.isBlank() || details.isBlank()) {
                showValidationErrors = true
                Toast.makeText(context, "Please fill in all required fields marked with *", Toast.LENGTH_SHORT).show()
              } else {
                showValidationErrors = false
                val ticketCode = "TMC-${(1000..9999).random()}"
                viewModel.submitComplaint(
                  category = selectedCategory,
                  title = subject.trim(),
                  description = details.trim(),
                  contactPhone = contactInfo.trim(),
                  busNumber = "SS-01 (Uttara - Gazipur)",
                  department = department.trim(),
                  submitterName = fullName.trim(),
                  imageUri = selectedImageUri?.toString(),
                  allowContact = isConsentGiven
                )
                submittedTicketId = ticketCode
              }
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = DeepMaroon,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .testTag("button_submit_complaint")
          ) {
            Text(
              text = "Submit complaint  →",
              fontSize = 14.5.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.3.sp
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          // SECTION F: PRIVACY WARNING FOOTER
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(
              imageVector = Icons.Outlined.Lock,
              contentDescription = null,
              tint = Color(0xFF888888),
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
              text = "Please don't include passwords or sensitive personal information.",
              fontSize = 11.sp,
              color = MutedNoteText,
              textAlign = TextAlign.Center
            )
          }
        }
      }
    }
  }

  // SUCCESS SUBMISSION DIALOG
  if (submittedTicketId != null) {
    Dialog(onDismissRequest = { /* Require action button click */ }) {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BorderSoft),
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.padding(24.dp)
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(64.dp)
              .background(SoftBlushCircle, CircleShape)
              .border(1.5.dp, DeepMaroon, CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = DeepMaroon,
              modifier = Modifier.size(36.dp)
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "Complaint Submitted",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = DeepMaroon
          )

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = "Tracking Ticket: #${submittedTicketId}",
            fontSize = 12.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = TaglineColor
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Your report has been logged and forwarded to the Titumir College Bus Fleet Management Desk. You will receive an update within 2 hours.",
            fontSize = 12.5.sp,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
            color = SubtitleText
          )

          Spacer(modifier = Modifier.height(20.dp))

          Button(
            onClick = {
              submittedTicketId = null
              onBackToProfile()
            },
            colors = ButtonDefaults.buttonColors(containerColor = DeepMaroon),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Back to Profile", fontWeight = FontWeight.Bold, color = Color.White)
          }

          Spacer(modifier = Modifier.height(8.dp))

          TextButton(
            onClick = {
              submittedTicketId = null
              subject = ""
              details = ""
              selectedCategory = ""
              selectedImageUri = null
            }
          ) {
            Text("Submit Another Report", color = DeepMaroon, fontSize = 13.sp)
          }
        }
      }
    }
  }

  // QUICK NAVIGATION MENU BOTTOM SHEET
  if (isMenuSheetOpen) {
    ModalBottomSheet(
      onDismissRequest = { isMenuSheetOpen = false },
      containerColor = Color.White,
      sheetState = rememberModalBottomSheetState()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 24.dp, vertical = 16.dp)
      ) {
        Text(
          text = "GOVT. TITUMIR COLLEGE BUS GROUP",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp,
          color = DeepMaroon
        )
        Text(
          text = "Quick Navigation",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = DarkText
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuNavigationItem(
          title = "Pass Hub / Profile",
          subtitle = "View student bus pass & account details",
          onClick = {
            isMenuSheetOpen = false
            onBackToProfile()
          }
        )

        MenuNavigationItem(
          title = "Live Bus Tracker",
          subtitle = "Track Sada Shundori SS-01 real-time GPS",
          onClick = {
            isMenuSheetOpen = false
            viewModel.navigateTo(AppScreen.DASHBOARD)
          }
        )

        MenuNavigationItem(
          title = "Pickup Points & Schedule",
          subtitle = "Explore all 18 stops from NU to Campus",
          onClick = {
            isMenuSheetOpen = false
            viewModel.navigateTo(AppScreen.PICKUP_FINDER)
          }
        )

        MenuNavigationItem(
          title = "Notices & Announcements",
          subtitle = "Official fleet updates & examinee schedules",
          onClick = {
            isMenuSheetOpen = false
            viewModel.navigateTo(AppScreen.NOTICES)
          }
        )

        MenuNavigationItem(
          title = "Crew & Transport Committee",
          subtitle = "Contact driver, conductor, and convener hotline",
          onClick = {
            isMenuSheetOpen = false
            viewModel.navigateTo(AppScreen.CREW)
          }
        )

        Spacer(modifier = Modifier.height(20.dp))
      }
    }
  }
}

// ---------------------------------------------------------------------------------------
// Sub-Composables & Styling Helpers
// ---------------------------------------------------------------------------------------

@Composable
private fun TopHeaderBar(
  onMenuClick: () -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    // Left: Bus Icon Emblem + Titles
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(1f)
    ) {
      // Circular Bus Emblem
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(42.dp)
          .clip(CircleShape)
          .background(DeepMaroonDark)
          .border(1.5.dp, DeepMaroon, CircleShape)
      ) {
        Icon(
          imageVector = Icons.Filled.DirectionsBus,
          contentDescription = "Bus Logo",
          tint = Color.White,
          modifier = Modifier.size(24.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column {
        Text(
          text = "GOVT. TITUMIR COLLEGE BUS GROUP",
          fontSize = 12.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 0.4.sp,
          color = Color(0xFF1E1E1E)
        )
        Text(
          text = "Route: Uttara - Gazipur",
          fontSize = 11.5.sp,
          color = Color(0xFF4A4A4A),
          fontWeight = FontWeight.Medium
        )
        Text(
          text = "KNOWLEDGE · SERVICE · FUTURE",
          fontSize = 9.sp,
          fontWeight = FontWeight.SemiBold,
          letterSpacing = 1.6.sp,
          color = TaglineColor
        )
      }
    }

    Spacer(modifier = Modifier.width(8.dp))

    // Right: Menu Button
    Surface(
      shape = RoundedCornerShape(10.dp),
      border = BorderStroke(1.dp, Color(0xFFE2D6CB)),
      color = Color.White,
      modifier = Modifier
        .clickable { onMenuClick() }
        .testTag("button_header_menu")
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Menu,
          contentDescription = "Menu",
          tint = Color(0xFF222222),
          modifier = Modifier.size(19.dp)
        )
        Text(
          text = "Menu",
          fontSize = 9.sp,
          fontWeight = FontWeight.SemiBold,
          color = Color(0xFF333333)
        )
      }
    }
  }
}

@Composable
private fun BackToProfileButton(
  onClick: () -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .padding(horizontal = 16.dp, vertical = 6.dp)
      .clickable { onClick() }
      .testTag("button_back_to_profile")
  ) {
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowBack,
      contentDescription = "Back to Profile",
      tint = DeepMaroon,
      modifier = Modifier.size(18.dp)
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = "Back to Profile",
      fontSize = 13.5.sp,
      fontWeight = FontWeight.SemiBold,
      color = DeepMaroon
    )
  }
}

@Composable
private fun HeroComplaintSection() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    // Soft blush circular badge with cute bus icon
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(62.dp)
        .background(SoftBlushCircle, CircleShape)
        .border(1.dp, Color(0xFFF0D5D8), CircleShape)
    ) {
      Icon(
        imageVector = Icons.Outlined.DirectionsBus,
        contentDescription = "Complaint Box",
        tint = DeepMaroon,
        modifier = Modifier.size(30.dp)
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = "Complaint Box",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = DeepMaroonDark
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = "Help us improve your journey.\nShare a concern or suggestion.",
      fontSize = 13.sp,
      lineHeight = 18.sp,
      textAlign = TextAlign.Center,
      color = SubtitleText
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Verification banner pill
    Surface(
      shape = RoundedCornerShape(12.dp),
      color = BannerBackground,
      border = BorderStroke(0.8.dp, BannerBorder),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp)
      ) {
        Icon(
          imageVector = Icons.Outlined.Shield,
          contentDescription = null,
          tint = DeepMaroon,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Your message will be reviewed by the bus management team.",
          fontSize = 11.5.sp,
          fontWeight = FontWeight.Medium,
          color = Color(0xFF4A352F)
        )
      }
    }
  }
}

@Composable
private fun FormLabel(label: String) {
  Text(
    text = label,
    fontSize = 12.5.sp,
    fontWeight = FontWeight.SemiBold,
    color = Color(0xFF222222)
  )
}

@Composable
private fun ValidationErrorText(message: String) {
  Text(
    text = message,
    fontSize = 11.sp,
    color = Color(0xFFB00020),
    modifier = Modifier.padding(start = 4.dp, top = 2.dp)
  )
}

@Composable
private fun outlinedFieldColors() = OutlinedTextFieldDefaults.colors(
  focusedContainerColor = Color.White,
  unfocusedContainerColor = Color.White,
  disabledContainerColor = Color(0xFFF9F9F9),
  focusedBorderColor = DeepMaroon,
  unfocusedBorderColor = InputBorderColor,
  errorBorderColor = Color(0xFFB00020),
  focusedTextColor = DarkText,
  unfocusedTextColor = DarkText
)

@Composable
private fun MenuNavigationItem(
  title: String,
  subtitle: String,
  onClick: () -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = 12.dp)
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.5.sp,
        color = DarkText
      )
      Text(
        text = subtitle,
        fontSize = 12.sp,
        color = SubtitleText
      )
    }
    Icon(
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = null,
      tint = Color(0xFF888888),
      modifier = Modifier.size(18.dp)
    )
  }
}
