package com.gabriel.analyticsinterface

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BatteryInfo(
    private val batteryLevel: Int,
    private val isCharging: Boolean
) : Parcelable
