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
    val deviceImageUrl: String,
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


fun MetricsProfile.withUpdatedImage(imageUrl: String): MetricsProfile {
    return MetricsProfile(
        this.deviceCode,
        this.deviceName,
        imageUrl,
        this.densityQualifier,
        this.densityDpi,
        this.densityBucket,
        this.aspectRatio,
        this.format,
        this.widthPx,
        this.heightPx,
        this.widthDp,
        this.heightDp
    )
}

fun emptyMetricsProfile(): MetricsProfile {
    return MetricsProfile(
        "",
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