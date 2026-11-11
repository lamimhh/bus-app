package com.example

import com.example.data.BusRepository
import com.example.model.AppLanguage
import com.example.ui.util.AppStrings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UserProfileAndRulesTest {

  @Test
  fun `verify official university bus rules contains 8 rules in Bangla`() {
    val rules = AppStrings.officialBusRules
    assertEquals(8, rules.size)

    val punctualityRule = rules.first { it.id == 1 }
    assertTrue(punctualityRule.titleBn.contains("সময়ানুবর্তিতা"))
    assertTrue(punctualityRule.descBn.contains("৫ থেকে ১০ মিনিট"))

    val safetyRule = rules.first { it.id == 6 }
    assertTrue(safetyRule.titleBn.contains("নিরাপত্তা"))
    assertTrue(safetyRule.descBn.contains("ঝুলে যাতায়াত করা"))
  }

  @Test
  fun `verify user profile avatar and registration dynamic updates`() {
    val repo = BusRepository()
    repo.registerUser(
      fullName = "Tariqul Islam",
      department = "Accounting",
      session = "2023-24",
      email = "tariqul.islam@gmail.com",
      college = "Dhaka College"
    )

    val profile = repo.userProfile.value
    assertEquals("Tariqul Islam", profile.fullName)
    assertEquals("Tariqul", profile.name)
    assertEquals("Accounting", profile.department)
    assertEquals("2023-24", profile.session)
    assertEquals("tariqul.islam@gmail.com", profile.email)
    assertEquals("Dhaka College", profile.college)
    assertTrue(profile.studentId.isNotBlank())
    assertTrue(profile.passId.startsWith("SS-DU7C-"))

    // Update avatar
    repo.updateUserAvatar("content://media/picker/photo_99.jpg")
    assertEquals("content://media/picker/photo_99.jpg", repo.userProfile.value.avatarUri)
  }

  @Test
  fun `verify bilingual app strings switch instantly between Bangla and English`() {
    val bnHome = AppStrings.backToHome(AppLanguage.BANGLA)
    val enHome = AppStrings.backToHome(AppLanguage.ENGLISH)
    assertEquals("হোম পেজে ফিরুন", bnHome)
    assertEquals("BACK TO HOME PAGE", enHome)

    val bnRules = AppStrings.busRulesTitle(AppLanguage.BANGLA)
    val enRules = AppStrings.busRulesTitle(AppLanguage.ENGLISH)
    assertEquals("বাসের নিয়মাবলী", bnRules)
    assertEquals("BUS RULES", enRules)
  }
}
