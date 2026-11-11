package com.example.ui.util

import com.example.model.AppLanguage
import com.example.model.BusRuleItem

object AppStrings {

  // Brand and App Titles
  fun appName(lang: AppLanguage): String = if (lang.isBangla) "সাদা সুন্দরী" else "SADA SHUNDORI"
  fun appSubtitle(lang: AppLanguage): String = if (lang.isBangla) "লাইভ বাস কম্প্যানিয়ন" else "LIVE BUS COMPANION"
  fun duAffiliation(lang: AppLanguage): String = if (lang.isBangla) "ঢাবি অধিভুক্ত ৭ কলেজ পরিবহন" else "DU Affiliated 7 Colleges Transit"

  // Bottom Navigation
  fun navLiveLocation(lang: AppLanguage): String = if (lang.isBangla) "লাইভ লোকেশন" else "Live Location"
  fun navBoarding(lang: AppLanguage): String = if (lang.isBangla) "বোর্ডিং" else "Boarding"
  fun navInsights(lang: AppLanguage): String = if (lang.isBangla) "ইনসাইটস" else "Insights"

  // Profile Hub
  fun profileHub(lang: AppLanguage): String = if (lang.isBangla) "প্রোফাইল হাব" else "Profile Hub"
  fun backToHome(lang: AppLanguage): String = if (lang.isBangla) "হোম পেজে ফিরুন" else "BACK TO HOME PAGE"
  fun tapToChangePhoto(lang: AppLanguage): String = if (lang.isBangla) "ছবি পরিবর্তন করতে ট্যাপ করুন" else "Tap to change photo"
  fun departmentLabel(lang: AppLanguage): String = if (lang.isBangla) "বিভাগ:" else "Dept:"
  fun sessionLabel(lang: AppLanguage): String = if (lang.isBangla) "সেশন:" else "Session:"
  fun studentIdLabel(lang: AppLanguage): String = if (lang.isBangla) "শিক্ষার্থী আইডি:" else "Student ID:"
  fun collegeLabel(lang: AppLanguage): String = if (lang.isBangla) "কলেজ:" else "College:"
  fun accountIdLabel(lang: AppLanguage): String = if (lang.isBangla) "অ্যাকাউন্ট আইডি:" else "Account ID:"

  // Sections
  fun busServicesTitle(lang: AppLanguage): String = if (lang.isBangla) "বাস সার্ভিসেস" else "BUS SERVICES"
  fun communityTitle(lang: AppLanguage): String = if (lang.isBangla) "কমিউনিটি ও তথ্য" else "COMMUNITY & INFO"
  fun preferencesTitle(lang: AppLanguage): String = if (lang.isBangla) "পছন্দ ও সেটিংস" else "PREFERENCES & SETTINGS"
  fun busRulesTitle(lang: AppLanguage): String = if (lang.isBangla) "বাসের নিয়মাবলী" else "BUS RULES"
  fun busRulesSubtitle(lang: AppLanguage): String = if (lang.isBangla) "সুশৃঙ্খল ও নিরাপদ যাতায়াতের আবশ্যকীয় নীতিমালা" else "Essential University Transit Guidelines"

  // Tiles
  fun noticeBoardTitle(lang: AppLanguage): String = if (lang.isBangla) "নোটিশ বোর্ড" else "Notice Board"
  fun noticeBoardSub(lang: AppLanguage): String = if (lang.isBangla) "জরুরি আপডেট ও সময়সূচি" else "Official transit bulletins & schedule"
  fun controlPanelTitle(lang: AppLanguage): String = if (lang.isBangla) "কন্ট্রোল প্যানেল ও মডারেটর" else "Control Panel & Moderator"
  fun controlPanelSub(lang: AppLanguage): String = if (lang.isBangla) "মনিটরিং ও জরুরি সেল" else "Direct bus command & monitor"
  fun complaintBoxTitle(lang: AppLanguage): String = if (lang.isBangla) "অভিযোগ বক্স" else "Complaint Box"
  fun complaintBoxSub(lang: AppLanguage): String = if (lang.isBangla) "অনুপস্থিতি ও ফিডব্যাক দাখিল" else "Report issues or bus crew feedback"
  fun crewTitle(lang: AppLanguage): String = if (lang.isBangla) "ক্রু ও কমিটি" else "Crew & Committee"
  fun crewSub(lang: AppLanguage): String = if (lang.isBangla) "চালক, সহকারী ও ছাত্র প্রতিনিধি" else "Drivers, helpers & student leaders"

