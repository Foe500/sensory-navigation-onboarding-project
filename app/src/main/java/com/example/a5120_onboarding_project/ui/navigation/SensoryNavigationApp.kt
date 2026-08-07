package com.example.a5120_onboarding_project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.a5120_onboarding_project.data.MainTab
import com.example.a5120_onboarding_project.ui.screens.home.HomeScreen
import com.example.a5120_onboarding_project.ui.screens.predict.PredictScreen
import com.example.a5120_onboarding_project.ui.screens.settings.SettingsScreen

@Composable
fun SensoryNavigationApp() {
    var selectedTab by remember { mutableStateOf(MainTab.Search) }

    when (selectedTab) {
        MainTab.Search -> HomeScreen(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
        )

        MainTab.Predict -> PredictScreen(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
        )

        MainTab.Settings -> SettingsScreen(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
        )
    }
}
