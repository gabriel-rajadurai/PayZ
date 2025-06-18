package com.gabriel.payz

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.gabriel.analyticsinterface.IAnalyticsInterface
import com.gabriel.payz.ui.screens.CompleteTransactionScreen
import com.gabriel.payz.ui.theme.PayZTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val serviceConnection = object : ServiceConnection {
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

                    CompleteTransactionScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}