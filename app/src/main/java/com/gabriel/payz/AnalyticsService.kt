package com.gabriel.payz

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.gabriel.analyticsinterface.IAnalyticsInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class AnalyticsService @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var analyticsInterface: IAnalyticsInterface? = null

    private var isConnected = true
    private val mutex = Mutex()

    //Returns the instance of AIDL interface if service connected, or bind to service and then returns
    suspend fun getAnalyticsInterface(): IAnalyticsInterface? {
        if (isConnected && analyticsInterface != null) {
            return analyticsInterface
        }
        //Using mutex to prevent multiple calls to bind with service
        return mutex.withLock {
            withTimeoutOrNull(TIMEOUT_MILLIS, {
                createInterface()
            })
        }
    }

    //Bind to Analytics service and gracefully handle service disconnection and binder deaths
    private suspend fun createInterface(): IAnalyticsInterface? =
        suspendCancellableCoroutine { continuation ->
            val serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    Log.d(TAG, "Analytics service connected ")
                    isConnected = true
                    analyticsInterface = IAnalyticsInterface.Stub.asInterface(service)
                    safeResume(
                        analyticsInterface
                    )
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.d(TAG, "Analytics service disconnected $name")
                    cleanUpAndResume()
                }

                override fun onBindingDied(name: ComponentName?) {
                    Log.d(TAG, "Binding died $name")
                    cleanUpAndResume()
                }

                private fun cleanUpAndResume() {
                    analyticsInterface = null
                    isConnected = false
                    safeResume(null)
                }

                private fun safeResume(analyticsInterface: IAnalyticsInterface?) {
                    if (continuation.isActive)
                        continuation.resume(analyticsInterface)
                }
            }

            val status = context.bindService(
                Intent().apply {
                    component = ComponentName(
                        ANALYTICS_SERVICE_PKG,
                        ANALYTICS_SERVICE
                    )
                    action = ANALYTICS_SERVICE_BINDER_ACTION
                },
                serviceConnection,
                Context.BIND_AUTO_CREATE
            )

            if (!status) {
                Log.e(TAG, "Failed to bind analytics interface")
                if (continuation.isActive) continuation.resume(null)
            }
        }


    companion object {
        private const val TAG = "AnalyticsService"
        private const val TIMEOUT_MILLIS = 5000L

        private const val ANALYTICS_SERVICE_PKG = "com.gabriel.analytics"
        private const val ANALYTICS_SERVICE = "com.gabriel.analytics.AnalyticsService"
        private const val ANALYTICS_SERVICE_BINDER_ACTION =
            "com.gabriel.analytics.BIND_ANALYTICS_SERVICE"
    }
}