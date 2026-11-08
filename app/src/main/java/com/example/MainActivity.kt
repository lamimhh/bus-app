package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.navigation.AppNavigation
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.BusViewModel
import com.example.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
  private val busViewModel: BusViewModel by viewModels()
  private val themeViewModel: ThemeViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val isDarkMode by themeViewModel.isDarkMode.collectAsState()
      MyApplicationTheme(darkTheme = isDarkMode) {
        AppNavigation(
          viewModel = busViewModel,
          themeViewModel = themeViewModel,
          modifier = Modifier.fillMaxSize()
        )
      }
    }
  }
}
