package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.AppScreen
import com.example.model.Notice
import com.example.viewmodel.BusViewModel

// Color palette matching the reference design (6.jpeg)
private val CreamBackground = Color(0xFFFAF7F2)
private val DeepMaroon = Color(0xFF4A0E17)
private val DeepMaroonDark = Color(0xFF2C080E)
private val SoftBlushCircle = Color(0xFFFBECEE)
private val CardBorderColor = Color(0xFFEADBCE)
private val GoldBorderColor = Color(0xFFD4AF37)
private val GoldBadgeBg = Color(0xFFFFF9E6)
private val GoldAccent = Color(0xFFB8860B)
private val TextMain = Color(0xFF1E1E1E)
private val TextSub = Color(0xFF555555)
private val TextMutedDate = Color(0xFF757575)

@Composable
fun NoticeAndComplaintScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val notices by viewModel.allNotices.collectAsState()
  var selectedNoticeForModal by remember { mutableStateOf<Notice?>(null) }

  // System back gesture and back navigation handling
  BackHandler {
    viewModel.navigateBack()
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(CreamBackground)
      .testTag("notice_board_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
    ) {
      Spacer(modifier = Modifier.height(8.dp))

      // 1. TOP HEADER (Back Button + SADA SHUNDORI Branding)
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
            .clickable { viewModel.navigateBack() }
            .testTag("button_back_notice_board")
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

        // Center Brand Header matching reference screenshot
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          // Stylized S emblem
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
              color = TextMutedDate,
              fontSize = 8.sp,
              fontWeight = FontWeight.Medium,
              letterSpacing = 1.6.sp
            )
          }
        }

        Spacer(modifier = Modifier.weight(1.3f))
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 2. HERO SECTION (Title, Subtitle & Megaphone Art)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Notice Board",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DeepMaroonDark,
            letterSpacing = (-0.3).sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Everything important, all in one place",
            fontSize = 13.5.sp,
            color = TextSub
          )
        }

        // Megaphone Line Art Icon
        Icon(
          imageVector = Icons.Outlined.Campaign,
          contentDescription = null,
          tint = DeepMaroon,
          modifier = Modifier.size(52.dp)
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      // 3. SECTION LABEL "Notice" with accent bar
      Column {
        Text(
          text = "Notice",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = DeepMaroon
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
          modifier = Modifier
            .width(26.dp)
            .height(3.5.dp)
            .background(DeepMaroon, RoundedCornerShape(2.dp))
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 4. NOTICE CARDS LIST
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      ) {
        items(notices, key = { it.id }) { notice ->
          NoticeItemCard(
            notice = notice,
            onClick = { selectedNoticeForModal = notice }
          )
        }

        // Bottom link to Complaint Box
        item {
          Spacer(modifier = Modifier.height(6.dp))
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = BorderStroke(1.dp, CardBorderColor),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { viewModel.navigateTo(AppScreen.COMPLAINT_BOX) }
              .padding(bottom = 24.dp)
              .testTag("link_complaint_box")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(14.dp)
            ) {
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                  .size(36.dp)
                  .background(SoftBlushCircle, CircleShape)
              ) {
                Icon(
                  imageVector = Icons.Outlined.ChatBubbleOutline,
                  contentDescription = null,
                  tint = DeepMaroon,
                  modifier = Modifier.size(18.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Have an issue or concern?",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.5.sp,
                  color = TextMain
                )
                Text(
                  text = "Submit a formal report to the bus team in Complaint Box",
                  fontSize = 11.sp,
                  color = TextSub
                )
              }
              Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF888888),
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    }
  }

  // 5. DETAILED DOCUMENT MODAL
  selectedNoticeForModal?.let { notice ->
    NoticeDocumentModal(
      notice = notice,
      onDismiss = { selectedNoticeForModal = null },
      onShare = {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
          type = "text/plain"
          putExtra(Intent.EXTRA_SUBJECT, notice.title)
          putExtra(
            Intent.EXTRA_TEXT,
            "SADA SHUNDORI OFFICIAL NOTICE\nDate: ${notice.date}\nRef: ${notice.referenceCode}\n\n${notice.title}\n\n${notice.fullBody}\n\n- ${notice.author}"
          )
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Official Notice"))
      }
    )
  }
}

// ---------------------------------------------------------------------------------------
// Sub-Composables
// ---------------------------------------------------------------------------------------

