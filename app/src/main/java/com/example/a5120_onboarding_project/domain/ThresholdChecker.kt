package com.example.a5120_onboarding_project.domain

import com.example.a5120_onboarding_project.data.RouteOption
import com.example.a5120_onboarding_project.data.RouteRisk
import com.example.a5120_onboarding_project.data.RouteSegment

class ThresholdChecker {
    fun exceededSegments(route: RouteOption, crowdThreshold: Int): List<RouteSegment> {
        return route.segments.filter { it.pedestrianCount > crowdThreshold }
    }

    fun exceedsThreshold(route: RouteOption, crowdThreshold: Int): Boolean {
        return exceededSegments(route, crowdThreshold).isNotEmpty()
    }

    fun findAlternative(
        currentRoute: RouteOption,
        availableRoutes: List<RouteOption>,
        crowdThreshold: Int,
    ): RouteOption? {
        return availableRoutes
            .filterNot { it.name == currentRoute.name }
            .filterNot { exceedsThreshold(it, crowdThreshold) }
            .sortedWith(compareBy<RouteOption> { it.risk.sortOrder() }.thenBy { it.minutes })
            .firstOrNull()
    }

    private fun RouteRisk.sortOrder(): Int {
        return when (this) {
            RouteRisk.Low -> 0
            RouteRisk.Medium -> 1
            RouteRisk.High -> 2
        }
    }
}
