package com.example.a5120_onboarding_project.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5120_onboarding_project.R
import com.example.a5120_onboarding_project.data.MainTab
import com.example.a5120_onboarding_project.data.RouteOption
import com.example.a5120_onboarding_project.data.RouteRisk
import com.example.a5120_onboarding_project.data.repository.SensoryRepository
import com.example.a5120_onboarding_project.ui.components.SensoryBottomBar
import com.example.a5120_onboarding_project.ui.theme._5120_onboarding_projectTheme

@Composable
fun HomeScreen(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val repository = remember { SensoryRepository() }
    val routes = remember { repository.getRouteOptions("State Library Victoria") }
    val categories = remember { repository.getRefugeCategories() }
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var selectedRoute by remember { mutableStateOf(routes.first()) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEAF4EA)),
    ) {
        MapPreview(
            selectedRoute = selectedRoute,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 20.dp, vertical = 12.dp),
        ) {
            SearchBar()
            CategoryChips(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
            )
        }

        RouteOptionsSheet(
            selectedTab = selectedTab,
            routes = routes,
            selectedRoute = selectedRoute,
            onRouteSelected = { selectedRoute = it },
            onTabSelected = onTabSelected,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun TopStatus() {
}

@Composable
private fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFD7E8F9), RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("o", color = Color(0xFF3B8FF3), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "State Library Victoria",
            modifier = Modifier.weight(1f),
            color = Color(0xFF2F3B48),
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(Color(0xFFEAF0F7)),
            contentAlignment = Alignment.Center,
        ) {
            Text("x", color = Color(0xFF7B8794), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun CategoryChips(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 2.dp),
    ) {
        items(categories) { category ->
            val selected = category == selectedCategory
            Box(
                modifier = Modifier
                    .height(34.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .background(if (selected) Color(0xFF2997FF) else Color.White)
                    .border(
                        width = 1.dp,
                        color = if (selected) Color(0xFF2997FF) else Color(0xFFE4EAF2),
                        shape = RoundedCornerShape(17.dp),
                    )
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = category,
                    color = if (selected) Color.White else Color(0xFF3F4B5B),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
private fun MapPreview(
    selectedRoute: RouteOption,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.map_melbourne_cbd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = Color(0xFFFF6B5D).copy(alpha = 0.20f),
                topLeft = Offset(size.width * 0.34f, size.height * 0.22f),
                size = Size(size.width * 0.28f, size.height * 0.28f),
                cornerRadius = CornerRadius(22.dp.toPx()),
            )

            drawRouteLines(selectedRoute)
            drawCircle(Color(0xFF269BFF), radius = 7.dp.toPx(), center = Offset(size.width * 0.52f, size.height * 0.70f))
            drawCircle(Color.White, radius = 4.dp.toPx(), center = Offset(size.width * 0.52f, size.height * 0.70f))

            drawMarker(size.width * 0.30f, size.height * 0.33f, Color(0xFF2677D9))
            drawMarker(size.width * 0.48f, size.height * 0.28f, Color(0xFFFF7A1A))
            drawMarker(size.width * 0.66f, size.height * 0.44f, Color(0xFFFF7A1A))
            drawMarker(size.width * 0.72f, size.height * 0.52f, Color(0xFF008A74))
        }

        Box(
            modifier = Modifier
                .padding(start = 76.dp, top = 222.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.78f))
                .padding(horizontal = 8.dp, vertical = 5.dp),
        ) {
            Text("High crowd corridor", color = Color(0xFF7D5B4F), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

private fun DrawScope.drawRouteLines(selectedRoute: RouteOption) {
    val greenPath = Path().apply {
        moveTo(size.width * 0.28f, size.height * 0.66f)
        cubicTo(size.width * 0.18f, size.height * 0.42f, size.width * 0.28f, size.height * 0.22f, size.width * 0.58f, size.height * 0.13f)
    }
    drawPath(greenPath, Color(0xFF14B85A), style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round))

    val redPath = Path().apply {
        moveTo(size.width * 0.43f, size.height * 0.66f)
        cubicTo(size.width * 0.42f, size.height * 0.50f, size.width * 0.44f, size.height * 0.34f, size.width * 0.52f, size.height * 0.20f)
    }
    drawPath(
        path = redPath,
        color = if (selectedRoute.risk == RouteRisk.High) Color(0xFFFF6658) else Color(0xFFFF6658).copy(alpha = 0.55f),
        style = Stroke(width = if (selectedRoute.risk == RouteRisk.High) 7.dp.toPx() else 5.dp.toPx(), cap = StrokeCap.Round),
    )
}

private fun DrawScope.drawMarker(
    x: Float,
    y: Float,
    color: Color,
) {
    drawCircle(color = color, radius = 8.dp.toPx(), center = Offset(x, y))
    drawCircle(color = Color.White, radius = 3.dp.toPx(), center = Offset(x, y))
}

@Composable
private fun RouteOptionsSheet(
    selectedTab: MainTab,
    routes: List<RouteOption>,
    selectedRoute: RouteOption,
    onRouteSelected: (RouteOption) -> Unit,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.38f)
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.navigationBars),
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .size(width = 48.dp, height = 4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFFD5DCE6))
                .align(Alignment.CenterHorizontally),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 12.dp),
        ) {
            Text("Route options", color = Color(0xFF26313E), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Tap a route card to view or select that route.", color = Color(0xFF7B8794), fontSize = 11.sp)
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(routes) { route ->
                RouteCard(route = route, selected = route == selectedRoute, onClick = { onRouteSelected(route) })
            }
        }

        SensoryBottomBar(selectedTab = selectedTab, onTabSelected = onTabSelected)
    }
}

@Composable
private fun RouteCard(
    route: RouteOption,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val accent = if (route.risk == RouteRisk.Low) Color(0xFF18B85D) else Color(0xFFFF7A1A)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFF7FAFD)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = if (selected) 3.dp else 1.dp),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(accent),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(route.name, modifier = Modifier.weight(1f), color = Color(0xFF27323D), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    RiskBadge(route.risk)
                }
                Text(
                    text = "${route.minutes} min · ${route.summary}",
                    color = Color(0xFF6E7A88),
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    MiniTag("Pedestrian: ${route.pedestrianRisk}")
                    MiniTag("Construction: ${route.constructionRisk}")
                    MiniTag("Events: ${route.eventsRisk}")
                }
            }
        }
    }
}

@Composable
private fun RiskBadge(risk: RouteRisk) {
    val isLow = risk == RouteRisk.Low
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isLow) Color(0xFFD8F8E2) else Color(0xFFFFE3C4))
            .padding(horizontal = 12.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = risk.name,
            color = if (isLow) Color(0xFF14934B) else Color(0xFFB76000),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun MiniTag(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFFEFF4FA))
            .padding(horizontal = 7.dp, vertical = 4.dp),
    ) {
        Text(label, color = Color(0xFF657180), fontSize = 9.sp, maxLines = 1)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenPreview() {
    _5120_onboarding_projectTheme {
        HomeScreen(
            selectedTab = MainTab.Search,
            onTabSelected = {},
        )
    }
}
