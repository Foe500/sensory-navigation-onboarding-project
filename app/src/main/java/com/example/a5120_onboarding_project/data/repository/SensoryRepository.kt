package com.example.a5120_onboarding_project.data.repository

import com.example.a5120_onboarding_project.data.PredictedRiskArea
import com.example.a5120_onboarding_project.data.RefugeLocation
import com.example.a5120_onboarding_project.data.RouteOption
import com.example.a5120_onboarding_project.data.predictedRiskAreas
import com.example.a5120_onboarding_project.data.refugeCategories
import com.example.a5120_onboarding_project.data.refugeLocations
import com.example.a5120_onboarding_project.data.routeOptions

class SensoryRepository {
    fun getRouteOptions(destination: String): List<RouteOption> = routeOptions

    fun getRefugeCategories(): List<String> = refugeCategories

    fun getRefugeLocations(category: String): List<RefugeLocation> {
        if (category == "All") return refugeLocations
        return refugeLocations.filter { it.category == category }
    }

    fun getPredictedRiskAreas(): List<PredictedRiskArea> = predictedRiskAreas
}
