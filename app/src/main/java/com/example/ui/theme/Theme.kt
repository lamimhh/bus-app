package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Comprehensive theme token container for the SADA SHUNDORI application.
 * All screens and components inherit these tokens dynamically.
 */
data class SadaColors(
  val isDark: Boolean,
  val background: Color,
  val backgroundEnd: Color,
  val cardBackground: Color,
  val cardSurface: Color,
  val cardBorder: Color,
  val cardBorderSubtle: Color,
  val textPrimary: Color,
  val textSecondary: Color,
  val textMuted: Color,
  val headerText: Color,
  val maroonPrimary: Color,
  val maroonDark: Color,
  val maroonDeep: Color,
  val maroonContainer: Color,
  val maroonGlow: Color,
  val accentGold: Color,
  val goldBadgeBg: Color,
  val goldBorder: Color,
  val softPillBg: Color,
  val inputBackground: Color,
  val inputBorder: Color,
  val dividerColor: Color,
  val iconTint: Color,
  val statusBarColor: Color,
  val mapBackground: Color,
  val mapRoad: Color,
  val mapWater: Color,
  val mapRouteCompleted: Color,
  val mapRouteRemaining: Color,
  val primaryButtonGradient: List<Color>,
  val cardGradient: List<Color>
)

val LightSadaColors = SadaColors(
  isDark = false,
  background = LightBackground, // #FAF7F2
  backgroundEnd = LightBackgroundEnd, // #F3ECE4
  cardBackground = LightCardBackground, // #FFFFFF
  cardSurface = LightCardSurface,
  cardBorder = LightCardBorder, // #EADBCE
  cardBorderSubtle = LightCardBorderSubtle,
  textPrimary = LightTextPrimary, // #1E1E1E
  textSecondary = LightTextSecondary, // #555555
  textMuted = LightTextMuted, // #757575
  headerText = LightHeaderText, // #38080F
  maroonPrimary = MaroonDeep, // #4A0A12
  maroonDark = Color(0xFF38080F),
  maroonDeep = MaroonDeep,
  maroonContainer = Color(0xFF4A0E17),
  maroonGlow = Color(0xFFDC2626),
  accentGold = GoldAccentLight, // #C59B27
  goldBadgeBg = Color(0xFFFFF9E6),
  goldBorder = Color(0xFFE2C475),
  softPillBg = LightSoftPill, // #FBECEE
  inputBackground = LightInputBg, // #FFFFFF
  inputBorder = LightInputBorder, // #DDD6CE
  dividerColor = LightCardBorder,
  iconTint = Color(0xFF4A0E17),
  statusBarColor = LightBackground,
  mapBackground = LightMapBg,
  mapRoad = LightMapRoad,
  mapWater = LightMapWater,
  mapRouteCompleted = LightMapRouteCompleted,
  mapRouteRemaining = LightMapRouteRemaining,
  primaryButtonGradient = listOf(Color(0xFF5A0C16), Color(0xFF38060E)),
  cardGradient = listOf(Color(0xFFFFFFFF), Color(0xFFFAF6F2))
)

