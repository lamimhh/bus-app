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
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.viewmodel.BusViewModel

// Color palette matching the reference design (10_3475f3ae.jpeg)
private val CreamBackground = Color(0xFFFAF7F2)
private val DeepMaroon = Color(0xFF4A0E17)
private val DeepMaroonDark = Color(0xFF2C080E)
private val CardBorderColor = Color(0xFFEADBCE)
private val GoldAccent = Color(0xFFC59B27)
private val GoldBorder = Color(0xFFE2C475)
private val GoldCornerBracket = Color(0xFFC8A878)
private val GoldLabel = Color(0xFFB09060)
private val TextMain = Color(0xFF1E1E1E)
private val TextSub = Color(0xFF555555)
private val TextMuted = Color(0xFF757575)

private const val URL_MESSENGER = "https://m.me/cm/4pkb_W-NP7Cd3sWD/?send_source=cm%3Acopy_invite_link"
private const val URL_FACEBOOK = "https://www.facebook.com/share/g/1EjspKMeok/"
private const val URL_WEBSITE = "https://sadasundoricommunity.netlify.app/"

@Composable
fun CommunityScreen(
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
      .testTag("community_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .padding(bottom = 36.dp)
    ) {
      // 1. TOP BAR (Back Button + SADA SHUNDORI Logo Crest)
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
            .testTag("button_back_community")
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

      Spacer(modifier = Modifier.height(16.dp))

      // 2. HEADER TITLES & ARTWORK SECTION
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        // "OFFICIAL COMMUNITY" with subtle divider line
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier.padding(horizontal = 24.dp)
        ) {
          Box(
            modifier = Modifier
              .width(28.dp)
              .height(1.dp)
              .background(GoldBorder)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "OFFICIAL COMMUNITY",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            color = GoldAccent
          )
          Spacer(modifier = Modifier.width(8.dp))
          Box(
            modifier = Modifier
              .width(28.dp)
              .height(1.dp)
              .background(GoldBorder)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Large serif heading: "Stay Connected"
        Text(
          text = "Stay Connected",
          fontSize = 32.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Serif,
          color = DeepMaroonDark,
          letterSpacing = (-0.5).sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
          text = "The SADA SHUNDORI community brings us together through updates, announcements and shared journeys.",
          fontSize = 13.5.sp,
          lineHeight = 19.sp,
          textAlign = TextAlign.Center,
          color = TextSub,
          modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Central Illustrated Transit Vector
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color.Transparent),
          border = BorderStroke(0.8.dp, CardBorderColor.copy(alpha = 0.6f)),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.community_transit_art),
            contentDescription = "Sada Shundori Community Transit Art",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(20.dp))
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 3. INTERACTIVE COMMUNITY CARDS

      // CARD 1: MESSENGER COMMUNITY CARD
      CommunityInteractiveCard(
        title = "Join Our\nMessenger Community",
        subtitle = "Connect with fellow passengers, discuss updates, and stay informed.",
        buttonText = "JOIN COMMUNITY",
        url = URL_MESSENGER,
        qrDrawable = R.drawable.qr_messenger,
        scanLabel = "SCAN TO JOIN",
        iconType = CommunityIconType.MESSENGER,
        testTag = "card_messenger_community",
        onActionClick = {
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_MESSENGER))
          context.startActivity(intent)
        }
      )

      Spacer(modifier = Modifier.height(16.dp))

      // CARD 2: FACEBOOK PAGE / GROUP CARD
      CommunityInteractiveCard(
        title = "Follow Us on\nFacebook",
        subtitle = "Follow our official page for announcements, photos, events, and community news.",
        buttonText = "VISIT FACEBOOK PAGE",
        url = URL_FACEBOOK,
        qrDrawable = R.drawable.qr_facebook,
        scanLabel = "SCAN TO VISIT",
        iconType = CommunityIconType.FACEBOOK,
        testTag = "card_facebook_page",
        onActionClick = {
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_FACEBOOK))
          context.startActivity(intent)
        }
      )

      Spacer(modifier = Modifier.height(16.dp))

      // CARD 3: OFFICIAL WEB PORTAL CARD
      CommunityInteractiveCard(
        title = "Official\nWeb Portal",
        subtitle = "Explore our online web platform and resources.",
        buttonText = "VISIT WEBSITE",
        url = URL_WEBSITE,
        qrDrawable = R.drawable.qr_website,
        scanLabel = "SCAN TO VISIT",
        iconType = CommunityIconType.WEBSITE,
        testTag = "card_official_website",
        onActionClick = {
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_WEBSITE))
          context.startActivity(intent)
        }
      )

      Spacer(modifier = Modifier.height(30.dp))

      // 4. FOOTER SECTION
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
      ) {
        // Centered small bus icon with subtle golden divider lines
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxWidth()
        ) {
          Box(
            modifier = Modifier
              .width(42.dp)
              .height(1.dp)
              .background(GoldBorder)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Icon(
            imageVector = Icons.Default.DirectionsBus,
            contentDescription = null,
            tint = GoldAccent,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Box(
            modifier = Modifier
              .width(42.dp)
              .height(1.dp)
              .background(GoldBorder)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Thank you for being part of ",
            fontSize = 12.5.sp,
            color = TextSub
          )
          Text(
            text = "SADA SHUNDORI.",
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Bold,
            color = DeepMaroon
          )
        }
      }
    }
  }
}

