package com.example.ui.util

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
 * Utility for triggering tactile haptic feedback (vibrations) on touch interactions.
 */
object HapticHelper {
  fun trigger(view: View?, feedbackConstant: Int = HapticFeedbackConstants.VIRTUAL_KEY) {
    view?.performHapticFeedback(feedbackConstant)
  }
}

/**
 * Extension on View to trigger haptic feedback cleanly.
 */
fun View?.triggerHaptic(feedbackConstant: Int = HapticFeedbackConstants.VIRTUAL_KEY) {
  this?.performHapticFeedback(feedbackConstant)
}

/**
 * Composable clickable modifier that automatically executes tactile haptic feedback on click.
 */
@Composable
fun Modifier.hapticClickable(
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  feedbackConstant: Int = HapticFeedbackConstants.VIRTUAL_KEY,
  onClick: () -> Unit
): Modifier {
  val view = LocalView.current
  return this.clickable(
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role
  ) {
    view.performHapticFeedback(feedbackConstant)
    onClick()
  }
}
