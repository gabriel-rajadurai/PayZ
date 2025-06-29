package com.gabriel.payz.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabriel.payz.AnalyticsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val analyticsService: AnalyticsService
) : ViewModel() {

    val transactionInProgress = MutableStateFlow(false)

    fun completeTransaction() {
        viewModelScope.launch {
            transactionInProgress.value = true //reset previous state
            logStats("Pre Transaction")
            delay(2000) //simulate an API call
            transactionInProgress.value = false
            logStats("Post Transaction")
        }
    }

    private suspend fun logStats(state: String) {
        val currentStats = analyticsService.getAnalyticsInterface()?.currentStats
        Log.d(TAG, "$state, current stats => $currentStats")
    }

    companion object {
        private const val TAG = "TransactionsViewModel"
    }

}