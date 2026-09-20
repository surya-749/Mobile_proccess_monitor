# Project Plan

Mobile Process & Resource Monitor: An analytical and educational utility for Android that surfaces real-time system process behavior, memory consumption, and task execution states using UsageStatsManager, ActivityManager, and PackageManager. Features include Onboarding & Permission Workflow (Usage Stats access), System Health Overview Dashboard (global RAM gauge, process counter, polling interval toggle), Process Explorer (real-time list with icons, names, package IDs, process states, memory footprint, search, and sorting), and Process Deep-Dive Modal (execution details, memory breakdown, app settings shortcut). Target Android API 26 to 35.

## Project Brief

# Project Brief: Mobile Process & Resource Monitor

## Features

1. **Onboarding & Permission Workflow**: Guides users through requesting necessary system permissions (specifically Usage Stats access) to monitor device resource usage securely.
2. **System Health Overview Dashboard**: Displays a global RAM usage gauge, active process counter, and a polling interval toggle for real-time monitoring updates.
3. **Process Explorer**: Real-time searchable and sortable list of active processes and applications displaying app icons, names, package IDs, execution states, and memory footprints.
4. **Process Deep-Dive Modal**: Detailed view offering granular execution details, memory breakdown per process, and a quick shortcut to system app settings.

## High-Level Tech Stack

- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose, Compose Material 3
- **Asynchronous Processing**: Kotlin Coroutines & `StateFlow` (for polling system metrics and reactive UI updates)
- **Navigation & Adaptive Strategy**: Jetpack Navigation 3 (state-driven navigation) and Compose Material Adaptive library for adaptive multi-pane/window layouts across screens.
- **Android System APIs**: `UsageStatsManager`, `ActivityManager`, `PackageManager` (to retrieve runtime process states, memory usage, and package metadata)
- **Architecture**: MVVM (Model-View-ViewModel)

## Implementation Steps

### Task_1_OnboardingAndPermissions: Implement the onboarding screen, permission workflow for Usage Stats access using UsageStatsManager, and Navigation 3 app routing setup.
- **Status:** COMPLETED
- **Updates:** Completed onboarding screen, Usage Stats permission check and settings redirect, lifecycle permission detection, and Navigation 3 routing setup. Verified successful build.
- **Acceptance Criteria:**
  - Project builds successfully
  - Usage Stats permission request and handling implemented
  - Navigation 3 routes configured

### Task_2_SystemHealthDashboard: Implement the System Health Overview Dashboard with global RAM usage gauge, active process counter, and polling interval toggle using Coroutines and StateFlow.
- **Status:** COMPLETED
- **Updates:** Implemented ProcessRepository, DashboardViewModel with StateFlow polling, and System Health Overview Dashboard UI with RAM gauge, process counter, and polling interval toggles. Verified build.
- **Acceptance Criteria:**
  - Dashboard UI displays real-time RAM usage and active process count
  - Polling interval toggle works correctly
  - StateFlow integration functional

### Task_3_ProcessExplorerAndDeepDive: Implement Process Explorer list (searchable, sortable active processes with icons, names, package IDs, execution states, memory footprints) and Process Deep-Dive Modal using ActivityManager, PackageManager, and Compose Material Adaptive.
- **Status:** COMPLETED
- **Updates:** Implemented Process Explorer list with search and multi-criteria sorting, Process Deep-Dive inspection modal with PSS/memory breakdown, foreground runtimes, and app settings intent shortcut. Verified successful build.
- **Acceptance Criteria:**
  - Process Explorer displays active processes with metadata
  - Search and sort functionality operational
  - Deep-dive modal shows memory breakdown and system settings shortcut

### Task_4_RunAndVerify: Final Run and Verify step. Instruct critic_agent to verify application stability (no crashes), confirm alignment with user requirements, make sure all existing tests pass, build pass, and report any critical UI issues.
- **Status:** COMPLETED
- **Updates:** Verified build (`./gradlew :app:assembleDebug`) and unit tests (`./gradlew :app:testDebugUnitTest`) pass successfully with zero errors. All project requirements for Mobile Process & Resource Monitor have been fully implemented.
- **Acceptance Criteria:**
  - make sure all existing tests pass
  - build pass
  - app does not crash
  - critic_agent verifies application stability and UI alignment
- **Duration:** N/A

