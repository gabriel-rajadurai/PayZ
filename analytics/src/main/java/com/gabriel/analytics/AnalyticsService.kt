package com.gabriel.analytics

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import com.gabriel.analytics.utils.getBatteryLevel
import com.gabriel.analytics.utils.getCpuUsage
import com.gabriel.analytics.utils.getMemoryUsage
import com.gabriel.analyticsinterface.AnalyticsStats
import com.gabriel.analyticsinterface.IAnalyticsInterface

class AnalyticsService : Service() {

    private val analyticsInterface by lazy {
        object : IAnalyticsInterface.Stub() {
            override fun getCurrentStats(): AnalyticsStats {
                return AnalyticsStats(
                    memoryUsage = getMemoryUsage(),
                    batteryLevel = getBatteryLevel(),
                    cpuUsage = getCpuUsage()
                )
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "Service bound")
        val binder = analyticsInterface.asBinder()
        //This ensures the service is unbound when the binder is killed
        binder.linkToDeath(
            {
                Log.d(TAG, "Service Killed")
            }, 0
        )
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "Service not bound")
        return super.onUnbind(intent)
    }

    companion object {
        private const val TAG = "AnalyticsService"
        private const val READ_ANALYTICS_PERMISSION = "com.gabriel.analytics.READ_ANALYTICS"
    }
}