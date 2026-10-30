package com.example.model

enum class AppScreen(val label: String) {
  WELCOME("Welcome"),
  DASHBOARD("Live Location"),
  PICKUP_FINDER("Pickup Finder"),
  BOARDING("Boarding"),
  INSIGHTS("Insights"),
  PROFILE("Profile Hub"),
  NOTICES("Notices & Reports"),
  CREW("Crew & Committee"),
  BRAND_SPLASH("Brand Showcase")
}

enum class BusConnectionState(val label: String) {
  LIVE("LIVE"),
  CONNECTING("CONNECTING"),
  STALE("STALE LOCATION"),
  OFFLINE("OFFLINE")
}

data class UserProfile(
  val name: String = "Lamim",
  val fullName: String = "Hafiz Hossain Lamim",
  val email: String = "hafizhossainlamim@gmail.com",
  val department: String = "Finance & Banking",
  val college: String = "Dhaka College",
  val affiliation: String = "DU Affiliated 7 Colleges",
  val studentId: String = "DC-2022-FB-149",
  val session: String = "2022-23",
  val bloodGroup: String = "B+",
  val emergencyContact: String = "+880 1712-345678",
  val passId: String = "SS-DU7C-9042",
  val passValidity: String = "Valid till Dec 2026",
  val favoriteStopId: String = "stop_5_tongi_college"
)

data class BusStop(
  val id: String,
  val sequenceNumber: Int,
  val name: String,
  val banglaName: String,
  val landmark: String,
  val latitude: Double,
  val longitude: Double,
  val distanceFromStartKm: Double,
  val typicalTransitMinFromPrev: Int,
  val estimatedArrivalMin: Int = 0,
  val morningSchedule: String = "07:15 AM",
  val afternoonSchedule: String = "02:30 PM",
  val isPassed: Boolean = false,
  val isCurrent: Boolean = false,
  val isNext: Boolean = false,
  val isTarget: Boolean = false
)

data class BusStatus(
  val busId: String = "SS-01",
  val busName: String = "SADA SHUNDORI",
  val busBengaliName: String = "সাদা সুন্দরী",
  val regNumber: String = "Dhaka Metro-Ba 15-8824",
  val routeName: String = "National University ➔ Gazipur ➔ Tongi ➔ Campus",
  val currentLatitude: Double = 23.8872,
  val currentLongitude: Double = 90.3985,
  val currentSpeedKmh: Int = 48,
  val speedAccuracyMps: Float = 0.5f,
  val headingDegrees: Float = 175.0f,
  val previousStopName: String = "Gazipura 27",
  val currentStopName: String = "Tongi",
  val nextStopName: String = "Abdullahpur",
  val currentStopIndex: Int = 4, // 0-indexed (Tongi College Gate is 4)
  val nextStopIndex: Int = 5,
  val distanceToNextKm: Double = 3.3,
  val etaToNextMinutes: Int = 6,
  val totalRouteDistanceKm: Double = 32.5,
  val completedDistanceKm: Double = 8.8,
  val journeyPercentage: Int = 27,
  val isOperatorTransmitting: Boolean = false,
  val operatorName: String = "Rahim Mia (Lead Pilot)",
  val operatorPhone: String = "+880 1711-223344",
  val isSimulatingMovement: Boolean = false,
  val connectionState: BusConnectionState = BusConnectionState.LIVE,
  val lastUpdatedSecondsAgo: Int = 1,
  val lastUpdatedFormatted: String = "Just now",
  val totalSeats: Int = 52,
  val onboardCount: Int = 38,
  val isUserOnboard: Boolean = false,
  val isBusFull: Boolean = false,
  val isElevatedRoute: Boolean = false,
  val serviceUpdateText: String = "Bus is running on schedule. Stay tuned for further updates.",
  val serviceUpdateTimestamp: String = "08:15 AM",
  val targetStopId: String = "stop_5_tongi_college"
)

data class DelayInsight(
  val day: String,
  val morningDelayMin: Int,
  val eveningDelayMin: Int,
  val punctualityPercent: Int,
  val trafficNote: String
)

data class BottleneckZone(
  val name: String,
  val location: String,
  val typicalDelayMin: Int,
  val peakHours: String,
  val severity: String // High, Medium, Low
)

data class Notice(
  val id: String,
  val title: String,
  val category: String, // Urgent, Schedule, Route, Maintenance
  val date: String,
  val summary: String,
  val fullBody: String,
  val isUrgent: Boolean = false,
  val author: String = "DU 7 Colleges Transport Committee"
)

data class Complaint(
  val id: String,
  val category: String,
  val busNumber: String,
  val title: String,
  val description: String,
  val contactPhone: String,
  val status: String,
  val timestamp: String,
  val officialResponse: String? = null
)

data class CrewMember(
  val id: String,
  val name: String,
  val role: String,
  val designation: String,
  val phone: String,
  val experienceYears: Int,
  val collegeAffiliation: String,
  val rating: Double,
  val bio: String
)

data class CommunityChatMessage(
  val id: String,
  val senderName: String,
  val senderCollege: String,
  val message: String,
  val timestamp: String,
  val isMe: Boolean = false,
  val category: String = "Traffic" // Traffic, Location, General
)
