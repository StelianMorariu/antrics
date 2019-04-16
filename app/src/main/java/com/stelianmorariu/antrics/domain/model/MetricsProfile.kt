/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MetricsProfile(
    val deviceCode: String,
    val deviceName: String,
    val densityQualifier: Float,
    val densityDpi: Int, // ex: 560 ddpi
    val densityBucket: String, // ex: xxhdpi
    val aspectRatio: Float,
    val format: String, // ex: long
    val widthPx: Int,
    val heightPx: Int,
    val widthDp: Int,
    val heightDp: Int
) : Parcelable


fun emptyMetricsProfile(): MetricsProfile {
    return MetricsProfile(
        "",
        "",
        0.0F,
        0,
        "",
        0.0F,
        "",
        0,
        0,
        0,
        0
    )
}