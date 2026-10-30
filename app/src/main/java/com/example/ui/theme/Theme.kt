package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SadaShundoriDarkColorScheme = darkColorScheme(
  primary = MaroonPrimary,
  onPrimary = BusWhite,
  primaryContainer = MaroonContainer,
  onPrimaryContainer = Color(0xFFFFD9DD),
  secondary = AccentAmber,
  onSecondary = Color(0xFF1E1000),
  secondaryContainer = Color(0xFF452B00),
  onSecondaryContainer = AccentGold,
  tertiary = MaroonGlow,
  onTertiary = BusWhite,
  tertiaryContainer = MaroonDark,
  onTertiaryContainer = Color(0xFFFFD9DD),
  background = BackgroundDark,
  onBackground = TextPrimary,
  surface = SurfaceDark,
  onSurface = TextPrimary,
  surfaceVariant = SurfaceElevated,
  onSurfaceVariant = TextSecondary,
  outline = OutlineDark,
  outlineVariant = Color(0xFF262A3B),
  error = StatusRed,
  onError = BusWhite
)

private val SadaShundoriLightColorScheme = darkColorScheme( // Keep dark-first aesthetic
  primary = MaroonPrimary,
  onPrimary = BusWhite,
  primaryContainer = MaroonContainer,
  onPrimaryContainer = Color(0xFFFFD9DD),
  secondary = AccentAmber,
  onSecondary = Color(0xFF1E1000),
  background = BackgroundDark,
  onBackground = TextPrimary,
  surface = SurfaceDark,
  onSurface = TextPrimary,
  surfaceVariant = SurfaceElevated,
  onSurfaceVariant = TextSecondary,
  outline = OutlineDark,
  error = StatusRed
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep brand aesthetic
  content: @Composable () -> Unit,
) {
  val colorScheme = SadaShundoriDarkColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