  fun officialCommunityTitle(lang: AppLanguage): String = if (lang.isBangla) "অফিসিয়াল কমিউনিটি" else "Official Community"
  fun officialCommunitySub(lang: AppLanguage): String = if (lang.isBangla) "মেসেঞ্জার, ফেসবুক ও লাইভ চ্যাট" else "Facebook group & student network"
  fun aboutTitle(lang: AppLanguage): String = if (lang.isBangla) "সাদা সুন্দরী সম্পর্কে" else "About Sada Shundori"
  fun aboutSub(lang: AppLanguage): String = if (lang.isBangla) "ইতিহাস, রুট ও উদ্দেশ্য" else "Fleet background & student initiative"

  fun settingsTileTitle(lang: AppLanguage): String = if (lang.isBangla) "সেটিংস ও নিরাপত্তা" else "Settings & Privacy"
  fun settingsTileSub(lang: AppLanguage): String = if (lang.isBangla) "সহায়তা, অনুসন্ধান ও ডায়াগনস্টিক রিপোর্ট" else "Help, FAQ search & diagnostic reports"
  fun languageTileTitle(lang: AppLanguage): String = if (lang.isBangla) "ভাষা পরিবর্তন (Language)" else "App Language"
  fun languageTileSub(lang: AppLanguage): String = if (lang.isBangla) "বর্তমান: বাংলা (বাংলা / English)" else "Current: English (বাংলা / English)"
  fun logoutTitle(lang: AppLanguage): String = if (lang.isBangla) "লগ আউট" else "Log Out"
  fun logoutSub(lang: AppLanguage): String = if (lang.isBangla) "অন্য অ্যাকাউন্টে প্রবেশ করুন" else "Sign out of your active account"

  // Dark Mode
  fun darkModeTitle(lang: AppLanguage): String = if (lang.isBangla) "ডার্ক মোড" else "Dark Mode"
  fun lightModeTitle(lang: AppLanguage): String = if (lang.isBangla) "লাইট মোড" else "Light Mode"

  // Dashboard & Finder Common
  fun findMyPickupPoint(lang: AppLanguage): String = if (lang.isBangla) "আমার পিকআপ পয়েন্ট খুঁজুন" else "FIND MY PICKUP POINT"
  fun allowLocationHint(lang: AppLanguage): String = if (lang.isBangla) "নিকটস্থ পিকআপ পয়েন্ট দেখতে লোকেশন অন করুন।" else "Allow location access to find the nearest pickup point."
  fun recommendedPickupPoint(lang: AppLanguage): String = if (lang.isBangla) "প্রস্তাবিত পিকআপ পয়েন্ট" else "Recommended Pickup Point"
  fun getDirections(lang: AppLanguage): String = if (lang.isBangla) "দিকনির্দেশনা" else "Get Directions"
  fun busArrivalEstimate(lang: AppLanguage): String = if (lang.isBangla) "বাস পৌঁছানোর আনুমানিক সময়" else "BUS ARRIVAL ESTIMATE"
  fun toReachThisStop(lang: AppLanguage): String = if (lang.isBangla) "এই স্টপে পৌঁছাতে সময় লাগবে" else "to reach this stop"
  fun expectedArrival(lang: AppLanguage): String = if (lang.isBangla) "সম্ভাব্য আগমন" else "Expected arrival"
  fun currentPosition(lang: AppLanguage): String = if (lang.isBangla) "বর্তমান অবস্থান" else "Current position"
  fun lastUpdate(lang: AppLanguage): String = if (lang.isBangla) "সর্বশেষ আপডেট" else "Last update"
  fun explore360View(lang: AppLanguage): String = if (lang.isBangla) "৩৬০° ভিউ দেখুন" else "Explore 360° View"
  fun explore360Desc(lang: AppLanguage): String = if (lang.isBangla) "পিকআপ পয়েন্ট এবং আশেপাশের ল্যান্ডমার্কের স্পষ্ট দৃশ্য দেখুন।" else "Get a real look at the pickup point and nearby landmarks."
  fun viewPanorama(lang: AppLanguage): String = if (lang.isBangla) "প্যানোরামা ভিউ" else "View Panorama"
  fun otherStopsOnRoute(lang: AppLanguage): String = if (lang.isBangla) "এই রুটের অন্যান্য স্টপসমূহ" else "Other Stops on This Route"
  fun viewAll(lang: AppLanguage): String = if (lang.isBangla) "সব দেখুন" else "View all"
  fun imOnTheBus(lang: AppLanguage): String = if (lang.isBangla) "আমি বাসে আছি" else "I'M ON THE BUS"
  fun iveAlighted(lang: AppLanguage): String = if (lang.isBangla) "আমি বাস থেকে নেমেছি" else "I'VE ALIGHTED"

