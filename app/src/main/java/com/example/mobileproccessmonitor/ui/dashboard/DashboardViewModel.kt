package com.example.mobileproccessmonitor.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproccessmonitor.data.ProcessItem
import com.example.mobileproccessmonitor.data.ProcessRepository
import com.example.mobileproccessmonitor.data.SortCriterion
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class SystemHealthState(
    val totalRamBytes: Long = 0L,
    val availableRamBytes: Long = 0L,
    val usedRamBytes: Long = 0L,
    val ramUsagePercent: Float = 0f,
    val totalProcessCount: Int = 0,
    val allProcesses: List<ProcessItem> = emptyList(),
    val filteredProcesses: List<ProcessItem> = emptyList(),
    val searchQuery: String = "",
    val sortCriterion: SortCriterion = SortCriterion.MEMORY_DESC,
    val isRefreshing: Boolean = false,
    val pollingIntervalSeconds: Int = 5
)

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProcessRepository(application)

    private val _uiState = MutableStateFlow(SystemHealthState())
    val uiState: StateFlow<SystemHealthState> = _uiState.asStateFlow()

    private var pollingJob: Job? = null

    init {
        refreshMetrics()
        startPolling(5)
    }

    fun refreshMetrics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            try {
                val memory = repository.getMemoryInfo()
                val processes = repository.getActiveProcesses()
                _uiState.update { state ->
                    val updatedState = state.copy(
                        totalRamBytes = memory.totalBytes,
                        availableRamBytes = memory.availableBytes,
                        usedRamBytes = memory.usedBytes,
                        ramUsagePercent = memory.usagePercent,
                        totalProcessCount = processes.size,
                        allProcesses = processes,
                        isRefreshing = false
                    )
                    updatedState.copy(filteredProcesses = filterAndSort(updatedState.allProcesses, state.searchQuery, state.sortCriterion))
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = query,
                filteredProcesses = filterAndSort(state.allProcesses, query, state.sortCriterion)
            )
        }
    }

    fun setSortCriterion(criterion: SortCriterion) {
        _uiState.update { state ->
            state.copy(
                sortCriterion = criterion,
                filteredProcesses = filterAndSort(state.allProcesses, state.searchQuery, criterion)
            )
        }
    }

    fun setPollingInterval(seconds: Int) {
        _uiState.update { it.copy(pollingIntervalSeconds = seconds) }
        startPolling(seconds)
    }

    private fun filterAndSort(processes: List<ProcessItem>, query: String, criterion: SortCriterion): List<ProcessItem> {
        val filtered = if (query.isBlank()) {
            processes
        } else {
            processes.filter {
                it.appName.contains(query, ignoreCase = true) || it.packageName.contains(query, ignoreCase = true)
            }
        }

        return when (criterion) {
            SortCriterion.MEMORY_DESC -> filtered.sortedByDescending { it.memoryUsageMb }
            SortCriterion.LAST_ACTIVE -> filtered.sortedByDescending { it.lastActiveTimeMillis }
            SortCriterion.ALPHABETICAL -> filtered.sortedBy { it.appName.lowercase() }
        }
    }

    private fun startPolling(seconds: Int) {
        pollingJob?.cancel()
        if (seconds <= 0) return // Manual mode

        pollingJob = viewModelScope.launch {
            while (isActive) {
                delay(seconds * 1000L)
                try {
                    val memory = repository.getMemoryInfo()
                    val processes = repository.getActiveProcesses()
                    _uiState.update { state ->
                        val updatedState = state.copy(
                            totalRamBytes = memory.totalBytes,
                            availableRamBytes = memory.availableBytes,
                            usedRamBytes = memory.usedBytes,
                            ramUsagePercent = memory.usagePercent,
                            totalProcessCount = processes.size,
                            allProcesses = processes
                        )
                        updatedState.copy(filteredProcesses = filterAndSort(updatedState.allProcesses, state.searchQuery, state.sortCriterion))
                    }
                } catch (e: Exception) {
                    // ignore polling errors
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        pollingJob?.cancel()
    }
}
