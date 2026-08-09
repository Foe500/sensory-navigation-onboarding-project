package com.example.a5120_onboarding_project.ui.screens.predict

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5120_onboarding_project.R
import com.example.a5120_onboarding_project.data.MainTab
import com.example.a5120_onboarding_project.data.PredictedRiskArea
import com.example.a5120_onboarding_project.data.RouteRisk
import com.example.a5120_onboarding_project.data.repository.SensoryRepository
import com.example.a5120_onboarding_project.ui.components.SensoryBottomBar
import com.example.a5120_onboarding_project.ui.theme._5120_onboarding_projectTheme

@Composable
fun PredictScreen(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val repository = remember { SensoryRepository() }
    val riskAreas = remember { repository.getPredictedRiskAreas() }
    var showRiskSheet by remember { mutableStateOf(false) }
    var sheetExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEAF4EA)),
    ) {
        PredictionMap(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 22.dp, vertical = 20.dp),
        ) {
            Text(
                text = "Predict",
                color = Color(0xFF26313E),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Future sensory risk in the next hour",
                color = Color(0xFF657180),
                fontSize = 13.sp,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .windowInsetsPadding(WindowInsets.navigationBars),
        ) {
            if (showRiskSheet) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (sheetExpanded) 420.dp else 230.dp)
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(Color.White)
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { _, dragAmount ->
                                when {
                                    dragAmount < -8f -> sheetExpanded = true
                                    dragAmount > 8f -> sheetExpanded = false
                                }
                            }
                        },
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .height(4.dp)
                            .fillMaxWidth(0.14f)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color(0xFFD5DCE6))
                            .clickable { sheetExpanded = !sheetExpanded }
                            .align(Alignment.CenterHorizontally),
                    )
                    Text(
                        text = "Predicted risk areas",
                        modifier = Modifier.padding(start = 22.dp, top = 14.dp, bottom = 8.dp),
                        color = Color(0xFF26313E),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(riskAreas) { area ->
                            RiskAreaCard(area)
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                        .border(1.dp, Color(0xFFD7E8F9), RoundedCornerShape(18.dp))
                        .clickable {
                            showRiskSheet = true
                            sheetExpanded = false
                        }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                ) {
                    Text(
                        text = "View risk areas",
                        color = Color(0xFF2997FF),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            SensoryBottomBar(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        }
    }
}

@Composable
private fun PredictionMap(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.map_melbourne_cbd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = Color(0xFFFF6B5D).copy(alpha = 0.28f),
                topLeft = Offset(size.width * 0.32f, size.height * 0.20f),
                size = Size(size.width * 0.36f, size.height * 0.22f),
                cornerRadius = CornerRadius(26.dp.toPx()),
            )
            drawRoundRect(
                color = Color(0xFFFFB84D).copy(alpha = 0.26f),
                topLeft = Offset(size.width * 0.55f, size.height * 0.40f),
                size = Size(size.width * 0.30f, size.height * 0.18f),
                cornerRadius = CornerRadius(22.dp.toPx()),
            )
            drawCircle(
                color = Color(0xFFFF6658),
                radius = 9.dp.toPx(),
                center = Offset(size.width * 0.52f, size.height * 0.30f),
            )
            drawCircle(
                color = Color(0xFFFFB02E),
                radius = 9.dp.toPx(),
                center = Offset(size.width * 0.70f, size.height * 0.49f),
            )
        }
    }
}

@Composable
private fun RiskAreaCard(area: PredictedRiskArea) {
    val accent = when (area.risk) {
        RouteRisk.Low -> Color(0xFF18B85D)
        RouteRisk.Medium -> Color(0xFFFFB02E)
        RouteRisk.High -> Color(0xFFFF6658)
    }
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFF7FAFD)),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = area.name,
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF27323D),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = area.risk.name,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(accent.copy(alpha = 0.16f))
                        .border(1.dp, accent.copy(alpha = 0.20f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    color = accent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(area.timeWindow, color = Color(0xFF657180), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            Text(area.reason, color = Color(0xFF657180), fontSize = 11.sp)
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PredictScreenPreview() {
    _5120_onboarding_projectTheme {
        PredictScreen(selectedTab = MainTab.Predict, onTabSelected = {})
    }
}
