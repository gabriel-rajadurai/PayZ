package com.gabriel.payz

import android.app.Application
import com.gabriel.payz.maintenance.ScheduledMaintenanceWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PayZApp : Application() {

    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate() {
        super.onCreate()

        analyticsService.connectService()
        //assuming user is logged in
        ScheduledMaintenanceWorker.scheduleMaintenance(this)
    }
}