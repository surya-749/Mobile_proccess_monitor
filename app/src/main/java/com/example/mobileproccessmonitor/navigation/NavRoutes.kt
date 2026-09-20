package com.example.mobileproccessmonitor.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Onboarding : Screen

    @Serializable
    data object Dashboard : Screen

    @Serializable
    data class ProcessDetail(val packageName: String) : Screen
}
