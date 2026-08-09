package com.example.a5120_onboarding_project.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a5120_onboarding_project.R
import com.example.a5120_onboarding_project.data.MainTab
import com.example.a5120_onboarding_project.data.RefugeLocation
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
    val categories = remember { repository.getRefugeCategories() }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var destinationText by remember { mutableStateOf("") }
    var submittedDestination by remember { mutableStateOf<String?>(null) }
    var homeMode by remember { mutableStateOf(HomeMode.RefugeBrowse) }
    var validationMessage by remember { mutableStateOf<String?>(null) }
    var sheetExpanded by remember { mutableStateOf(false) }

    val routes = remember(submittedDestination) {
        submittedDestination?.let { repository.getRouteOptions(it) }.orEmpty()
    }
    val refuges = remember(selectedCategory) {
        repository.getRefugeLocations(selectedCategory ?: "All")
    }
    var selectedRoute by remember { mutableStateOf<RouteOption?>(null) }

    fun submitDestination() {
        val destination = destinationText.trim()
        if (repository.isValidDestination(destination)) {
            submittedDestination = destination
            homeMode = HomeMode.RouteResults
            selectedRoute = null
            sheetExpanded = false
            validationMessage = null
        } else {
            submittedDestination = null
            homeMode = HomeMode.RefugeBrowse
            selectedRoute = null
            sheetExpanded = false
            validationMessage = "Try State Library Victoria, Melbourne Central, QV Melbourne, or Carlton Gardens."
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEAF4EA)),
    ) {
        MapPreview(
            homeMode = homeMode,
            routes = routes,
            selectedRoute = selectedRoute,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 20.dp, vertical = 12.dp),
        ) {
            SearchBar(
                value = destinationText,
                validationMessage = validationMessage,
                onValueChange = {
                    destinationText = it
                    validationMessage = null
                },
                onSubmit = { submitDestination() },
            )
        CategoryChips(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = if (selectedCategory == it) null else it
                    homeMode = HomeMode.RefugeBrowse
                    selectedRoute = null
                    sheetExpanded = false
                },
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .windowInsetsPadding(WindowInsets.navigationBars),
        ) {
            HomeBottomSheet(
                homeMode = homeMode,
                expanded = sheetExpanded,
                routes = routes,
                refuges = refuges,
                selectedRoute = selectedRoute,
                onExpandedChange = { sheetExpanded = it },
                onRouteSelected = {
                    selectedRoute = it
                    homeMode = HomeMode.RouteSelected
                    sheetExpanded = true
                },
            )

            SensoryBottomBar(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        }
    }
}

@Composable
private fun TopStatus() {
}

@Composable
private fun SearchBar(
    value: String,
    validationMessage: String?,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(Color.White)
                .border(1.dp, Color(0xFFD7E8F9), RoundedCornerShape(26.dp))
                .padding(start = 14.dp, end = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("o", color = Color(0xFF3B8FF3), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Search Melbourne CBD destination", color = Color(0xFF8792A0), fontSize = 14.sp)
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            TextButton(onClick = onSubmit) {
                Text("Go", color = Color(0xFF2997FF), fontWeight = FontWeight.Bold)
            }
        }
        validationMessage?.let {
            Text(
                text = it,
                modifier = Modifier
                    .padding(start = 12.dp, top = 6.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.86f))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                color = Color(0xFFB76000),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun CategoryChips(
    categories: List<String>,
    selectedCategory: String?,
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
    homeMode: HomeMode,
    routes: List<RouteOption>,
    selectedRoute: RouteOption?,
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

            when (homeMode) {
                HomeMode.RouteResults -> routes.forEach { route ->
                    drawRouteLine(route = route, selected = false)
                }

                HomeMode.RouteSelected -> selectedRoute?.let { route ->
                    drawRouteLine(route = route, selected = true)
                }

                HomeMode.RefugeBrowse -> Unit
            }
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

private fun DrawScope.drawRouteLine(
    route: RouteOption,
    selected: Boolean,
) {
    val routePath = Path().apply {
        route.routePoints.forEachIndexed { index, point ->
            val offset = Offset(size.width * point.x, size.height * point.y)
            if (index == 0) {
                moveTo(offset.x, offset.y)
            } else {
                lineTo(offset.x, offset.y)
            }
        }
    }

    drawPath(
        path = routePath,
        color = route.risk.routeColor().copy(alpha = if (selected) 1f else 0.78f),
        style = Stroke(
            width = if (selected) 9.dp.toPx() else 6.dp.toPx(),
            cap = StrokeCap.Round,
        ),
    )
}

private fun RouteRisk.routeColor(): Color {
    return when (this) {
        RouteRisk.Low -> Color(0xFF14B85A)
        RouteRisk.Medium -> Color(0xFFFFB02E)
        RouteRisk.High -> Color(0xFFFF6658)
    }
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
private fun HomeBottomSheet(
    homeMode: HomeMode,
    expanded: Boolean,
    routes: List<RouteOption>,
    refuges: List<RefugeLocation>,
    selectedRoute: RouteOption?,
    onExpandedChange: (Boolean) -> Unit,
    onRouteSelected: (RouteOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(if (expanded) 0.62f else 0.25f)
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(Color.White)
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    when {
                        dragAmount < -8f -> onExpandedChange(true)
                        dragAmount > 8f -> onExpandedChange(false)
                    }
                }
            },
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .size(width = 48.dp, height = 4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFFD5DCE6))
                .clickable { onExpandedChange(!expanded) }
                .align(Alignment.CenterHorizontally),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 12.dp),
        ) {
            Text(
                text = when (homeMode) {
                    HomeMode.RefugeBrowse -> "Nearby sensory refuge"
                    HomeMode.RouteResults -> "Route options"
                    HomeMode.RouteSelected -> selectedRoute?.name ?: "Route details"
                },
                color = Color(0xFF26313E),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = when (homeMode) {
                    HomeMode.RefugeBrowse -> "Choose a quiet place or search for a destination."
                    HomeMode.RouteResults -> "Tap a route card to view sensory details."
                    HomeMode.RouteSelected -> "Sensory rating explanation and route factors."
                },
                color = Color(0xFF7B8794),
                fontSize = 11.sp,
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            when (homeMode) {
                HomeMode.RefugeBrowse -> {
                    items(refuges) { refuge ->
                        RefugeCard(refuge)
                    }
                }

                HomeMode.RouteResults -> {
                    items(routes) { route ->
                        RouteCard(route = route, selected = route == selectedRoute, onClick = { onRouteSelected(route) })
                    }
                }

                HomeMode.RouteSelected -> {
                    selectedRoute?.let { route ->
                        item {
                            RouteExplanationCard(route)
                        }
                    }
                    items(routes) { route ->
                        RouteCard(route = route, selected = route == selectedRoute, onClick = { onRouteSelected(route) })
                    }
                }
            }
        }
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
private fun RouteExplanationCard(route: RouteOption) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFFFFBF2)),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Why ${route.risk.name}?",
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF26313E),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
                RiskBadge(route.risk)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = route.explanation,
                color = Color(0xFF657180),
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            route.segments.forEach { segment ->
                Text(
                    text = "${segment.name}: ${segment.pedestrianCount} pedestrians/hour",
                    color = Color(0xFF657180),
                    fontSize = 11.sp,
                )
            }
        }
    }
}

@Composable
private fun RefugeCard(refuge: RefugeLocation) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFF7FAFD)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(refuge.name, color = Color(0xFF27323D), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("${refuge.distance} · ${refuge.openingInfo}", color = Color(0xFF6E7A88), fontSize = 11.sp)
            }
            RiskBadge(refuge.risk)
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
