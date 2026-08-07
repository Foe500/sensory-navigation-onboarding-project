package com.example.a5120_onboarding_project.data

enum class RouteRisk {
    Low,
    Medium,
    High,
}

data class RouteOption(
    val name: String,
    val minutes: Int,
    val summary: String,
    val risk: RouteRisk,
    val pedestrianRisk: String,
    val constructionRisk: String,
    val eventsRisk: String,
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

enum class MainTab(
    val label: String,
) {
    Search("Search"),
    Predict("Predict"),
    Settings("Me"),
}
