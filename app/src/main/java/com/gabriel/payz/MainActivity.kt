package com.gabriel.payz

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gabriel.analyticsinterface.IAnalyticsInterface
import com.gabriel.payz.ui.theme.PayZTheme

class MainActivity : ComponentActivity() {

    private val serviceConnection  = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "Analytics service connected ")
            val analyticsInterface = IAnalyticsInterface.Stub.asInterface(service)
            Log.d(TAG, "Current stats : ${analyticsInterface.currentStats} ")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "Analytics service connected ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PayZTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                bindToService()
                            }
                        ) {
                            Text("Bind to service")
                        }
                    }
                }
            }
        }
    }

    private fun bindToService() {
        bindService(
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

    companion object {
        private const val TAG = "MainActivity"
    }
}