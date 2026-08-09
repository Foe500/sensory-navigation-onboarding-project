package com.example.a5120_onboarding_project.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5120_onboarding_project.data.MainTab
import com.example.a5120_onboarding_project.data.UserPreferences
import com.example.a5120_onboarding_project.ui.components.SensoryBottomBar
import com.example.a5120_onboarding_project.ui.theme._5120_onboarding_projectTheme

@Composable
fun SettingsScreen(
    selectedTab: MainTab,
    userPreferences: UserPreferences,
    onUserPreferencesChange: (UserPreferences) -> Unit,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val crowdTolerance = remember(userPreferences.crowdThreshold) {
        thresholdToSliderValue(userPreferences.crowdThreshold)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F7FB))
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 22.dp, vertical = 22.dp),
        ) {
            Text(
                text = "Me",
                color = Color(0xFF26313E),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Personalise sensory-aware route planning",
                color = Color(0xFF657180),
                fontSize = 13.sp,
            )

            Spacer(modifier = Modifier.height(24.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Crowd tolerance",
                        color = Color(0xFF26313E),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Current threshold: ${userPreferences.crowdThreshold} pedestrians/hour. Routes above this will show an alternative-route warning.",
                        color = Color(0xFF657180),
                        fontSize = 12.sp,
                    )
                    Slider(
                        value = crowdTolerance,
                        onValueChange = {
                            onUserPreferencesChange(
                                userPreferences.copy(crowdThreshold = sliderValueToThreshold(it)),
                            )
                        },
                        steps = 1,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Low", color = Color(0xFF657180), fontSize = 11.sp)
                        Text("Medium", color = Color(0xFF657180), fontSize = 11.sp)
                        Text("High", color = Color(0xFF657180), fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            PreferenceRow(
                title = "Avoid construction zones",
                subtitle = "Increase route risk when construction is nearby.",
                checked = userPreferences.avoidConstruction,
                onCheckedChange = {
                    onUserPreferencesChange(userPreferences.copy(avoidConstruction = it))
                },
            )
            PreferenceRow(
                title = "Show sensory refuges",
                subtitle = "Display libraries, parks and quiet spaces on the map.",
                checked = userPreferences.showRefuges,
                onCheckedChange = {
                    onUserPreferencesChange(userPreferences.copy(showRefuges = it))
                },
            )

            Spacer(modifier = Modifier.height(18.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEAF4FF))
                    .border(1.dp, Color(0xFFD7E8F9), RoundedCornerShape(12.dp))
                    .padding(16.dp),
            ) {
                Text(
                    text = "Current mode: prefers calmer routes even when travel time is slightly longer.",
                    color = Color(0xFF2F4E6F),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        SensoryBottomBar(selectedTab = selectedTab, onTabSelected = onTabSelected)
    }
}

@Composable
private fun PreferenceRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color(0xFF26313E), fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(subtitle, color = Color(0xFF657180), fontSize = 12.sp)
            }
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

private fun thresholdToSliderValue(threshold: Int): Float {
    return when {
        threshold <= 300 -> 0f
        threshold >= 800 -> 1f
        else -> 0.5f
    }
}

private fun sliderValueToThreshold(value: Float): Int {
    return when {
        value < 0.25f -> 300
        value < 0.75f -> 550
        else -> 800
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun SettingsScreenPreview() {
    _5120_onboarding_projectTheme {
        SettingsScreen(
            selectedTab = MainTab.Settings,
            userPreferences = UserPreferences(),
            onUserPreferencesChange = {},
            onTabSelected = {},
        )
    }
}
