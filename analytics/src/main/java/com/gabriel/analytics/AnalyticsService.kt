package com.gabriel.analytics

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.gabriel.analyticsinterface.AnalyticsStats
import com.gabriel.analyticsinterface.IAnalyticsInterface

class AnalyticsService : Service() {

    private val analyticsInterface by lazy {
        object : IAnalyticsInterface.Stub() {
            override fun getCurrentStats(): AnalyticsStats {
                return AnalyticsStats(
                    100 //hardcoding for now to test the IPC setup
                )
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "Service bound")
        val binder = analyticsInterface.asBinder()
        binder.linkToDeath(
            {
                Log.d(TAG, "Service Killed")
            },0
        )
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "Service not bound")
        return super.onUnbind(intent)
    }

    companion object {
        private const val TAG = "AnalyticsService"
    }
}