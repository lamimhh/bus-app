package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.Complaint
import com.example.model.Notice
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
import com.example.ui.theme.StatusRed
import com.example.ui.theme.StatusYellow
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.BusViewModel

@Composable
fun NoticeAndComplaintScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) } // 0: Notices, 1: Complaints & Lost/Found
  val selectedCategory by viewModel.selectedNoticeCategory.collectAsState()
  val notices by viewModel.filteredNotices.collectAsState()
  val complaints by viewModel.complaints.collectAsState()
  val selectedDetail by viewModel.selectedNoticeDetail.collectAsState()
  val isComplaintFormOpen by viewModel.isComplaintSheetOpen.collectAsState()
  val successMessage by viewModel.complaintSuccessMessage.collectAsState()

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundDark)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
    ) {
      Spacer(modifier = Modifier.height(10.dp))

      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Notice Board & Student Box",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Text(
            text = "Official updates, complaints & lost property",
            fontSize = 12.sp,
            color = TextSecondary
          )
        }

        Surface(
          color = MaroonContainer,
          shape = RoundedCornerShape(12.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, MaroonDark)
        ) {
          Text(
            text = "7 Colleges Desk",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = AccentGold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Tab Switcher (Notices vs Complaints)
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = SurfaceDark,
        contentColor = BusWhite,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = MaroonPrimary
          )
        },
        modifier = Modifier
          .clip(RoundedCornerShape(14.dp))
          .border(1.dp, OutlineDark, RoundedCornerShape(14.dp))
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Campus Notices (${notices.size})", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            }
          }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Feedback, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Complaint / Lost & Found", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            }
          }
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      if (selectedTab == 0) {
        // --- NOTICES TAB ---
        // Category Filter Chips
        val categories = listOf("All", "Urgent", "Schedule", "Route", "Maintenance")
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(categories) { category ->
            val isSelected = selectedCategory == category
            Surface(
              color = if (isSelected) MaroonPrimary else SurfaceElevated,
              shape = RoundedCornerShape(20.dp),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isSelected) MaroonGlow else OutlineDark
              ),
              modifier = Modifier.clickable { viewModel.setNoticeCategory(category) }
            ) {
              Text(
                text = category,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) BusWhite else TextSecondary,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(notices, key = { it.id }) { notice ->
            NoticeItemCard(
              notice = notice,
              onClick = { viewModel.openNoticeDetail(notice) }
            )
          }

          item {
            Spacer(modifier = Modifier.height(60.dp))
          }
        }
      } else {
        // --- COMPLAINTS & LOST/FOUND TAB ---
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Tracked Submissions & Recovery",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary
          )

          Button(
            onClick = { viewModel.setComplaintSheetOpen(true) },
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("new_complaint_button")
          ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Submit Report", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(complaints, key = { it.id }) { complaint ->
            ComplaintItemCard(complaint = complaint)
          }

          item {
            Spacer(modifier = Modifier.height(60.dp))
          }
        }
      }
    }
  }

  // Notice Detail Dialog
  selectedDetail?.let { notice ->
    Dialog(onDismissRequest = { viewModel.closeNoticeDetail() }) {
      Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, MaroonPrimary),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              color = if (notice.isUrgent) StatusRed else MaroonPrimary,
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                text = notice.category.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = BusWhite,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }

            IconButton(onClick = { viewModel.closeNoticeDetail() }) {
              Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = notice.title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = "Posted: ${notice.date} • By ${notice.author}",
            fontSize = 11.sp,
            color = TextMuted
          )

          Spacer(modifier = Modifier.height(14.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(SurfaceElevated, RoundedCornerShape(12.dp))
              .padding(14.dp)
          ) {
            Text(
              text = notice.fullBody,
              fontSize = 13.sp,
              lineHeight = 19.sp,
              color = TextPrimary
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = { viewModel.closeNoticeDetail() },
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Understood")
          }
        }
      }
    }
  }

  // Interactive Complaint / Lost Property Submission Dialog
  if (isComplaintFormOpen) {
    ComplaintSubmissionModal(
      onDismiss = { viewModel.setComplaintSheetOpen(false) },
      onSubmit = { cat, title, desc, phone ->
        viewModel.submitComplaint(cat, title, desc, phone)
      },
      successMessage = successMessage
    )
  }
}

