package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.BusViewModel

@Composable
fun BrandSplashScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val glowScale by infiniteTransition.animateFloat(
    initialValue = 0.98f,
    targetValue = 1.03f,
    animationSpec = infiniteRepeatable(
      animation = tween(1500, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glow_scale"
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundDark)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Hero Bus & Curzon Backdrop
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(340.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.sada_shundori_hero),
          contentDescription = "SADA SHUNDORI Fleet Showcase",
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )

        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  Color.Transparent,
                  BackgroundDark.copy(alpha = 0.7f),
                  BackgroundDark
                )
              )
            )
        )

        // Floating Gold Emblem
        Surface(
          color = MaroonDark.copy(alpha = 0.95f),
          shape = RoundedCornerShape(20.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, AccentGold),
          modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 48.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
          ) {
            Icon(Icons.Default.School, contentDescription = null, tint = AccentGold, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "DHAKA UNIVERSITY HERITAGE",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp,
              color = AccentGold
            )
          }
        }
      }

      // Brand Title & Slogan
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Image(
          painter = painterResource(id = R.drawable.sada_shundori_icon),
          contentDescription = "SADA SHUNDORI Official Logo",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(80.dp)
            .scale(glowScale)
            .clip(CircleShape)
            .border(2.5.dp, MaroonGlow, CircleShape)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "সাদা সুন্দরী",
          fontSize = 34.sp,
          fontWeight = FontWeight.Black,
          color = BusWhite,
          letterSpacing = 1.sp
        )

        Text(
          text = "SADA SHUNDORI",
          fontSize = 18.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 3.sp,
          color = MaroonPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "\"Your campus journey, connected\"",
          fontSize = 15.sp,
          fontWeight = FontWeight.Medium,
          color = AccentGold,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "The beloved lifeline connecting Gazipur, Tongi & Greater Dhaka to Curzon Hall and Nilkhet for thousands of Dhaka University & 7 Affiliated Colleges students.",
          fontSize = 12.sp,
          lineHeight = 18.sp,
          color = TextSecondary,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Fleet Key Highlights Card
        Card(
          colors = CardDefaults.cardColors(containerColor = SurfaceDark),
          shape = RoundedCornerShape(18.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, MaroonDark),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Fleet Specifications & Corridor",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              SpecItem(title = "Corridor", value = "43 km")
              SpecItem(title = "Stops", value = "15 Points")
              SpecItem(title = "Capacity", value = "52 Seats")
              SpecItem(title = "Trips/Day", value = "4 Runs")
            }
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Return to Dashboard Action Button
        Button(
          onClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
          colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("enter_tracker_button")
        ) {
          Text(
            text = "Enter Live Tracker Dashboard",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

@Composable
private fun SpecItem(title: String, value: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaroonGlow)
    Spacer(modifier = Modifier.height(2.dp))
    Text(text = title, fontSize = 10.sp, color = TextMuted)
  }
}
