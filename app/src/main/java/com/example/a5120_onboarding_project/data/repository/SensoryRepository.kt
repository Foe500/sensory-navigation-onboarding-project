package com.example.a5120_onboarding_project.data.repository

import com.example.a5120_onboarding_project.data.PredictedRiskArea
import com.example.a5120_onboarding_project.data.RefugeLocation
import com.example.a5120_onboarding_project.data.RouteDraft
import com.example.a5120_onboarding_project.data.RouteOption
import com.example.a5120_onboarding_project.data.predictedRiskAreas
import com.example.a5120_onboarding_project.data.refugeCategories
import com.example.a5120_onboarding_project.data.refugeLocations
import com.example.a5120_onboarding_project.data.routeDrafts
import com.example.a5120_onboarding_project.data.supportedDestinations
import com.example.a5120_onboarding_project.domain.SensoryScoreCalculator

class SensoryRepository(
    private val sensoryScoreCalculator: SensoryScoreCalculator = SensoryScoreCalculator(),
) {
    fun isValidDestination(destination: String): Boolean {
        return recogniseDestination(destination) != null
    }

    fun getRouteOptions(destination: String): List<RouteOption> {
        val recognisedDestination = recogniseDestination(destination) ?: return emptyList()
        val matchingDrafts = routeDrafts.filter {
            it.destination.equals(recognisedDestination, ignoreCase = true)
        }.ifEmpty {
            routeDrafts.filter { it.destination == "State Library Victoria" }
        }

        return matchingDrafts.map { it.toRouteOption(recognisedDestination) }
    }

    fun getRefugeCategories(): List<String> = refugeCategories

    fun getRefugeLocations(category: String): List<RefugeLocation> {
        if (category == "All") return refugeLocations
        return refugeLocations.filter { it.category == category }
    }

    fun getPredictedRiskAreas(): List<PredictedRiskArea> = predictedRiskAreas

    private fun recogniseDestination(destination: String): String? {
        val normalisedDestination = destination.trim()
        return supportedDestinations.firstOrNull {
            it.equals(normalisedDestination, ignoreCase = true) ||
                it.contains(normalisedDestination, ignoreCase = true) ||
                normalisedDestination.contains(it, ignoreCase = true)
        }
    }

    private fun RouteDraft.toRouteOption(recognisedDestination: String): RouteOption {
        val risk = sensoryScoreCalculator.calculateRisk(segments)
        return RouteOption(
            name = name,
            destination = recognisedDestination,
            minutes = minutes,
            summary = summary,
            risk = risk,
            pedestrianRisk = sensoryScoreCalculator.pedestrianRiskLabel(segments),
            constructionRisk = if (segments.any { it.hasConstruction }) "Nearby" else "None",
            eventsRisk = if (segments.any { it.hasEvent }) "Possible" else "None",
            segments = segments,
            routePoints = routePoints,
            explanation = sensoryScoreCalculator.buildExplanation(segments, risk),
        )
    }
}