@Composable
private fun NoticeItemCard(
  notice: Notice,
  onClick: () -> Unit
) {
  val isPinned = notice.isPinned

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isPinned) Color(0xFFFFFDF8) else Color.White
    ),
    border = if (isPinned) {
      BorderStroke(1.5.dp, GoldBorderColor)
    } else {
      BorderStroke(1.dp, CardBorderColor)
    },
    elevation = CardDefaults.cardElevation(defaultElevation = if (isPinned) 2.dp else 1.dp),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag("notice_item_${notice.id}")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 14.dp)
    ) {
      // Icon Box (with appropriate symbol per notice type)
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(46.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(if (isPinned) GoldBadgeBg else SoftBlushCircle)
          .border(
            width = 0.8.dp,
            color = if (isPinned) GoldBorderColor else Color(0xFFF0D5D8),
            shape = RoundedCornerShape(12.dp)
          )
      ) {
        val icon = when (notice.category) {
          "Route" -> Icons.Outlined.Campaign
          "Schedule" -> Icons.Outlined.AccessTime
          "Alert" -> Icons.Outlined.WarningAmber
          "Community" -> Icons.Outlined.Event
          else -> Icons.Outlined.Description
        }

        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = if (isPinned) GoldAccent else DeepMaroon,
          modifier = Modifier.size(24.dp)
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      // Middle Column: Publish Date at top + Title
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Official Publish Date
          Text(
            text = notice.date,
            fontSize = 11.5.sp,
            color = TextMutedDate,
            fontWeight = FontWeight.Normal
          )

          // Pinned Badge
          if (isPinned) {
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
              shape = RoundedCornerShape(4.dp),
              color = GoldBadgeBg,
              border = BorderStroke(0.6.dp, GoldBorderColor)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Star,
                  contentDescription = null,
                  tint = GoldAccent,
                  modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                  text = "PINNED",
                  fontSize = 8.5.sp,
                  fontWeight = FontWeight.Bold,
                  color = GoldAccent
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Notice Title
        Text(
          text = notice.title,
          fontSize = 13.5.sp,
          fontWeight = FontWeight.Bold,
          color = TextMain,
          lineHeight = 18.sp
        )
      }

      Spacer(modifier = Modifier.width(8.dp))

      // Right Chevron
      Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = "View Details",
        tint = Color(0xFF9E9E9E),
        modifier = Modifier.size(20.dp)
      )
    }
  }
}

/**
 * Detailed Document Modal showing complete announcement text, reference, signatory and action buttons.
 */
@Composable
private fun NoticeDocumentModal(
  notice: Notice,
  onDismiss: () -> Unit,
  onShare: () -> Unit
) {
  val scrollState = rememberScrollState()

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      border = BorderStroke(1.dp, CardBorderColor),
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .padding(vertical = 24.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        // Modal Top Navigation / Close
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = if (notice.isPinned) GoldBadgeBg else SoftBlushCircle,
              border = BorderStroke(0.8.dp, if (notice.isPinned) GoldBorderColor else Color(0xFFF0D5D8))
            ) {
              Text(
                text = notice.category.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (notice.isPinned) GoldAccent else DeepMaroon,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }

            if (notice.isPinned) {
              Spacer(modifier = Modifier.width(6.dp))
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = GoldBadgeBg,
                border = BorderStroke(0.8.dp, GoldBorderColor)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = GoldAccent,
                    modifier = Modifier.size(11.dp)
                  )
                  Spacer(modifier = Modifier.width(3.dp))
                  Text(
                    text = "OFFICIAL PINNED",
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent
                  )
                }
              }
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = Color(0xFF666666),
              modifier = Modifier.size(20.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Official Title
        Text(
          text = notice.title,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = DeepMaroonDark,
          lineHeight = 23.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Published Date & Official Ref Number
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Published: ${notice.date}",
            fontSize = 11.5.sp,
            color = TextMutedDate,
            fontWeight = FontWeight.Medium
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "•  Ref: ${notice.referenceCode}",
            fontSize = 11.sp,
            color = DeepMaroon,
            fontWeight = FontWeight.SemiBold
          )
        }

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(color = CardBorderColor, thickness = 0.8.dp)
        Spacer(modifier = Modifier.height(14.dp))

        // Document Body in a scrollable view
        Column(
          modifier = Modifier
            .weight(1f, fill = false)
            .verticalScroll(scrollState)
            .background(CreamBackground, RoundedCornerShape(12.dp))
            .border(1.dp, CardBorderColor, RoundedCornerShape(12.dp))
            .padding(14.dp)
        ) {
          Text(
            text = notice.fullBody,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            color = TextMain
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Authorized Signatory seal block
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            border = BorderStroke(0.6.dp, CardBorderColor),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text(
                text = "ISSUED BY AUTHORIZED DESK",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = DeepMaroon
              )
              Text(
                text = notice.author,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextMain
              )
              Text(
                text = "Dhaka University Affiliated 7 Colleges Bus Operations",
                fontSize = 10.sp,
                color = TextSub
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Actions (Share & Close)
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          OutlinedButton(
            onClick = onShare,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, DeepMaroon),
            modifier = Modifier.weight(1f)
          ) {
            Icon(
              imageVector = Icons.Outlined.Share,
              contentDescription = null,
              tint = DeepMaroon,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Share", color = DeepMaroon, fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }

          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = DeepMaroon),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1.3f)
          ) {
            Text("Understood", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }
        }
      }
    }
  }
}
