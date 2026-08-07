package com.example.a5120_onboarding_project.data

val routeOptions = listOf(
    RouteOption(
        name = "Route A",
        minutes = 18,
        summary = "lower pedestrian density, avoids crowded area",
        risk = RouteRisk.Low,
        pedestrianRisk = "Low",
        constructionRisk = "None",
        eventsRisk = "None",
    ),
    RouteOption(
        name = "Route B",
        minutes = 13,
        summary = "passes Swanston St high crowd corridor",
        risk = RouteRisk.High,
        pedestrianRisk = "High",
        constructionRisk = "Nearby",
        eventsRisk = "Possible",
    ),
)

val refugeCategories = listOf("All", "Libraries", "Parks", "Coffee")

val refugeLocations = listOf(
    RefugeLocation(
        name = "State Library Victoria",
        category = "Libraries",
        distance = "5 min walk",
        openingInfo = "Quiet indoor seating",
        risk = RouteRisk.Low,
    ),
    RefugeLocation(
        name = "Carlton Gardens",
        category = "Parks",
        distance = "9 min walk",
        openingInfo = "Open green space",
        risk = RouteRisk.Low,
    ),
    RefugeLocation(
        name = "Quiet Cafe",
        category = "Coffee",
        distance = "7 min walk",
        openingInfo = "Low music before 4 PM",
        risk = RouteRisk.Medium,
    ),
)

val predictedRiskAreas = listOf(
    PredictedRiskArea(
        name = "Swanston St corridor",
        timeWindow = "Next hour",
        risk = RouteRisk.High,
        reason = "Historical pedestrian counts rise sharply near Melbourne Central.",
    ),
    PredictedRiskArea(
        name = "La Trobe St crossing",
        timeWindow = "4:30 PM - 5:30 PM",
        risk = RouteRisk.Medium,
        reason = "Tram interchange and school commute traffic may increase crowd density.",
    ),
)
