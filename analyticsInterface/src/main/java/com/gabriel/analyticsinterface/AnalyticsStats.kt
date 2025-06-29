package com.gabriel.analyticsinterface

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalyticsStats(
    val memoryUsage: MemoryUsage,
    val batteryLevel: BatteryInfo,
    val cpuUsage: Long
) : Parcelable
