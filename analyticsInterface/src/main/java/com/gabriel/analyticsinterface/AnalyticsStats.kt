package com.gabriel.analyticsinterface

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalyticsStats(
    val memoryUsage : Int
) : Parcelable
