package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.SadaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Minimal, aesthetic animated launch splash screen.
 * Displays the SADA SHUNDORI logo with smooth scaling, fading, and subtle ambient breathing.
 * Dynamically adapts to both Day/Light and Luxury Dark modes.
 */
@Composable
fun AestheticSplashScreen(
  onSplashComplete: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isDark = SadaTheme.isDark
  val colors = SadaTheme.colors

  // Animation drivers
  val logoScale = remember { Animatable(0.80f) }
  val logoAlpha = remember { Animatable(0f) }
  val textAlpha = remember { Animatable(0f) }
  val textOffsetY = remember { Animatable(18f) }
  val progressAnim = remember { Animatable(0f) }

  // Continuous subtle ambient pulse
  val infiniteTransition = rememberInfiniteTransition(label = "halo_pulse")
  val haloPulse by infiniteTransition.animateFloat(
    initialValue = 0.96f,
    targetValue = 1.05f,
    animationSpec = infiniteRepeatable(
      animation = tween(1600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "halo_scale"
  )
  val haloAlpha by infiniteTransition.animateFloat(
    initialValue = 0.35f,
    targetValue = 0.85f,
    animationSpec = infiniteRepeatable(
      animation = tween(1600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "halo_alpha"
  )

  LaunchedEffect(Unit) {
    // Stage 1: Logo scale & fade in smoothly
    launch {
      logoAlpha.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
      )
    }
    launch {
      logoScale.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
      )
    }

    // Stage 2: Typography fades and settles upward
    delay(280)
    launch {
      textAlpha.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
      )
    }
    launch {
      textOffsetY.animateTo(
        targetValue = 0f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
      )
    }

    // Stage 3: Minimal shimmer progress line
    delay(200)
    launch {
      progressAnim.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
      )
    }

    // Stage 4: Hold briefly and transition into the app
    delay(1150)
    onSplashComplete()
  }

  val backgroundBrush = if (isDark) {
    Brush.radialGradient(
      colors = listOf(
        Color(0xFF1E0A12), // Deep midnight crimson center
        Color(0xFF0F0508),
        Color(0xFF070305)  // Obsidian perimeter
      ),
      radius = 1100f
    )
  } else {
    Brush.radialGradient(
      colors = listOf(
        Color(0xFFFFFBF7), // Warm ivory center
        Color(0xFFFAF5EE),
        Color(0xFFF1EAE0)  // Soft cream perimeter
      ),
      radius = 1100f
    )
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(backgroundBrush)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onSplashComplete // Tap anytime to enter immediately
      )
      .testTag("aesthetic_splash_screen"),
    contentAlignment = Alignment.Center
  ) {
    // Center Hero Logo & Minimal Brand Typography
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .statusBarsPadding()
        .padding(horizontal = 24.dp)
    ) {
      // Ambient Breathing Ring + Logo
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .scale(logoScale.value)
          .alpha(logoAlpha.value)
      ) {
        // Outer glowing halo
        Box(
          modifier = Modifier
            .size(126.dp)
            .scale(haloPulse)
            .alpha(haloAlpha)
            .background(
              color = if (isDark) Color(0xFFEF4444).copy(alpha = 0.12f) else Color(0xFFC59B27).copy(alpha = 0.14f),
              shape = CircleShape
            )
        )

        // Middle stylized border ring
        Box(
          modifier = Modifier
            .size(110.dp)
            .border(
              width = 1.5.dp,
              brush = Brush.linearGradient(
                colors = if (isDark) {
                  listOf(Color(0xFFEF4444), Color(0xFFE2C475), Color(0xFF331119))
                } else {
                  listOf(Color(0xFFC59B27), Color(0xFF4A0A12), Color(0xFFEADBCE))
                }
              ),
              shape = CircleShape
            )
        )

        // Logo Container
        Surface(
          shape = CircleShape,
          color = colors.cardBackground,
          shadowElevation = if (isDark) 0.dp else 4.dp,
          modifier = Modifier.size(96.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.sada_shundori_icon),
            contentDescription = "SADA SHUNDORI Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .fillMaxSize()
              .clip(CircleShape)
              .padding(4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Minimal Serif Brand Typography
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .alpha(textAlpha.value)
          .padding(top = textOffsetY.value.dp)
      ) {
        Text(
          text = "SADA SHUNDORI",
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Serif,
          letterSpacing = 3.5.sp,
          color = colors.headerText,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Box(
            modifier = Modifier
              .width(18.dp)
              .height(1.dp)
              .background(colors.accentGold)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "সাদা সুন্দরী",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDark) Color(0xFFEF4444) else colors.maroonPrimary,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.width(8.dp))
          Box(
            modifier = Modifier
              .width(18.dp)
              .height(1.dp)
              .background(colors.accentGold)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "LIVE BUS COMPANION",
          fontSize = 10.sp,
          fontWeight = FontWeight.SemiBold,
          letterSpacing = 3.sp,
          color = colors.textSecondary
        )
      }
    }

    // Minimal Bottom Progress Track & Tagline
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .navigationBarsPadding()
        .padding(bottom = 32.dp)
        .alpha(textAlpha.value)
    ) {
      // Slender Shimmer Indicator Track (44dp wide)
      Box(
        modifier = Modifier
          .width(52.dp)
          .height(2.5.dp)
          .clip(RoundedCornerShape(2.dp))
          .background(if (isDark) Color(0xFF260D13) else Color(0xFFE8DFD5))
      ) {
        val fillWidth = (52 * progressAnim.value).dp
        Box(
          modifier = Modifier
            .width(fillWidth)
            .height(2.5.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(
              Brush.horizontalGradient(
                colors = if (isDark) {
                  listOf(Color(0xFF881324), Color(0xFFEF4444), Color(0xFFE2C475))
                } else {
                  listOf(Color(0xFF4A0A12), Color(0xFFC59B27))
                }
              )
            )
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "KNOWLEDGE · SERVICE · FUTURE",
        fontSize = 9.5.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 2.sp,
        color = colors.textMuted
      )
    }
  }
}
