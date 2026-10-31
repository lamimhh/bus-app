package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.viewmodel.BusViewModel

// Color palette matching the reference design
private val CreamBackground = Color(0xFFFAF7F2)
private val DeepMaroon = Color(0xFF4A0E17)
private val DeepMaroonDark = Color(0xFF2C080E)
private val RichWine = Color(0xFF3B0A12)
private val CardBorderColor = Color(0xFFEADBCE)
private val GoldAccent = Color(0xFFC59B27)
private val GoldBadgeBg = Color(0xFFFFF9E6)
private val GoldBorder = Color(0xFFE2C475)
private val SoftBlushCircle = Color(0xFFFBECEE)
private val TextMain = Color(0xFF1E1E1E)
private val TextSub = Color(0xFF555555)
private val TextMuted = Color(0xFF757575)

@Composable
fun CrewScreen(
  viewModel: BusViewModel,
  onBackClick: () -> Unit = { viewModel.navigateBack() },
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  // Smooth back-swipe navigation
  BackHandler {
    onBackClick()
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(CreamBackground)
      .testTag("crew_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .padding(bottom = 32.dp)
    ) {
      // 1. HEADER & BACK ACTION
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      ) {
        // Back Button
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = Color.White,
          border = BorderStroke(1.dp, CardBorderColor),
          modifier = Modifier
            .size(38.dp)
            .clickable { onBackClick() }
            .testTag("button_back_crew")
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = DeepMaroon,
              modifier = Modifier.size(19.dp)
            )
          }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Center SADA SHUNDORI logo crest
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = DeepMaroon,
            modifier = Modifier.size(32.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = "S",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 19.sp,
                fontFamily = FontFamily.Serif
              )
            }
          }

          Spacer(modifier = Modifier.width(8.dp))

          Column {
            Text(
              text = "SADA\nSHUNDORI",
              color = DeepMaroon,
              fontWeight = FontWeight.Black,
              fontSize = 12.sp,
              lineHeight = 13.sp,
              fontFamily = FontFamily.Serif,
              letterSpacing = 0.5.sp
            )
            Text(
              text = "LIVE BUS COMPANION",
              color = TextMuted,
              fontSize = 8.sp,
              fontWeight = FontWeight.Medium,
              letterSpacing = 1.6.sp
            )
          }
        }

        Spacer(modifier = Modifier.weight(1.3f))
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 2. TITLES SECTION
      Column(
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "THE PEOPLE BEHIND EVERY JOURNEY",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.6.sp,
          color = GoldAccent
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Meet the Crew",
          fontSize = 28.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Serif,
          color = DeepMaroonDark,
          letterSpacing = (-0.5).sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "The familiar faces that keep us moving.",
          fontSize = 13.5.sp,
          color = TextSub
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 3. CREW PROFILES CONNECTED BY A STYLIZED ROUTE LINE

      // PROFILE 1: MIZAN MAMA (DRIVER)
      CrewMemberCard(
        name = "Mizan Mama",
        bengaliName = "মিজান মামা — সিনিয়র চালক",
        role = "Lead Bus Pilot (Driver)",
        badgeLabel = "DRIVER / SENIOR PILOT",
        badgeIcon = Icons.Default.DirectionsBus,
        imageRes = R.drawable.mizan_mama,
        experience = "12+ Years on NU-Gazipur-Campus Route • Over 100,000 Safe Student Trips",
        motto = "“Students' safety and on-time arrival to the exam halls is our daily mission. We drive with care and responsibility.”",
        rating = "4.9",
        ratingDetail = "Senior Transport Pilot • DU Central Transport Pool",
        phone = "+880 1711-223344",
        onCallClick = {
          val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+8801711223344"))
          context.startActivity(callIntent)
        },
        highlights = listOf(
          "Master Fleet Pilot Award",
          "Zero Safety Violations",
          "Expressway Certified"
        )
      )

      // STYLIZED ROUTE CONNECTOR LINE
      RouteConnector()

      // PROFILE 2: SOHAG MAMA (HELPER)
      CrewMemberCard(
        name = "Sohag Mama",
        bengaliName = "সোহাগ মামা — বাস হেল্পার ও সহায়ক",
        role = "Bus Helper & Fleet Attendant",
        badgeLabel = "HELPER / ATTENDANT",
        badgeIcon = Icons.Default.Handshake,
        imageRes = R.drawable.sohag_mama,
        experience = "8+ Years Safeguarding Boarding Queues, Student Welfare & Baggage",
        motto = "“Every student on Sada Shundori is like family. We make sure everyone boards smoothly, finds a seat, and reaches home safely.”",
        rating = "4.9",
        ratingDetail = "Safety & Attendance Helper • DU 7 Colleges Support",
        phone = "+880 1819-556677",
        onCallClick = {
          val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+8801819556677"))
          context.startActivity(callIntent)
        },
        highlights = listOf(
          "Queue & Boarding Manager",
          "Lost & Found Custodian",
          "Friendly Student Guide"
        )
      )

      Spacer(modifier = Modifier.height(28.dp))

      // 4. FOOTER APPRECIATION CARD
      AppreciationCard()
    }
  }
}

