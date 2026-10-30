package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppScreen
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentGold
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.BusWhite
import com.example.ui.theme.MaroonContainer
import com.example.ui.theme.MaroonDark
import com.example.ui.theme.MaroonDeep
import com.example.ui.theme.MaroonGlow
import com.example.ui.theme.MaroonPrimary
import com.example.ui.theme.OutlineDark
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.BusViewModel

@Composable
fun ProfileScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val userProfile by viewModel.userProfile.collectAsState()

  var isVibrationEnabled by remember { mutableStateOf(true) }
  var isApproachAlertEnabled by remember { mutableStateOf(true) }
  var isSeatAlarmEnabled by remember { mutableStateOf(false) }

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
            text = "Profile & Pass Hub",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Text(
            text = "Digital Bus Pass, Campus Services & Settings",
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
              imageVector = Icons.Default.Badge,
              contentDescription = null,
              tint = StatusGreen,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Verified Pass",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = StatusGreen
            )
          }
        }
      }
    }

    // Digital RFID Campus Bus Pass Card (Stylized Physical Pass Replica)
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaroonDeep),
        shape = RoundedCornerShape(22.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, MaroonPrimary),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("digital_bus_pass_card")
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.linearGradient(
                colors = listOf(
                  MaroonDark,
                  MaroonDeep,
                  Color(0xFF2B060B)
                )
              )
            )
            .padding(20.dp)
        ) {
          // Pass Header
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Image(
                painter = painterResource(id = R.drawable.sada_shundori_icon),
                contentDescription = "Sada Shundori Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                  .border(1.dp, AccentGold, CircleShape)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "SADA SHUNDORI • PASS",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Black,
                  letterSpacing = 1.sp,
                  color = BusWhite
                )
                Text(
                  text = "DU AFFILIATED 7 COLLEGES",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = AccentGold
                )
              }
            }

            Surface(
              color = StatusGreen.copy(alpha = 0.2f),
              shape = RoundedCornerShape(6.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, StatusGreen)
            ) {
              Text(
                text = "ACTIVE",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = StatusGreen,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          // Student Details and QR Code Simulator
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = userProfile.fullName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BusWhite
              )
              Text(
                text = "${userProfile.department} (${userProfile.session})",
                fontSize = 12.sp,
                color = AccentGold
              )
              Text(
                text = userProfile.email,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
              )

              Spacer(modifier = Modifier.height(8.dp))

              Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column {
                  Text(text = "ROLL ID", fontSize = 9.sp, color = TextMuted)
                  Text(text = userProfile.studentId, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = BusWhite)
                }
                Column {
                  Text(text = "BLOOD GROUP", fontSize = 9.sp, color = TextMuted)
                  Text(text = userProfile.bloodGroup, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaroonGlow)
                }
              }
            }

            // QR Code Box Representation
            Box(
              modifier = Modifier
                .size(76.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BusWhite)
                .padding(6.dp),
              contentAlignment = Alignment.Center
            ) {
              Canvas(modifier = Modifier.fillMaxSize()) {
                val s = size.width
                // Draw simulated QR matrix pattern
                drawRect(color = Color.Black, topLeft = Offset(0f, 0f), size = Size(s * 0.35f, s * 0.35f))
                drawRect(color = Color.White, topLeft = Offset(s * 0.08f, s * 0.08f), size = Size(s * 0.19f, s * 0.19f))
                drawRect(color = Color.Black, topLeft = Offset(s * 0.12f, s * 0.12f), size = Size(s * 0.11f, s * 0.11f))

                drawRect(color = Color.Black, topLeft = Offset(s * 0.65f, 0f), size = Size(s * 0.35f, s * 0.35f))
                drawRect(color = Color.White, topLeft = Offset(s * 0.73f, s * 0.08f), size = Size(s * 0.19f, s * 0.19f))
                drawRect(color = Color.Black, topLeft = Offset(s * 0.77f, s * 0.12f), size = Size(s * 0.11f, s * 0.11f))

                drawRect(color = Color.Black, topLeft = Offset(0f, s * 0.65f), size = Size(s * 0.35f, s * 0.35f))
                drawRect(color = Color.White, topLeft = Offset(s * 0.08f, s * 0.73f), size = Size(s * 0.19f, s * 0.19f))
                drawRect(color = Color.Black, topLeft = Offset(s * 0.12f, s * 0.77f), size = Size(s * 0.11f, s * 0.11f))

                // Center dots
                drawCircle(color = Color.Black, radius = s * 0.06f, center = Offset(s * 0.5f, s * 0.5f))
                drawRect(color = Color.Black, topLeft = Offset(s * 0.5f, s * 0.25f), size = Size(s * 0.12f, s * 0.12f))
                drawRect(color = Color.Black, topLeft = Offset(s * 0.35f, s * 0.65f), size = Size(s * 0.12f, s * 0.12f))
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Card Footer
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Pass ID: ${userProfile.passId}",
              fontSize = 10.sp,
              color = TextMuted
            )
            Text(
              text = userProfile.passValidity,
              fontSize = 10.sp,
              fontWeight = FontWeight.Medium,
              color = AccentGold
            )
          }
        }
      }
    }

    // Bus Services Directory Quick Navigation
    item {
      Text(
        text = "Campus Transit Services",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(8.dp))

      Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(8.dp)) {
          // Notice Board link
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { viewModel.navigateTo(AppScreen.NOTICES) }
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Assignment, contentDescription = null, tint = AccentAmber, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = "Notice Board & Announcements", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
              Text(text = "Schedules, exam specials, maintenance updates", fontSize = 11.sp, color = TextMuted)
            }
            Text(text = "➔", fontSize = 14.sp, color = TextMuted)
          }

          Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(OutlineDark))

          // Moderator & Crew view
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { viewModel.navigateTo(AppScreen.CREW) }
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Group, contentDescription = null, tint = MaroonGlow, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = "Meet the Crew & Transport Committee", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
              Text(text = "Drivers, conductors, student conveners & hotline", fontSize = 11.sp, color = TextMuted)
            }
            Text(text = "➔", fontSize = 14.sp, color = TextMuted)
          }

          Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(OutlineDark))

          // Brand Splash Showcase
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { viewModel.navigateTo(AppScreen.BRAND_SPLASH) }
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.DirectionsBus, contentDescription = null, tint = AccentGold, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = "SADA SHUNDORI Brand Showcase", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
              Text(text = "History, heritage & stylized campus fleet gallery", fontSize = 11.sp, color = TextMuted)
            }
            Text(text = "➔", fontSize = 14.sp, color = TextMuted)
          }
        }
      }
    }

    // App Preferences & Notification Settings
    item {
      Text(
        text = "Preferences & Alert Settings",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(8.dp))

      Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Approach Notification Toggle
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Notifications, contentDescription = null, tint = MaroonGlow, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(text = "Approach Alarm (5 mins)", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                Text(text = "Alert when bus is within 2 km of pickup", fontSize = 11.sp, color = TextMuted)
              }
            }
            Switch(
              checked = isApproachAlertEnabled,
              onCheckedChange = { isApproachAlertEnabled = it },
              colors = SwitchDefaults.colors(
                checkedThumbColor = BusWhite,
                checkedTrackColor = MaroonPrimary
              )
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Vibration Feedback Toggle
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Vibration, contentDescription = null, tint = AccentAmber, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(text = "Haptic Bus Vibration", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                Text(text = "Vibrate on arrival & stop transitions", fontSize = 11.sp, color = TextMuted)
              }
            }
            Switch(
              checked = isVibrationEnabled,
              onCheckedChange = { isVibrationEnabled = it },
              colors = SwitchDefaults.colors(
                checkedThumbColor = BusWhite,
                checkedTrackColor = MaroonPrimary
              )
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Dark Theme Badge
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.DarkMode, contentDescription = null, tint = AccentGold, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(text = "Maroon Dark Theme", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                Text(text = "Optimized for battery & night commutes", fontSize = 11.sp, color = TextMuted)
              }
            }
            Surface(
              color = MaroonContainer,
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                text = "ENABLED",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
        }
      }
    }

    // Emergency Helpline Call Button & Logout
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Emergency Call
        Button(
          onClick = {
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+8801712345678"))
            try {
              context.startActivity(dialIntent)
            } catch (e: Exception) {
              // Ignore
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "DU Transport SOS", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        // Logout
        Button(
          onClick = { viewModel.logout() },
          colors = ButtonDefaults.buttonColors(containerColor = SurfaceElevated),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.ExitToApp, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Switch / Sign Out", fontSize = 12.sp, color = TextSecondary)
        }
      }

      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}
