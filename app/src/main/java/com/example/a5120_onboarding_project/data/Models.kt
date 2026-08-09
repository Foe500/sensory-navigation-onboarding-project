package com.example.a5120_onboarding_project.data

enum class RouteRisk {
    Low,
    Medium,
    High,
}

data class RouteOption(
    val name: String,
    val destination: String,
    val minutes: Int,
    val summary: String,
    val risk: RouteRisk,
    val pedestrianRisk: String,
    val constructionRisk: String,
    val eventsRisk: String,
    val segments: List<RouteSegment>,
    val routePoints: List<MapPoint>,
    val explanation: String,
)

data class RouteDraft(
    val name: String,
    val destination: String,
    val minutes: Int,
    val summary: String,
    val segments: List<RouteSegment>,
    val routePoints: List<MapPoint>,
)

data class MapPoint(
    val x: Float,
    val y: Float,
)

data class RouteSegment(
    val name: String,
    val sensorName: String,
    val pedestrianCount: Int,
    val hasConstruction: Boolean,
    val hasEvent: Boolean,
)

data class RefugeLocation(
    val name: String,
    val category: String,
    val distance: String,
    val openingInfo: String,
    val risk: RouteRisk,
)

data class PredictedRiskArea(
    val name: String,
    val timeWindow: String,
    val risk: RouteRisk,
    val reason: String,
)

data class UserPreferences(
    val crowdThreshold: Int = 800,
    val avoidConstruction: Boolean = true,
    val showRefuges: Boolean = true,
)

enum class MainTab(
    val label: String,
) {
    Search("Search"),
    Predict("Predict"),
    Settings("Me"),
}
