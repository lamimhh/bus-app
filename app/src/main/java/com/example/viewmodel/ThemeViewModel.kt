package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
  private val prefs = application.getSharedPreferences("sada_shundori_prefs", Context.MODE_PRIVATE)
  private val _isDarkMode = MutableStateFlow(prefs.getBoolean("key_dark_mode", false))
  val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

  fun toggleDarkMode() {
    setDarkMode(!_isDarkMode.value)
  }

  fun setDarkMode(enabled: Boolean) {
    _isDarkMode.value = enabled
    prefs.edit().putBoolean("key_dark_mode", enabled).apply()
  }
}
