package com.example

import com.example.data.BusRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testBusRepositoryInitialState() {
    val repository = BusRepository()
    val status = repository.busStatus.value
    assertEquals("SS-01", status.busId)
    assertEquals(52, status.totalSeats)
    assertFalse(status.isUserOnboard)
  }

  @Test
  fun testToggleOnboardStatus() {
    val repository = BusRepository()
    val initialCount = repository.busStatus.value.onboardCount
    repository.toggleOnboardStatus()
    assertTrue(repository.busStatus.value.isUserOnboard)
    assertEquals(initialCount + 1, repository.busStatus.value.onboardCount)

    repository.toggleOnboardStatus()
    assertFalse(repository.busStatus.value.isUserOnboard)
    assertEquals(initialCount, repository.busStatus.value.onboardCount)
  }

  @Test
  fun testSelectTargetStop() {
    val repository = BusRepository()
    repository.selectTargetStop("stop_13_airport")
    val currentTarget = repository.stops.value.find { it.id == "stop_13_airport" }
    assertNotNull(currentTarget)
    assertTrue(currentTarget!!.isTarget)
    assertEquals("stop_13_airport", repository.busStatus.value.targetStopId)
  }

  @Test
  fun testOperatorAuthentication() {
    val repository = BusRepository()
    assertTrue(repository.verifyOperatorPassword("SADA2026"))
    assertFalse(repository.verifyOperatorPassword("WRONGKEY"))
  }

  @Test
  fun testSubmitComplaint() {
    val repository = BusRepository()
    val initialSize = repository.complaints.value.size
    val complaint = repository.submitComplaint(
      category = "Lost Item",
      title = "Lost DU ID Card",
      description = "Lost on front row seat",
      contactPhone = "+8801700000000"
    )
    assertEquals(initialSize + 1, repository.complaints.value.size)
    assertEquals("Submitted", complaint.status)
    assertEquals("Lost DU ID Card", repository.complaints.value.first().title)
  }

  @Test
  fun testRegisterUser() {
    val repository = BusRepository()
    repository.registerUser(
      fullName = "Tanvir Rahman",
      department = "Accounting",
      session = "2022-2023",
      email = "tanvir@gmail.com"
    )
    val user = repository.userProfile.value
    assertEquals("Tanvir Rahman", user.fullName)
    assertEquals("Tanvir", user.name)
    assertEquals("Accounting", user.department)
    assertEquals("2022-2023", user.session)
    assertEquals("tanvir@gmail.com", user.email)
  }

  @Test
  fun testLoginUser() {
    val repository = BusRepository()
    repository.loginUser("student.du7c@gmail.com")
    assertEquals("student.du7c@gmail.com", repository.userProfile.value.email)
  }

  @Test
  fun testComplaintWithDetails() {
    val repository = BusRepository()
    val complaint = repository.submitComplaint(
      category = "Bus Timing & Punctuality",
      title = "Delayed departure from Tongi",
      description = "Bus was delayed by 25 minutes at morning rush hour.",
      contactPhone = "+8801712345678",
      busNumber = "SS-01 (Uttara - Gazipur)",
      department = "Economics",
      submitterName = "Rashidul Islam",
      allowContact = true
    )
    assertEquals("Rashidul Islam", complaint.submitterName)
    assertEquals("Economics", complaint.department)
    assertTrue(complaint.allowContact)
    assertEquals("Submitted", complaint.status)
  }
}
