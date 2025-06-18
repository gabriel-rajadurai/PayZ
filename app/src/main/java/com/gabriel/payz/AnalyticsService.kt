package com.gabriel.payz

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.gabriel.analyticsinterface.IAnalyticsInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsService @Inject constructor(
    @ApplicationContext private val context: Context
) {

    var analyticsInterface: IAnalyticsInterface? = null
        private set

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "Analytics service connected ")
            analyticsInterface = IAnalyticsInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "Analytics service connected ")
            analyticsInterface = null
        }
    }

    fun connectService() {
        context.bindService(
            Intent().apply {
                component = ComponentName(
                    "com.gabriel.analytics",
                    "com.gabriel.analytics.AnalyticsService"
                )
                action = "com.gabriel.analytics.BIND_ANALYTICS_SERVICE"
            },
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    fun disconnectService() {
        context.unbindService(serviceConnection)
    }


    companion object {
        private const val TAG = "AnalyticsService"
    }
}