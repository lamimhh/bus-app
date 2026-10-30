package com.example.ui.screens

import android.util.Patterns
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.viewmodel.BusViewModel

// Light / Cream Aesthetic Palette matching reference design
private val CreamBackground = Color(0xFFF9F6F0)
private val CreamCard = Color(0xFFFFFFFF)
private val MaroonHeading = Color(0xFF4A0E17)
private val MaroonDeepButton = Color(0xFF4A0A12)
private val MaroonGlow = Color(0xFFDC2626)
private val GoldAccent = Color(0xFFC89B53)
private val TextMutedBrown = Color(0xFF786F66)
private val BorderWarm = Color(0xFFE2D9CC)
private val TextDark = Color(0xFF2C241D)
private val ErrorRed = Color(0xFFDC2626)

@Composable
fun WelcomeAuthScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  var showAuthDialog by remember { mutableStateOf(false) }
  var isRegisterMode by remember { mutableStateOf(false) }
  var showLegalDialog by remember { mutableStateOf<String?>(null) }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            Color(0xFFFAF7F2),
            CreamBackground,
            Color(0xFFF5EFE6)
          )
        )
      )
  ) {
    // Subtle background dot pattern in top corners
    Canvas(modifier = Modifier.fillMaxSize()) {
      val dotColor = Color(0xFFDDD2C4).copy(alpha = 0.45f)
      // Top-Left Dot Grid
      for (r in 0..5) {
        for (c in 0..4) {
          drawCircle(
            color = dotColor,
            radius = 1.5.dp.toPx(),
            center = Offset(24.dp.toPx() + c * 10.dp.toPx(), 40.dp.toPx() + r * 10.dp.toPx())
          )
        }
      }
      // Top-Right Dot Grid
      for (r in 0..5) {
        for (c in 0..4) {
          drawCircle(
            color = dotColor,
            radius = 1.5.dp.toPx(),
            center = Offset(size.width - 64.dp.toPx() + c * 10.dp.toPx(), 40.dp.toPx() + r * 10.dp.toPx())
          )
        }
      }
    }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(36.dp))

      // Top Logo: Official Maroon & White S Logo
      Image(
        painter = painterResource(id = R.drawable.sada_shundori_icon),
        contentDescription = "SADA SHUNDORI Official Logo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(72.dp)
          .clip(RoundedCornerShape(18.dp))
          .border(2.dp, MaroonHeading.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
      )

      Spacer(modifier = Modifier.height(12.dp))

      // App Title: SADA SHUNDORI in Serif
      Text(
        text = "SADA",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        color = MaroonHeading,
        letterSpacing = 3.sp,
        textAlign = TextAlign.Center
      )
      Text(
        text = "SHUNDORI",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        color = MaroonHeading,
        letterSpacing = 3.sp,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Subtitle: THE CAMPUS BUS COMMUNITY
      Text(
        text = "THE CAMPUS BUS COMMUNITY",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextMutedBrown,
        letterSpacing = 2.5.sp,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Ornamental Divider: Thin horizontal lines with center diamond/dot
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.width(130.dp)
      ) {
        HorizontalDivider(
          modifier = Modifier.weight(1f),
          color = GoldAccent.copy(alpha = 0.5f),
          thickness = 1.dp
        )
        Box(
          modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(5.dp)
            .clip(CircleShape)
            .background(GoldAccent)
        )
        HorizontalDivider(
          modifier = Modifier.weight(1f),
          color = GoldAccent.copy(alpha = 0.5f),
          thickness = 1.dp
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Middle Section: Official Watercolor Art Illustration of SADA SHUNDORI Bus
      // (Replacing previous abstract bus graphic with the real watercolor canvas artwork)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(260.dp),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painterResource(id = R.drawable.sada_shundori_art),
          contentDescription = "SADA SHUNDORI Bus Watercolor Artwork",
          contentScale = ContentScale.Fit,
          modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Headline: "Your campus journey, connected."
      Text(
        text = "Your campus journey,\nconnected.",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        color = MaroonHeading,
        textAlign = TextAlign.Center,
        lineHeight = 31.sp
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Accent gold bar
      Box(
        modifier = Modifier
          .width(36.dp)
          .height(3.dp)
          .clip(RoundedCornerShape(2.dp))
          .background(GoldAccent)
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Subhead: "One community. Every journey."
      Text(
        text = "One community. Every journey.",
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = TextMutedBrown,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(26.dp))

      // Button 1: Solid Maroon "Log In"
      Button(
        onClick = {
          isRegisterMode = false
          showAuthDialog = true
        },
        colors = ButtonDefaults.buttonColors(
          containerColor = MaroonDeepButton,
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("login_button")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = "Log In",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Button 2: White/Cream Outlined "Create Account"
      OutlinedButton(
        onClick = {
          isRegisterMode = true
          showAuthDialog = true
        },
        colors = ButtonDefaults.outlinedButtonColors(
          containerColor = CreamCard,
          contentColor = MaroonDeepButton
        ),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, MaroonDeepButton),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("create_account_button")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.PersonAdd,
              contentDescription = null,
              tint = MaroonDeepButton,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = "Create Account",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = MaroonDeepButton
            )
          }
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = MaroonDeepButton,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Divider: "or"
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      ) {
        HorizontalDivider(
          modifier = Modifier.weight(1f),
          color = BorderWarm,
          thickness = 1.dp
        )
        Text(
          text = "or",
          fontSize = 12.sp,
          color = TextMutedBrown,
          modifier = Modifier.padding(horizontal = 12.dp)
        )
        HorizontalDivider(
          modifier = Modifier.weight(1f),
          color = BorderWarm,
          thickness = 1.dp
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Button 3: Text Button "Continue as guest >"
      TextButton(
        onClick = { viewModel.login(asGuest = true) },
        modifier = Modifier.testTag("guest_login_button")
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.PersonOutline,
            contentDescription = null,
            tint = MaroonHeading,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Continue as guest",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaroonHeading
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "›",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaroonHeading
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Footer: Terms & Privacy
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 28.dp)
      ) {
        Text(
          text = "By continuing, you agree to our",
          fontSize = 12.sp,
          color = TextMutedBrown
        )
        Spacer(modifier = Modifier.height(3.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Terms of Service",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            textDecoration = TextDecoration.Underline,
            color = MaroonHeading,
            modifier = Modifier.clickable { showLegalDialog = "Terms of Service" }
          )
          Text(
            text = "  and  ",
            fontSize = 12.sp,
            color = TextMutedBrown
          )
          Text(
            text = "Privacy Policy",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            textDecoration = TextDecoration.Underline,
            color = MaroonHeading,
            modifier = Modifier.clickable { showLegalDialog = "Privacy Policy" }
          )
        }
      }
    }
  }

  // Auth Dialog: Smooth Transition between Log In and Create Account
  if (showAuthDialog) {
    AuthFlowDialog(
      initialRegisterMode = isRegisterMode,
      onDismiss = { showAuthDialog = false },
      onLoginSuccess = { gmail ->
        showAuthDialog = false
        viewModel.login(asGuest = false, email = gmail)
      },
      onRegisterSuccess = { fullName, department, session, gmail ->
        showAuthDialog = false
        viewModel.registerAndLogin(
          fullName = fullName,
          department = department,
          session = session,
          email = gmail
        )
      }
    )
  }

  // Terms & Privacy Dialog
  showLegalDialog?.let { title ->
    Dialog(onDismissRequest = { showLegalDialog = null }) {
      Card(
        colors = CardDefaults.cardColors(containerColor = CreamCard),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, MaroonHeading),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = null,
              tint = MaroonHeading,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = title,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = MaroonHeading
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = if (title == "Terms of Service")
              "Welcome to SADA SHUNDORI, the official campus transit companion for students of Dhaka University affiliated 7 Colleges.\n\n" +
              "• Dedicated Service: SADA SHUNDORI (Fleet SS-01) is operated for registered students and faculty on the Gazipur-Curzon corridor.\n" +
              "• Boarding Pass: Present your digital student pass or Gmail confirmation to the conductor upon request.\n" +
              "• Safe Transit: Respect seat reservations, queue discipline, and crew directives at all designated pickup points."
            else
              "Your privacy and safety are paramount in the 7 Colleges transit community.\n\n" +
              "• Location Data: Real-time GPS and ETA countdowns are used exclusively for fleet coordination and passenger arrival estimates.\n" +
              "• Passenger Security: Submitted complaints, lost property claims, and emergency alerts are encrypted and reviewed solely by the DU Transport Committee.\n" +
              "• No Commercial Sharing: We never sell or share student commute logs with third-party advertising networks.",
            fontSize = 12.sp,
            lineHeight = 18.sp,
            color = TextDark
          )

          Spacer(modifier = Modifier.height(18.dp))

          Button(
            onClick = { showLegalDialog = null },
            colors = ButtonDefaults.buttonColors(containerColor = MaroonDeepButton),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Got It", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

/**
 * Unified Auth Dialog supporting seamless animated switching between:
 * 1. Log In (strictly requires only: Student Gmail Account and Password, no college badges)
 * 2. Create Account (strictly collects: Full Name, Department, Session, Gmail Account, Password, Confirm Password)
 */
@Composable
private fun AuthFlowDialog(
  initialRegisterMode: Boolean,
  onDismiss: () -> Unit,
  onLoginSuccess: (gmail: String) -> Unit,
  onRegisterSuccess: (fullName: String, department: String, session: String, gmail: String) -> Unit
) {
  var isRegisterMode by remember { mutableStateOf(initialRegisterMode) }

  // Log In Form States
  var loginGmail by remember { mutableStateOf("") }
  var loginPassword by remember { mutableStateOf("") }
  var loginPasswordVisible by remember { mutableStateOf(false) }
  var loginError by remember { mutableStateOf<String?>(null) }

  // Create Account Form States
  var regFullName by remember { mutableStateOf("") }
  var regDepartment by remember { mutableStateOf("") }
  var regSession by remember { mutableStateOf("") }
  var regGmail by remember { mutableStateOf("") }
  var regPassword by remember { mutableStateOf("") }
  var regConfirmPassword by remember { mutableStateOf("") }
  var regPasswordVisible by remember { mutableStateOf(false) }
  var regConfirmPasswordVisible by remember { mutableStateOf(false) }
  var regError by remember { mutableStateOf<String?>(null) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      colors = CardDefaults.cardColors(containerColor = CreamCard),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, MaroonHeading),
      shape = RoundedCornerShape(22.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp)
        .shadow(12.dp, RoundedCornerShape(22.dp))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        // Header with Close Button
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(MaroonHeading.copy(alpha = 0.1f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (isRegisterMode) Icons.Default.PersonAdd else Icons.Default.Lock,
                contentDescription = null,
                tint = MaroonHeading,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = if (isRegisterMode) "Create Account" else "Log In",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = MaroonHeading
              )
              Text(
                text = if (isRegisterMode)
                  "Register your student transit pass"
                else
                  "Sign in to access live bus tracker",
                fontSize = 11.sp,
                color = TextMutedBrown
              )
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = TextMutedBrown
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Animated Content Transition between Log In and Create Account
        AnimatedContent(
          targetState = isRegisterMode,
          transitionSpec = {
            if (targetState) {
              (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                slideOutHorizontally { width -> -width } + fadeOut()
              )
            } else {
              (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                slideOutHorizontally { width -> width } + fadeOut()
              )
            }
          },
          label = "auth_mode_transition"
        ) { inRegisterMode ->
          if (!inRegisterMode) {
            // ==================== LOG IN MODE ====================
            // Strictly requires only: Student Gmail Account and Password
            Column(modifier = Modifier.fillMaxWidth()) {
              if (loginError != null) {
                Surface(
                  color = ErrorRed.copy(alpha = 0.1f),
                  shape = RoundedCornerShape(8.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.5f)),
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                ) {
                  Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(
                      imageVector = Icons.Default.ErrorOutline,
                      contentDescription = null,
                      tint = ErrorRed,
                      modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = loginError ?: "",
                      fontSize = 12.sp,
                      color = ErrorRed
                    )
                  }
                }
              }

              // 1. Student Gmail Account
              OutlinedTextField(
                value = loginGmail,
                onValueChange = {
                  loginGmail = it
                  loginError = null
                },
                label = { Text("Student Gmail Account") },
                placeholder = { Text("e.g. student@gmail.com") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {
                  Icon(Icons.Default.Email, contentDescription = null, tint = MaroonHeading)
                },
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = MaroonHeading,
                  unfocusedBorderColor = BorderWarm,
                  focusedTextColor = TextDark,
                  unfocusedTextColor = TextDark,
                  focusedLabelColor = MaroonHeading,
                  unfocusedLabelColor = TextMutedBrown,
                  focusedContainerColor = CreamBackground,
                  unfocusedContainerColor = CreamBackground
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("login_gmail_input")
              )

              Spacer(modifier = Modifier.height(12.dp))

              // 2. Password
              OutlinedTextField(
                value = loginPassword,
                onValueChange = {
                  loginPassword = it
                  loginError = null
                },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") },
                singleLine = true,
                visualTransformation = if (loginPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                  Icon(Icons.Default.Lock, contentDescription = null, tint = MaroonHeading)
                },
                trailingIcon = {
                  IconButton(onClick = { loginPasswordVisible = !loginPasswordVisible }) {
                    Icon(
                      imageVector = if (loginPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                      contentDescription = if (loginPasswordVisible) "Hide password" else "Show password",
                      tint = TextMutedBrown
                    )
                  }
                },
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = MaroonHeading,
                  unfocusedBorderColor = BorderWarm,
                  focusedTextColor = TextDark,
                  unfocusedTextColor = TextDark,
                  focusedLabelColor = MaroonHeading,
                  unfocusedLabelColor = TextMutedBrown,
                  focusedContainerColor = CreamBackground,
                  unfocusedContainerColor = CreamBackground
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("login_password_input")
              )

              Spacer(modifier = Modifier.height(20.dp))

              // Submit Log In Button
              Button(
                onClick = {
                  val trimmedGmail = loginGmail.trim()
                  val trimmedPassword = loginPassword.trim()
                  when {
                    trimmedGmail.isEmpty() -> {
                      loginError = "Please enter your Gmail account."
                    }
                    !isValidGmail(trimmedGmail) -> {
                      loginError = "Please enter a valid Gmail address (e.g. name@gmail.com)."
                    }
                    trimmedPassword.isEmpty() -> {
                      loginError = "Please enter your password."
                    }
                    trimmedPassword.length < 6 -> {
                      loginError = "Password must be at least 6 characters."
                    }
                    else -> {
                      onLoginSuccess(trimmedGmail)
                    }
                  }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaroonDeepButton),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .height(50.dp)
                  .testTag("auth_submit_button")
              ) {
                Text(
                  text = "Log In to Sada Shundori",
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              }

              Spacer(modifier = Modifier.height(12.dp))

              // Switch to Register Mode
              TextButton(
                onClick = {
                  loginError = null
                  isRegisterMode = true
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
              ) {
                Text(
                  text = "Don't have an account? Create Account",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = MaroonHeading
                )
              }
            }
          } else {
            // ==================== CREATE ACCOUNT MODE ====================
            // Strictly collects: Full Name, Department, Session, Gmail Account, Password, Confirm Password
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 440.dp)
                .verticalScroll(rememberScrollState())
            ) {
              if (regError != null) {
                Surface(
                  color = ErrorRed.copy(alpha = 0.1f),
                  shape = RoundedCornerShape(8.dp),
                  border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.5f)),
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                ) {
                  Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(
                      imageVector = Icons.Default.ErrorOutline,
                      contentDescription = null,
                      tint = ErrorRed,
                      modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = regError ?: "",
                      fontSize = 12.sp,
                      color = ErrorRed
                    )
                  }
                }
              }

              // 1. Full Name
              OutlinedTextField(
                value = regFullName,
                onValueChange = {
                  regFullName = it
                  regError = null
                },
                label = { Text("Full Name") },
                placeholder = { Text("e.g. Hafiz Hossain Lamim") },
                singleLine = true,
                leadingIcon = {
                  Icon(Icons.Default.Person, contentDescription = null, tint = MaroonHeading)
                },
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = MaroonHeading,
                  unfocusedBorderColor = BorderWarm,
                  focusedTextColor = TextDark,
                  unfocusedTextColor = TextDark,
                  focusedLabelColor = MaroonHeading,
                  unfocusedLabelColor = TextMutedBrown,
                  focusedContainerColor = CreamBackground,
                  unfocusedContainerColor = CreamBackground
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("reg_name_input")
              )

              Spacer(modifier = Modifier.height(10.dp))

              // 2. Department
              OutlinedTextField(
                value = regDepartment,
                onValueChange = {
                  regDepartment = it
                  regError = null
                },
                label = { Text("Department") },
                placeholder = { Text("e.g. Finance & Banking") },
                singleLine = true,
                leadingIcon = {
                  Icon(Icons.Default.School, contentDescription = null, tint = MaroonHeading)
                },
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = MaroonHeading,
                  unfocusedBorderColor = BorderWarm,
                  focusedTextColor = TextDark,
                  unfocusedTextColor = TextDark,
                  focusedLabelColor = MaroonHeading,
                  unfocusedLabelColor = TextMutedBrown,
                  focusedContainerColor = CreamBackground,
                  unfocusedContainerColor = CreamBackground
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("reg_department_input")
              )

              Spacer(modifier = Modifier.height(10.dp))

              // 3. Session
              OutlinedTextField(
                value = regSession,
                onValueChange = {
                  regSession = it
                  regError = null
                },
                label = { Text("Session") },
                placeholder = { Text("e.g. 2021-2022") },
                singleLine = true,
                leadingIcon = {
                  Icon(Icons.Default.DateRange, contentDescription = null, tint = MaroonHeading)
                },
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = MaroonHeading,
                  unfocusedBorderColor = BorderWarm,
                  focusedTextColor = TextDark,
                  unfocusedTextColor = TextDark,
                  focusedLabelColor = MaroonHeading,
                  unfocusedLabelColor = TextMutedBrown,
                  focusedContainerColor = CreamBackground,
                  unfocusedContainerColor = CreamBackground
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("reg_session_input")
              )

              Spacer(modifier = Modifier.height(10.dp))

              // 4. Gmail Account
              OutlinedTextField(
                value = regGmail,
                onValueChange = {
                  regGmail = it
                  regError = null
                },
                label = { Text("Gmail Account") },
                placeholder = { Text("e.g. student@gmail.com") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {
                  Icon(Icons.Default.Email, contentDescription = null, tint = MaroonHeading)
                },
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = MaroonHeading,
                  unfocusedBorderColor = BorderWarm,
                  focusedTextColor = TextDark,
                  unfocusedTextColor = TextDark,
                  focusedLabelColor = MaroonHeading,
                  unfocusedLabelColor = TextMutedBrown,
                  focusedContainerColor = CreamBackground,
                  unfocusedContainerColor = CreamBackground
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("reg_gmail_input")
              )

              Spacer(modifier = Modifier.height(10.dp))

              // 5. Password
              OutlinedTextField(
                value = regPassword,
                onValueChange = {
                  regPassword = it
                  regError = null
                },
                label = { Text("Password") },
                placeholder = { Text("Min 6 characters") },
                singleLine = true,
                visualTransformation = if (regPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                  Icon(Icons.Default.Lock, contentDescription = null, tint = MaroonHeading)
                },
                trailingIcon = {
                  IconButton(onClick = { regPasswordVisible = !regPasswordVisible }) {
                    Icon(
                      imageVector = if (regPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                      contentDescription = if (regPasswordVisible) "Hide password" else "Show password",
                      tint = TextMutedBrown
                    )
                  }
                },
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = MaroonHeading,
                  unfocusedBorderColor = BorderWarm,
                  focusedTextColor = TextDark,
                  unfocusedTextColor = TextDark,
                  focusedLabelColor = MaroonHeading,
                  unfocusedLabelColor = TextMutedBrown,
                  focusedContainerColor = CreamBackground,
                  unfocusedContainerColor = CreamBackground
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("reg_password_input")
              )

              Spacer(modifier = Modifier.height(10.dp))

              // 6. Confirm Password
              OutlinedTextField(
                value = regConfirmPassword,
                onValueChange = {
                  regConfirmPassword = it
                  regError = null
                },
                label = { Text("Confirm Password") },
                placeholder = { Text("Re-enter password") },
                singleLine = true,
                visualTransformation = if (regConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                  Icon(Icons.Default.Lock, contentDescription = null, tint = MaroonHeading)
                },
                trailingIcon = {
                  IconButton(onClick = { regConfirmPasswordVisible = !regConfirmPasswordVisible }) {
                    Icon(
                      imageVector = if (regConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                      contentDescription = if (regConfirmPasswordVisible) "Hide password" else "Show password",
                      tint = TextMutedBrown
                    )
                  }
                },
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = MaroonHeading,
                  unfocusedBorderColor = BorderWarm,
                  focusedTextColor = TextDark,
                  unfocusedTextColor = TextDark,
                  focusedLabelColor = MaroonHeading,
                  unfocusedLabelColor = TextMutedBrown,
                  focusedContainerColor = CreamBackground,
                  unfocusedContainerColor = CreamBackground
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("reg_confirm_password_input")
              )

              Spacer(modifier = Modifier.height(18.dp))

              // Submit Create Account Button
              Button(
                onClick = {
                  val trimmedName = regFullName.trim()
                  val trimmedDept = regDepartment.trim()
                  val trimmedSession = regSession.trim()
                  val trimmedGmail = regGmail.trim()
                  val trimmedPass = regPassword.trim()
                  val trimmedConfirm = regConfirmPassword.trim()

                  when {
                    trimmedName.isEmpty() -> {
                      regError = "Please enter your full name."
                    }
                    trimmedName.length < 3 -> {
                      regError = "Full name must be at least 3 characters."
                    }
                    trimmedDept.isEmpty() -> {
                      regError = "Please enter your department."
                    }
                    trimmedSession.isEmpty() -> {
                      regError = "Please enter your academic session (e.g. 2021-2022)."
                    }
                    trimmedGmail.isEmpty() -> {
                      regError = "Please enter your Gmail account."
                    }
                    !isValidGmail(trimmedGmail) -> {
                      regError = "Please enter a valid Gmail address (e.g. name@gmail.com)."
                    }
                    trimmedPass.isEmpty() -> {
                      regError = "Please enter a password."
                    }
                    trimmedPass.length < 6 -> {
                      regError = "Password must be at least 6 characters."
                    }
                    trimmedPass != trimmedConfirm -> {
                      regError = "Passwords do not match."
                    }
                    else -> {
                      onRegisterSuccess(trimmedName, trimmedDept, trimmedSession, trimmedGmail)
                    }
                  }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaroonDeepButton),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .height(50.dp)
                  .testTag("auth_submit_button")
              ) {
                Text(
                  text = "Create Account & Continue",
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              }

              Spacer(modifier = Modifier.height(12.dp))

              // Switch to Login Mode
              TextButton(
                onClick = {
                  regError = null
                  isRegisterMode = false
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
              ) {
                Text(
                  text = "Already have an account? Log In",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = MaroonHeading
                )
              }
            }
          }
        }
      }
    }
  }
}

/**
 * Validates that an email is structurally valid and formatted properly.
 */
private fun isValidGmail(email: String): Boolean {
  return email.isNotBlank() &&
      email.contains("@") &&
      email.contains(".") &&
      (email.endsWith("@gmail.com", ignoreCase = true) || Patterns.EMAIL_ADDRESS.matcher(email).matches())
}
