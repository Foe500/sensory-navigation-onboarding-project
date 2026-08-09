package com.example.a5120_onboarding_project.data

val supportedDestinations = listOf(
    "State Library Victoria",
    "Melbourne Central",
    "QV Melbourne",
    "Carlton Gardens",
)

val routeDrafts = listOf(
    RouteDraft(
        name = "Route A",
        destination = "State Library Victoria",
        minutes = 18,
        summary = "lower pedestrian density, avoids crowded area",
        segments = listOf(
            RouteSegment(
                name = "Riverside quiet segment",
                sensorName = "Yarra north pedestrian sensor",
                pedestrianCount = 180,
                hasConstruction = false,
                hasEvent = false,
            ),
            RouteSegment(
                name = "Library approach",
                sensorName = "La Trobe St sensor",
                pedestrianCount = 260,
                hasConstruction = false,
                hasEvent = false,
            ),
        ),
        routePoints = listOf(
            MapPoint(0.24f, 0.88f),
            MapPoint(0.23f, 0.70f),
            MapPoint(0.28f, 0.53f),
            MapPoint(0.36f, 0.38f),
            MapPoint(0.50f, 0.22f),
            MapPoint(0.60f, 0.08f),
        ),
    ),
    RouteDraft(
        name = "Route B",
        destination = "State Library Victoria",
        minutes = 13,
        summary = "passes Swanston St high crowd corridor",
        segments = listOf(
            RouteSegment(
                name = "Swanston St corridor",
                sensorName = "Melbourne Central pedestrian sensor",
                pedestrianCount = 920,
                hasConstruction = true,
                hasEvent = true,
            ),
            RouteSegment(
                name = "Library crossing",
                sensorName = "State Library pedestrian sensor",
                pedestrianCount = 610,
                hasConstruction = false,
                hasEvent = false,
            ),
        ),
        routePoints = listOf(
            MapPoint(0.50f, 0.88f),
            MapPoint(0.50f, 0.70f),
            MapPoint(0.52f, 0.52f),
            MapPoint(0.55f, 0.35f),
            MapPoint(0.60f, 0.18f),
            MapPoint(0.66f, 0.07f),
        ),
    ),
    RouteDraft(
        name = "Route C",
        destination = "State Library Victoria",
        minutes = 16,
        summary = "balanced option with moderate pedestrian density",
        segments = listOf(
            RouteSegment(
                name = "La Trobe St crossing",
                sensorName = "La Trobe St pedestrian sensor",
                pedestrianCount = 480,
                hasConstruction = false,
                hasEvent = false,
            ),
            RouteSegment(
                name = "Library approach",
                sensorName = "State Library pedestrian sensor",
                pedestrianCount = 530,
                hasConstruction = false,
                hasEvent = true,
            ),
        ),
        routePoints = listOf(
            MapPoint(0.34f, 0.88f),
            MapPoint(0.38f, 0.72f),
            MapPoint(0.46f, 0.55f),
            MapPoint(0.56f, 0.39f),
            MapPoint(0.68f, 0.23f),
            MapPoint(0.78f, 0.10f),
        ),
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
