package com.gabriel.payz.maintenance

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.gabriel.payz.AnalyticsService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class ScheduledMaintenanceWorker @AssistedInject constructor(
    private val analyticsService: AnalyticsService,
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val currentStats = analyticsService.getAnalyticsInterface()?.currentStats
        Log.d(TAG, "Maintenance, current stats => $currentStats")
        return Result.success()
    }

    companion object {
        private const val TAG = "ScheduledMaintenanceWor"

        fun scheduleMaintenance(
            context: Context
        ) {
            val maintenanceTask = PeriodicWorkRequestBuilder<ScheduledMaintenanceWorker>(
                repeatInterval = 6,
                repeatIntervalTimeUnit = TimeUnit.HOURS,
                flexTimeInterval = 5,
                flexTimeIntervalUnit = TimeUnit.MINUTES
            ).addTag(TAG).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                TAG,
                ExistingPeriodicWorkPolicy.UPDATE,
                maintenanceTask
            )
        }
    }

}