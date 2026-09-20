package com.example.mobileproccessmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.mobileproccessmonitor.navigation.Screen
import com.example.mobileproccessmonitor.ui.dashboard.DashboardScreen
import com.example.mobileproccessmonitor.ui.detail.ProcessDetailScreen
import com.example.mobileproccessmonitor.ui.onboarding.OnboardingScreen
import com.example.mobileproccessmonitor.ui.theme.MobileProccessMonitorTheme
import com.example.mobileproccessmonitor.util.PermissionUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileProccessMonitorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val initialScreen = if (PermissionUtils.hasUsageStatsPermission(context)) {
        Screen.Dashboard
    } else {
        Screen.Onboarding
    }

    val backStack = remember { mutableStateListOf<Screen>(initialScreen) }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.size - 1)
            }
        }
    ) { key: Screen ->
        NavEntry<Screen>(key) {
            when (key) {
                is Screen.Onboarding -> {
                    OnboardingScreen(
                        onPermissionGranted = {
                            backStack.clear()
                            backStack.add(Screen.Dashboard)
                        }
                    )
                }
                is Screen.Dashboard -> {
                    DashboardScreen(
                        onProcessClick = { packageName ->
                            backStack.add(Screen.ProcessDetail(packageName))
                        }
                    )
                }
                is Screen.ProcessDetail -> {
                    ProcessDetailScreen(
                        packageName = key.packageName,
                        onBackClick = {
                            if (backStack.size > 1) {
                                backStack.removeAt(backStack.size - 1)
                            }
                        }
                    )
                }
            }
        }
    }
}
