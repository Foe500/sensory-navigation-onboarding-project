package com.example.a5120_onboarding_project.domain

import com.example.a5120_onboarding_project.data.RouteRisk
import com.example.a5120_onboarding_project.data.RouteSegment

class SensoryScoreCalculator {
    fun calculateRisk(routeSegments: List<RouteSegment>): RouteRisk {
        val highestPedestrianCount = routeSegments.maxOfOrNull { it.pedestrianCount } ?: 0
        val hasConstruction = routeSegments.any { it.hasConstruction }
        val hasEvent = routeSegments.any { it.hasEvent }

        val score = pedestrianScore(highestPedestrianCount) +
            if (hasConstruction) 2 else 0 +
            if (hasEvent) 1 else 0

        return when {
            score >= 7 -> RouteRisk.High
            score >= 4 -> RouteRisk.Medium
            else -> RouteRisk.Low
        }
    }

    fun pedestrianRiskLabel(routeSegments: List<RouteSegment>): String {
        val highestPedestrianCount = routeSegments.maxOfOrNull { it.pedestrianCount } ?: 0
        return when {
            highestPedestrianCount >= 800 -> "High"
            highestPedestrianCount >= 300 -> "Medium"
            else -> "Low"
        }
    }

    fun buildExplanation(routeSegments: List<RouteSegment>, risk: RouteRisk): String {
        val busiestSegment = routeSegments.maxByOrNull { it.pedestrianCount }
        val factors = mutableListOf<String>()

        busiestSegment?.let {
            factors.add(
                "${it.name} has ${it.pedestrianCount} pedestrians/hour from ${it.sensorName}.",
            )
        }
        if (routeSegments.any { it.hasConstruction }) {
            factors.add("Construction activity is near one route segment.")
        }
        if (routeSegments.any { it.hasEvent }) {
            factors.add("Possible event activity may increase sensory load.")
        }

        return "${risk.name} sensory rating: ${factors.joinToString(" ")}"
    }

    private fun pedestrianScore(pedestrianCount: Int): Int {
        return when {
            pedestrianCount >= 800 -> 5
            pedestrianCount >= 300 -> 3
            else -> 1
        }
    }
}