@Composable
private fun NoticeItemCard(
  notice: Notice,
  onClick: () -> Unit
) {
  Card(
    colors = CardDefaults.cardColors(
      containerColor = if (notice.isUrgent) MaroonContainer.copy(alpha = 0.35f) else SurfaceDark
    ),
    shape = RoundedCornerShape(16.dp),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (notice.isUrgent) MaroonGlow else OutlineDark
    ),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag("notice_card_${notice.id}")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          color = when (notice.category) {
            "Urgent" -> StatusRed.copy(alpha = 0.2f)
            "Schedule" -> AccentAmber.copy(alpha = 0.2f)
            "Route" -> StatusGreen.copy(alpha = 0.2f)
            else -> SurfaceElevated
          },
          shape = RoundedCornerShape(6.dp),
          border = androidx.compose.foundation.BorderStroke(
            0.5.dp,
            when (notice.category) {
              "Urgent" -> StatusRed
              "Schedule" -> AccentAmber
              "Route" -> StatusGreen
              else -> OutlineDark
            }
          )
        ) {
          Text(
            text = notice.category,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = when (notice.category) {
              "Urgent" -> StatusRed
              "Schedule" -> AccentAmber
              "Route" -> StatusGreen
              else -> TextSecondary
            },
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }

        Text(
          text = notice.date,
          fontSize = 11.sp,
          color = TextMuted
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = notice.title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = notice.summary,
        fontSize = 12.sp,
        color = TextSecondary,
        maxLines = 2
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "By ${notice.author}",
          fontSize = 10.sp,
          color = TextMuted
        )
        Text(
          text = "Read Full Details ➔",
          fontSize = 11.sp,
          fontWeight = FontWeight.SemiBold,
          color = AccentGold
        )
      }
    }
  }
}

@Composable
private fun ComplaintItemCard(complaint: Complaint) {
  Card(
    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
    shape = RoundedCornerShape(16.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            color = MaroonDark,
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = complaint.category,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = AccentGold,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Bus: ${complaint.busNumber}",
            fontSize = 10.sp,
            color = TextMuted
          )
        }

        // Status Badge
        Surface(
          color = when (complaint.status) {
            "Resolved" -> StatusGreen.copy(alpha = 0.2f)
            "Under Review" -> AccentAmber.copy(alpha = 0.2f)
            else -> MaroonPrimary.copy(alpha = 0.2f)
          },
          shape = RoundedCornerShape(6.dp),
          border = androidx.compose.foundation.BorderStroke(
            0.5.dp,
            when (complaint.status) {
              "Resolved" -> StatusGreen
              "Under Review" -> AccentAmber
              else -> MaroonPrimary
            }
          )
        ) {
          Text(
            text = complaint.status,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = when (complaint.status) {
              "Resolved" -> StatusGreen
              "Under Review" -> AccentAmber
              else -> MaroonGlow
            },
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = complaint.title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = complaint.description,
        fontSize = 12.sp,
        color = TextSecondary
      )

      // Official response notes if present
      complaint.officialResponse?.let { resp ->
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
          color = SurfaceElevated,
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = StatusGreen,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = resp,
              fontSize = 11.sp,
              color = TextPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Filed: ${complaint.timestamp}",
        fontSize = 10.sp,
        color = TextMuted
      )
    }
  }
}

@Composable
private fun ComplaintSubmissionModal(
  onDismiss: () -> Unit,
  onSubmit: (category: String, title: String, description: String, phone: String) -> Unit,
  successMessage: String?
) {
  var selectedCategory by remember { mutableStateOf("Lost Item") }
  var title by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }
  var contactPhone by remember { mutableStateOf("+880 1712-345678") }

  val categories = listOf("Lost Item", "Driving / Speed", "Seat Availability", "Cleanliness / AC", "Helper Behaviour")

  Dialog(onDismissRequest = onDismiss) {
    Card(
      colors = CardDefaults.cardColors(containerColor = SurfaceDark),
      shape = RoundedCornerShape(20.dp),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, MaroonPrimary),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Submit Complaint / Lost Item",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )

          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
          }
        }

        if (successMessage != null) {
          Surface(
            color = StatusGreen.copy(alpha = 0.2f),
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, StatusGreen),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.CheckCircle, contentDescription = null, tint = StatusGreen)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = successMessage,
                fontSize = 12.sp,
                color = BusWhite
              )
            }
          }
          Spacer(modifier = Modifier.height(14.dp))
          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Done")
          }
        } else {
          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Category",
            fontSize = 11.sp,
            color = TextSecondary
          )
          Spacer(modifier = Modifier.height(4.dp))

          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(categories) { cat ->
              val isSel = selectedCategory == cat
              Surface(
                color = if (isSel) MaroonPrimary else SurfaceElevated,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, if (isSel) MaroonGlow else OutlineDark),
                modifier = Modifier.clickable { selectedCategory = cat }
              ) {
                Text(
                  text = cat,
                  fontSize = 10.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSel) BusWhite else TextSecondary,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Subject / Item Name") },
            placeholder = { Text("e.g. Lost Black Backpack or Blower Issue") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = MaroonPrimary,
              unfocusedBorderColor = OutlineDark,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("complaint_title_field")
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Details & Description") },
            placeholder = { Text("Mention seat position, time, or specific issue...") },
            minLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = MaroonPrimary,
              unfocusedBorderColor = OutlineDark,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("complaint_desc_field")
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = contactPhone,
            onValueChange = { contactPhone = it },
            label = { Text("Student Contact Phone") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = MaroonGlow) },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = MaroonPrimary,
              unfocusedBorderColor = OutlineDark,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = {
              if (title.isNotBlank()) {
                onSubmit(selectedCategory, title, description, contactPhone)
              }
            },
            enabled = title.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = MaroonPrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("complaint_submit_btn")
          ) {
            Text("Submit Report", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
