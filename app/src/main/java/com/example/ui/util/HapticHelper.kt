package com.example.ui.util

import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role

/**
 * Standard tactile haptic feedback types for premium user interaction.
 */
enum class HapticType {
  LIGHT,      // Subtle feedback for chips, toggles, list items, minor options
  MEDIUM,     // Distinct tactile tap for navigation tabs, buttons, dialog triggers
  STRONG,     // Firmer impact for important commitments, calls, submit actions
  SUCCESS,    // Confirmation feedback on successful completion
  ERROR       // Feedback on validation rejection or alert
}

/**
 * Centralized, production-grade tactile haptic feedback utility.
 * Gracefully handles device support, platform versions, and settings.
 */
object HapticHelper {

  /**
   * Triggers a native device haptic vibration on user interaction.
   * Silently ignores calls if device vibrator or view is unavailable.
   */
  fun trigger(view: View?, type: HapticType = HapticType.LIGHT) {
    if (view == null) return
    try {
      val feedbackConstant = when (type) {
        HapticType.LIGHT -> {
          HapticFeedbackConstants.KEYBOARD_TAP
        }
        HapticType.MEDIUM -> {
          HapticFeedbackConstants.VIRTUAL_KEY
        }
        HapticType.STRONG -> {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            HapticFeedbackConstants.CONFIRM
          } else {
            HapticFeedbackConstants.VIRTUAL_KEY
          }
        }
        HapticType.SUCCESS -> {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            HapticFeedbackConstants.CONFIRM
          } else {
            HapticFeedbackConstants.VIRTUAL_KEY
          }
        }
        HapticType.ERROR -> {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            HapticFeedbackConstants.REJECT
          } else {
            HapticFeedbackConstants.LONG_PRESS
          }
        }
      }
      view.performHapticFeedback(feedbackConstant)
    } catch (_: Throwable) {
      // Graceful fallback: do not interrupt user operation
    }
  }
}

/**
 * Convenient extension on View to trigger a haptic pulse cleanly.
 */
fun View?.triggerHaptic(type: HapticType = HapticType.LIGHT) {
  HapticHelper.trigger(this, type)
}

/**
 * Composable clickable modifier that automatically produces tactile haptic feedback on genuine click.
 */
@Composable
fun Modifier.hapticClickable(
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  hapticType: HapticType = HapticType.LIGHT,
  onClick: () -> Unit
): Modifier {
  val view = LocalView.current
  return this.clickable(
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role
  ) {
    HapticHelper.trigger(view, hapticType)
    onClick()
  }
}
