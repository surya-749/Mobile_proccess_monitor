# Walkthrough - Task 3: Process Explorer and Deep-Dive

Completed the implementation of Task 3 for the Mobile Process & Resource Monitor app, delivering robust process aggregation, real-time search, multi-criteria sorting, memory breakdown inspection, and native app settings navigation.

## Changes Made

### 1. Data Layer & Repository (`ProcessRepository.kt`)
- **App Identification**: Resolved Application Icon (`Drawable` converted to `ImageBitmap`), App Name, and Package ID using `PackageManager`.
- **Process State & Last Active**: Classified processes into `Foreground`, `Background`, and `Recent/Cached` states based on `ActivityManager.RunningAppProcessInfo` importance levels and `UsageStatsManager`. Calculated last active timestamps and human-readable time-ago strings.
- **Memory Footprint & Breakdown**: Utilized `ActivityManager.getProcessMemoryInfo` to calculate accurate PSS (Proportional Set Size), Private Dirty, and Shared RAM metrics in MB.
- **Runtime & State Transitions**: Aggregated total foreground runtime over the past 24 hours and recorded recent state transition history.

### 2. ViewModel & Process Explorer (`DashboardViewModel.kt`, `DashboardScreen.kt`)
- **Real-Time Search Bar**: Added reactive search filtering by application name or package ID.
- **Multi-Criteria Sorting**: Implemented sort options for Memory Usage (High to Low), Last Active Time, and Alphabetical (A-Z) via a top bar dropdown menu.
- **Smooth List Rendering**: Designed cards featuring application icons, state badges (with distinctive colors for Foreground, Background, and Cached), package IDs, RAM usage, and CPU load.

### 3. Process Deep-Dive Inspection View (`ProcessDetailScreen.kt`)
- **Memory Breakdown**: Displayed detailed RAM allocation metrics (PSS, Private Dirty, Shared RAM).
- **Execution Details**: Highlighted total foreground runtime in the past 24 hours and recent state transition logs.
- **App Settings Shortcut**: Provided direct intent navigation (`Settings.ACTION_APPLICATION_DETAILS_SETTINGS`) to the target app's native Android Application Info screen.

## Verification Results

- **Build Verification**: Executed `./gradlew :app:assembleDebug` successfully with build status `BUILD SUCCESSFUL`.
