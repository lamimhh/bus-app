package com.example.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.viewmodel.BusViewModel

@Composable
fun CrewSectionScreen(
  viewModel: BusViewModel,
  modifier: Modifier = Modifier
) {
  CrewScreen(
    viewModel = viewModel,
    onBackClick = { viewModel.navigateBack() },
    modifier = modifier
  )
}