  // 8 Official University Bus Rules
  val officialBusRules: List<BusRuleItem> = listOf(
    BusRuleItem(
      id = 1,
      titleBn = "১. সময়ানুবর্তিতা ও স্টপে উপস্থিতি",
      titleEn = "1. Punctuality & Stop Arrival",
      descBn = "বাসের নির্ধারিত সময়সূচির অন্তত ৫ থেকে ১০ মিনিট পূর্বে নিজ নিজ স্টপে উপস্থিত থাকতে হবে। মহাসড়কে শৃঙ্খলা বজায় রাখতে এবং সময় বাঁচাতে নির্ধারিত স্টপ ব্যতীত কোনো অনির্ধারিত বা ঝুঁকিপূর্ণ স্থানে বাস থামানোর অনুরোধ সম্পূর্ণ নিষিদ্ধ।",
      descEn = "Arrive at your scheduled bus stop at least 5-10 minutes prior to departure. To ensure commuter safety and punctual schedules, requesting stops at unauthorized or unsafe points along the highway is strictly prohibited.",
      categoryBn = "সময়সূচি",
      categoryEn = "Schedule"
    ),
    BusRuleItem(
      id = 2,
      titleBn = "২. বাসে আরোহণে শৃঙ্খলা ও লাইনের নিয়ম",
      titleEn = "2. Queuing & Boarding Discipline",
      descBn = "বাস স্টপে পৌঁছালে হুড়োহুড়ি বা ধাক্কাধাক্কি না করে সুশৃঙ্খল লাইনে দাঁড়িয়ে বাসে আরোহণ করুন। অসুস্থ শিক্ষার্থী, নারী সহপাঠী এবং জ্যেষ্ঠদের প্রথমে বাসে ওঠার অগ্রাধিকার ও সুযোগ দিন।",
      descEn = "Form orderly queues at bus stops without pushing or rushing. Always prioritize unwell classmates, female students, and seniors during the boarding process.",
      categoryBn = "শৃঙ্খলা",
      categoryEn = "Discipline"
    ),
    BusRuleItem(
      id = 3,
      titleBn = "৩. আসন বণ্টন ও জ্যেষ্ঠদের প্রতি শ্রদ্ধা",
      titleEn = "3. Seat Courtesy & Seat Management",
      descBn = "বাসের আসন সকলের জন্য সমভাবে উন্মুক্ত। ব্যাগ বা ব্যক্তিগত জিনিসপত্র রেখে অতিরিক্ত আসন দখল করে রাখা শাস্তিযোগ্য আচরণ। শিক্ষক, জ্যেষ্ঠ ভাই-বোন ও অসুস্থ শিক্ষার্থীদের বসার জন্য স্বেচ্ছায় আসন ছেড়ে দিন।",
      descEn = "Seats are for passenger use; placing bags or personal items on adjacent seats to reserve them is prohibited. Offer seats respectfully to faculty members, seniors, and students in medical distress.",
      categoryBn = "আসন বিন্যাস",
      categoryEn = "Courtesy"
    ),
    BusRuleItem(
      id = 4,
      titleBn = "৪. চালক ও সহকারী মামাদের প্রতি আচরণ",
      titleEn = "4. Respect for Drivers & Crew",
      descBn = "আমাদের চালক ও সহকারী (হেলপার) মামারা শিক্ষার্থীদের নিরাপদ যাতায়াতের অক্লান্ত অভিভাবক। তাঁদের সাথে সর্বদা মার্জিত, শ্রদ্ধাশীল ও সহযোগী মনোভাব বজায় রাখুন। কোনো অভিযোগ বা অসন্তোষ থাকলে সরাসরি তর্ক না করে কমপ্লেন বক্সে বা কমিটিতে জানান।",
      descEn = "Treat drivers and helpers with utmost dignity and polite conduct. If any scheduling or service grievances arise, report them via the in-app Complaint Box or Transport Committee rather than engaging in arguments.",
      categoryBn = "শ্রদ্ধাবোধ",
      categoryEn = "Respect"
    ),
    BusRuleItem(
      id = 5,
      titleBn = "৫. বাসের অভ্যন্তরীণ পরিচ্ছন্নতা রক্ষা",
      titleEn = "5. Cleanliness & Hygiene",
      descBn = "সাদা সুন্দরী আমাদের ক্যাম্পাসের ভ্রাম্যমাণ প্রতিচ্ছবি। বাসের ভেতরে বাদামের খোসা, পানির বোতল, চিপসের প্যাকেট বা যেকোনো ধরনের বর্জ্য ফেলা সম্পূর্ণ নিষিদ্ধ। ময়লা নির্দিষ্ট বিনে ফেলুন অথবা নিজের ব্যাগে সংরক্ষণ করুন।",
      descEn = "Preserve cleanliness inside the bus. Discarding food wrappers, empty bottles, fruit peels, or trash on the floor is strictly forbidden. Use designated dustbins or keep waste in your bag.",
      categoryBn = "পরিচ্ছন্নতা",
      categoryEn = "Cleanliness"
    ),
    BusRuleItem(
      id = 6,
      titleBn = "৬. নিরাপত্তা ও বিপজ্জনক আচরণ পরিহার",
      titleEn = "6. Commuter Safety & Hazard Prevention",
      descBn = "বাসের দরজায় ঝুলে যাতায়াত করা, চলন্ত অবস্থায় জানালা দিয়ে হাত বা মাথা বের করা এবং চলন্ত বাস থেকে লাফ দিয়ে নামা বা ওঠার চেষ্টা সম্পূর্ণ নিষিদ্ধ। ফুটবোর্ড সবসময় মুক্ত রাখুন।",
      descEn = "Hanging from doors, leaning heads/arms out of windows, and attempting to alight or board while the vehicle is in motion are strictly forbidden. Keep footboards clear at all times.",
      categoryBn = "নিরাপত্তা",
      categoryEn = "Safety"
    ),
    BusRuleItem(
      id = 7,
      titleBn = "৭. বৈধ স্টুডেন্ট আইডি ও ডিজিটাল পাস সাথে রাখা",
      titleEn = "7. Student ID & Digital Pass Verification",
      descBn = "সাদা সুন্দরী বাসে ভ্রমণের অধিকার শুধুমাত্র ঢাকা বিশ্ববিদ্যালয়ের অধিভুক্ত ৭ কলেজের নিবন্ধিত শিক্ষার্থীদের। বাসে ভ্রমণের সময় কলেজের মূল আইডি কার্ড অথবা সাদা সুন্দরী অ্যাপের ভেরিফাইড প্রোফাইল সাথে রাখতে হবে।",
      descEn = "Transit service SS-01 is dedicated exclusively to registered students of the DU Affiliated 7 Colleges. Carry your valid college identity card or active digital app profile during commutes.",
      categoryBn = "আইডি কার্ড",
      categoryEn = "Verification"
    ),
    BusRuleItem(
      id = 8,
      titleBn = "৮. শালীনতা, শৃঙ্খলা ও ক্যাম্পাস ভ্রাতৃত্ব",
      titleEn = "8. Campus Decorum & Mutual Respect",
      descBn = "বাসের ভেতরে কোনো প্রকার অসৌজন্যমূলক আচরণ, র্যাগিং, উত্ত্যক্তকরণ, উচ্চৈঃস্বরে বিশৃঙ্খলা বা ধূমপান কঠোরভাবে নিষিদ্ধ। পরস্পরের প্রতি ভ্রাতৃত্বপূর্ণ ও সহযোগিতাপূর্ণ আচরণ প্রদর্শন করে ৭ কলেজের মর্যাদা সমুন্নত রাখুন।",
      descEn = "Harassment, ragging, loud disruption, and smoking inside the vehicle are strictly prohibited. Uphold the institutional dignity of the 7 Colleges with mature, respectful, and fraternal conduct.",
      categoryBn = "নৈতিকতা",
      categoryEn = "Decorum"
    )
  )
}
