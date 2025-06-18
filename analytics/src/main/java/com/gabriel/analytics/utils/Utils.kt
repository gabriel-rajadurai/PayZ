package com.gabriel.analytics.utils

import android.app.ActivityManager
import android.app.ActivityManager.MemoryInfo
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Process
import com.gabriel.analyticsinterface.BatteryInfo
import com.gabriel.analyticsinterface.MemoryUsage

fun Context.getBatteryLevel(): BatteryInfo {
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
        registerReceiver(null, ifilter)
    }

    val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
            || status == BatteryManager.BATTERY_STATUS_FULL

    val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

    return BatteryInfo(
        if (level > 0 && scale > 0)
            (level * 100) / scale
        else -1,
        isCharging
    )
}

fun Context.getMemoryUsage(): MemoryUsage {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val info = MemoryInfo().also { memoryInfo ->
        activityManager.getMemoryInfo(memoryInfo)
    }
    return MemoryUsage(
        availMem = info.availMem,
        totalMem = info.totalMem,
        lowMemory = info.lowMemory
    )
}

fun getCpuUsage(): Long {
    //this will actually return the cpu time of analytics app
    //I wasn't able to find a suitable option to get device cpu usage.
    return Process.getElapsedCpuTime()
}