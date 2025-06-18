package com.gabriel.analyticsinterface

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemoryUsage(
    private val availMem: Long,
    private val totalMem: Long,
    private val lowMemory: Boolean
) : Parcelable