val DarkSadaColors = SadaColors(
  isDark = true,
  background = DarkBackground, // #0C0507
  backgroundEnd = DarkBackgroundEnd, // #070305
  cardBackground = DarkCardBackground, // #15090D
  cardSurface = DarkCardSurface, // #1A0B10
  cardBorder = DarkCardBorder, // #331119
  cardBorderSubtle = DarkCardBorderSubtle, // #260D13
  textPrimary = DarkTextPrimary, // #F8FAFC
  textSecondary = DarkTextSecondary, // #A89DA2
  textMuted = DarkTextMuted, // #756A6F
  headerText = DarkHeaderText, // #FFFFFF
  maroonPrimary = Color(0xFFEF4444), // Glowing Crimson Accent
  maroonDark = Color(0xFF881324),
  maroonDeep = Color(0xFF15090D),
  maroonContainer = Color(0xFF260D13),
  maroonGlow = Color(0xFFF87171),
  accentGold = AccentGold, // #FCD34D
  goldBadgeBg = Color(0xFF2A1C0A),
  goldBorder = Color(0xFFD4AF37),
  softPillBg = DarkSoftPill, // #260D13
  inputBackground = DarkInputBg, // #12070A
  inputBorder = DarkInputBorder, // #331119
  dividerColor = Color(0xFF260D13),
  iconTint = Color(0xFFEF4444),
  statusBarColor = DarkBackground,
  mapBackground = DarkMapBg,
  mapRoad = DarkMapRoad,
  mapWater = DarkMapWater,
  mapRouteCompleted = DarkMapRouteCompleted,
  mapRouteRemaining = DarkMapRouteRemaining,
  primaryButtonGradient = listOf(Color(0xFF6B1120), Color(0xFF4A0A14), Color(0xFF6B1120)),
  cardGradient = listOf(Color(0xFF16090D), Color(0xFF110609))
)

val LocalSadaColors = staticCompositionLocalOf { LightSadaColors }
val LocalIsDarkMode = compositionLocalOf { false }

object SadaTheme {
  val colors: SadaColors
    @Composable
    get() = LocalSadaColors.current

  val isDark: Boolean
    @Composable
    get() = LocalIsDarkMode.current
}

// Material 3 Dark Color Scheme
private val SadaShundoriDarkColorScheme = darkColorScheme(
  primary = Color(0xFFEF4444),
  onPrimary = BusWhite,
  primaryContainer = DarkCardBackground,
  onPrimaryContainer = Color(0xFFFFD9DD),
  secondary = AccentGold,
  onSecondary = Color(0xFF1E1000),
  secondaryContainer = Color(0xFF452B00),
  onSecondaryContainer = AccentGold,
  tertiary = MaroonGlow,
  onTertiary = BusWhite,
  tertiaryContainer = DarkCardSurface,
  onTertiaryContainer = Color(0xFFFFD9DD),
  background = DarkBackground,
  onBackground = DarkTextPrimary,
  surface = DarkCardBackground,
  onSurface = DarkTextPrimary,
  surfaceVariant = Color(0xFF1E0C12),
  onSurfaceVariant = DarkTextSecondary,
  outline = DarkCardBorder,
  outlineVariant = Color(0xFF260D13),
  error = StatusRed,
  onError = BusWhite
)

// Material 3 Light Color Scheme
private val SadaShundoriLightColorScheme = lightColorScheme(
  primary = MaroonDeep,
  onPrimary = BusWhite,
  primaryContainer = Color(0xFFFBECEE),
  onPrimaryContainer = MaroonDeep,
  secondary = GoldAccentLight,
  onSecondary = BusWhite,
  secondaryContainer = Color(0xFFFFF9E6),
  onSecondaryContainer = Color(0xFF3D2C04),
  tertiary = MaroonPrimary,
  onTertiary = BusWhite,
  tertiaryContainer = Color(0xFFFCE8E8),
  onTertiaryContainer = MaroonDark,
  background = LightBackground,
  onBackground = LightTextPrimary,
  surface = LightCardBackground,
  onSurface = LightTextPrimary,
  surfaceVariant = LightBackgroundEnd,
  onSurfaceVariant = LightTextSecondary,
  outline = LightCardBorder,
  outlineVariant = LightInputBorder,
  error = StatusRed,
  onError = BusWhite
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colors = if (darkTheme) DarkSadaColors else LightSadaColors
  val materialScheme = if (darkTheme) SadaShundoriDarkColorScheme else SadaShundoriLightColorScheme

  CompositionLocalProvider(
    LocalSadaColors provides colors,
    LocalIsDarkMode provides darkTheme
  ) {
    MaterialTheme(
      colorScheme = materialScheme,
      typography = Typography,
      content = content
    )
  }
}
