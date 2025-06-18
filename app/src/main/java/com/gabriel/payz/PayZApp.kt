package com.gabriel.payz

import android.app.Application
import com.gabriel.payz.maintenance.ScheduledMaintenanceWorker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PayZApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //assuming user is logged in
        ScheduledMaintenanceWorker.scheduleMaintenance(this)
    }
}