// ---------------------------------------------------------------------------------------
// Sub-Composables
// ---------------------------------------------------------------------------------------

@Composable
private fun CrewMemberCard(
  name: String,
  bengaliName: String,
  role: String,
  badgeLabel: String,
  badgeIcon: androidx.compose.ui.graphics.vector.ImageVector,
  @DrawableRes imageRes: Int,
  experience: String,
  motto: String,
  rating: String,
  ratingDetail: String,
  phone: String,
  onCallClick: () -> Unit,
  highlights: List<String>
) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
    ) {
      // Circular Portrait Photo with Floating Badge
      Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.padding(bottom = 6.dp)
      ) {
        // Outer glow & double border
        Surface(
          shape = CircleShape,
          color = Color.White,
          border = BorderStroke(3.dp, DeepMaroon),
          modifier = Modifier
            .size(142.dp)
            .shadow(4.dp, CircleShape)
        ) {
          Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .fillMaxSize()
              .clip(CircleShape)
          )
        }

        // Floating Role Badge Pill
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = DeepMaroon,
          border = BorderStroke(1.5.dp, GoldBorder),
          shadowElevation = 3.dp,
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 0.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Icon(
              imageVector = badgeIcon,
              contentDescription = null,
              tint = GoldAccent,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = badgeLabel,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.8.sp,
              color = Color.White
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Name & Bengali Title
      Text(
        text = name,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        color = DeepMaroonDark
      )

      Text(
        text = bengaliName,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = GoldAccent
      )

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = role,
        fontSize = 13.5.sp,
        fontWeight = FontWeight.Medium,
        color = DeepMaroon
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Rating Pill
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = GoldBadgeBg,
        border = BorderStroke(0.8.dp, GoldBorder)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = GoldAccent,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "$rating ★",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "• $ratingDetail",
            fontSize = 10.5.sp,
            color = TextSub
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Experience & Route summary
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = CreamBackground,
        border = BorderStroke(0.8.dp, CardBorderColor),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(10.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Verified,
            contentDescription = null,
            tint = DeepMaroon,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = experience,
            fontSize = 11.5.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Medium,
            color = TextMain
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Motto Quote
      Text(
        text = motto,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center,
        color = TextSub,
        modifier = Modifier.padding(horizontal = 8.dp)
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Highlight Badges Row
      Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        highlights.forEach { tag ->
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = SoftBlushCircle,
            modifier = Modifier.weight(1f)
          ) {
            Text(
              text = tag,
              fontSize = 9.sp,
              fontWeight = FontWeight.SemiBold,
              color = DeepMaroon,
              textAlign = TextAlign.Center,
              modifier = Modifier.padding(vertical = 5.dp, horizontal = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Call Hotline Button
      Button(
        onClick = onCallClick,
        colors = ButtonDefaults.buttonColors(containerColor = DeepMaroon),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(
          imageVector = Icons.Default.Call,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Call ${name.split(" ").first()} Mama • $phone",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
      }
    }
  }
}

/**
 * Stylized Route Line connecting Driver and Helper nodes
 */
@Composable
private fun RouteConnector() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Box(
      modifier = Modifier
        .width(2.5.dp)
        .height(14.dp)
        .background(DeepMaroon)
    )

    Surface(
      shape = CircleShape,
      color = Color.White,
      border = BorderStroke(2.dp, GoldAccent),
      modifier = Modifier.size(24.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .background(DeepMaroon, CircleShape)
        )
      }
    }

    Box(
      modifier = Modifier
        .width(2.5.dp)
        .height(14.dp)
        .background(DeepMaroon)
    )
  }
}

/**
 * Footer Appreciation Card
 */
@Composable
private fun AppreciationCard() {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = RichWine),
    border = BorderStroke(1.dp, GoldBorder),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("appreciation_card")
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Shield,
        contentDescription = null,
        tint = GoldAccent,
        modifier = Modifier.size(28.dp)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "One team. Every journey.",
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        color = Color.White
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "Through monsoon rain, highway jams, early morning exam runs, and evening rush hours — Mizan Mama and Sohag Mama ensure every student of Dhaka University Affiliated 7 Colleges reaches campus with safety and peace of mind.",
        fontSize = 12.sp,
        lineHeight = 17.sp,
        textAlign = TextAlign.Center,
        color = Color(0xFFE2D6CB)
      )

      Spacer(modifier = Modifier.height(14.dp))
      HorizontalDivider(color = Color(0xFF671420), thickness = 0.8.dp)
      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Thank you, Mizan Mama & Sohag Mama! ♥",
        fontSize = 13.5.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.3.sp,
        color = Color(0xFFF7CF65),
        textAlign = TextAlign.Center
      )
    }
  }
}
