package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CrewMember
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentGold
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.BusWhite
import com.example.ui.theme.MaroonContainer
import com.example.ui.theme.MaroonDark
import com.example.ui.theme.MaroonGlow
import com.example.ui.theme.MaroonPrimary
import com.example.ui.theme.OutlineDark
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.StatusRed
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.BusViewModel

@Composable
fun CrewSectionScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val crewMembers by viewModel.crewMembers.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundDark)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))

      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Meet the Crew & Committee",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Text(
            text = "Captains, coordinators & student moderators",
            fontSize = 12.sp,
            color = TextSecondary
          )
        }

        Surface(
          color = MaroonDark,
          shape = RoundedCornerShape(12.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = null,
              tint = AccentGold,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Official Fleet Team",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = AccentGold
            )
          }
        }
      }
    }

    // Emergency Hotlines Grid Card
    item {
      Card(
        colors = CardDefaults.cardColors(containerColor = MaroonContainer.copy(alpha = 0.4f)),
        shape = RoundedCornerShape(18.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaroonGlow.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Security,
              contentDescription = null,
              tint = StatusRed,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Campus Emergency Hotlines",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = BusWhite
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            EmergencyPill(
              title = "DU Transport Desk",
              number = "+880 2-9661900",
              onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+88029661900"))
                try { context.startActivity(intent) } catch (e: Exception) {}
              },
              modifier = Modifier.weight(1f)
            )
            EmergencyPill(
              title = "DU Proctor Office",
              number = "+880 1711-554433",
              onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+8801711554433"))
                try { context.startActivity(intent) } catch (e: Exception) {}
              },
              modifier = Modifier.weight(1f)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            EmergencyPill(
              title = "Nilkhet Police Box",
              number = "999",
              onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:999"))
                try { context.startActivity(intent) } catch (e: Exception) {}
              },
              modifier = Modifier.weight(1f)
            )
            EmergencyPill(
              title = "DU Medical Center",
              number = "+880 2-9661920",
              onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+88029661920"))
                try { context.startActivity(intent) } catch (e: Exception) {}
              },
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }

    // Section Title
    item {
      Text(
        text = "SADA SHUNDORI Fleet Crew & Committee",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
    }

    // Crew List Cards
    items(crewMembers, key = { it.id }) { member ->
      CrewMemberCard(
        member = member,
        onCall = {
          val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${member.phone}"))
          try { context.startActivity(intent) } catch (e: Exception) {}
        }
      )
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@Composable
private fun EmergencyPill(
  title: String,
  number: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    color = SurfaceDark,
    shape = RoundedCornerShape(10.dp),
    border = androidx.compose.foundation.BorderStroke(0.5.dp, OutlineDark),
    modifier = modifier.clickable { onClick() }
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.Call,
        contentDescription = null,
        tint = MaroonGlow,
        modifier = Modifier.size(14.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Column {
        Text(text = title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text(text = number, fontSize = 9.sp, color = TextMuted)
      }
    }
  }
}

@Composable
private fun CrewMemberCard(
  member: CrewMember,
  onCall: () -> Unit
) {
  Card(
    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
    shape = RoundedCornerShape(18.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("crew_card_${member.id}")
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(CircleShape)
              .background(MaroonDark)
              .border(1.5.dp, MaroonPrimary, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = BusWhite,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = member.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Spacer(modifier = Modifier.width(6.dp))
              Surface(
                color = AccentAmber.copy(alpha = 0.2f),
                shape = RoundedCornerShape(4.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = AccentGold,
                    modifier = Modifier.size(10.dp)
                  )
                  Spacer(modifier = Modifier.width(2.dp))
                  Text(
                    text = "${member.rating}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold
                  )
                }
              }
            }

            Text(
              text = member.role,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = MaroonGlow
            )
            Text(
              text = member.collegeAffiliation,
              fontSize = 10.sp,
              color = TextMuted
            )
          }
        }

        // Call Button
        Surface(
          color = MaroonPrimary,
          shape = CircleShape,
          modifier = Modifier
            .size(40.dp)
            .clickable { onCall() }
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.Default.Call,
              contentDescription = "Call ${member.name}",
              tint = BusWhite,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = member.bio,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Experience: ${member.experienceYears} Years",
          fontSize = 11.sp,
          color = TextMuted
        )
        Text(
          text = member.phone,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          color = AccentGold
        )
      }
    }
  }
}