// ---------------------------------------------------------------------------------------
// Sub-Composables & Helpers
// ---------------------------------------------------------------------------------------

private enum class CommunityIconType {
  MESSENGER,
  FACEBOOK,
  WEBSITE
}

@Composable
private fun CommunityInteractiveCard(
  title: String,
  subtitle: String,
  buttonText: String,
  url: String,
  @DrawableRes qrDrawable: Int,
  scanLabel: String,
  iconType: CommunityIconType,
  testTag: String,
  onActionClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = BorderStroke(1.dp, CardBorderColor),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .testTag(testTag)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      // Left Icon with 3D drop shadow & border
      Box(
        modifier = Modifier.padding(end = 14.dp)
      ) {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color.White,
          border = BorderStroke(1.dp, CardBorderColor.copy(alpha = 0.8f)),
          shadowElevation = 3.dp,
          modifier = Modifier.size(66.dp)
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
          ) {
            when (iconType) {
              CommunityIconType.MESSENGER -> {
                // Official Messenger gradient circle with white lightning bolt
                Box(
                  contentAlignment = Alignment.Center,
                  modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                      Brush.linearGradient(
                        colors = listOf(
                          Color(0xFF00B2FE),
                          Color(0xFF9B30FF),
                          Color(0xFFFF4D6D),
                          Color(0xFFFF7843)
                        )
                      )
                    )
                ) {
                  // White Messenger Chat Bubble with Lightning Shape
                  Text(
                    text = "⚡",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
              CommunityIconType.FACEBOOK -> {
                // Official Facebook blue circle with bold "f"
                Box(
                  contentAlignment = Alignment.Center,
                  modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1877F2))
                ) {
                  Text(
                    text = "f",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(bottom = 2.dp)
                  )
                }
              }
              CommunityIconType.WEBSITE -> {
                // Official Web Portal cyan/teal gradient circle with Globe
                Box(
                  contentAlignment = Alignment.Center,
                  modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                      Brush.linearGradient(
                        colors = listOf(
                          Color(0xFF0E7490),
                          Color(0xFF06B6D4)
                        )
                      )
                    )
                ) {
                  Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = "Website",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                  )
                }
              }
            }
          }
        }
      }

      // Middle Column: Title, Subtitle, and Action Button
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = title,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Serif,
          color = DeepMaroonDark,
          lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
          text = subtitle,
          fontSize = 11.5.sp,
          lineHeight = 15.sp,
          color = TextSub
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Styled Deep Maroon Pill Button
        Button(
          onClick = onActionClick,
          colors = ButtonDefaults.buttonColors(
            containerColor = DeepMaroon,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(50),
          modifier = Modifier
            .height(34.dp)
            .shadow(2.dp, RoundedCornerShape(50))
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = buttonText,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.6.sp,
              color = Color.White
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
              imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(12.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(10.dp))

      // Right Column: QR Box with Golden Corner Brackets
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(76.dp)
      ) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(72.dp)
            .padding(2.dp)
        ) {
          // Corner bracket decorations
          Box(
            modifier = Modifier
              .matchParentSize()
              .border(1.dp, GoldCornerBracket.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
          )

          // QR Code Image
          Image(
            painter = painterResource(id = qrDrawable),
            contentDescription = scanLabel,
            contentScale = ContentScale.Fit,
            modifier = Modifier
              .size(62.dp)
              .clip(RoundedCornerShape(6.dp))
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = scanLabel,
          fontSize = 8.5.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.8.sp,
          color = GoldLabel,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}
