/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.model

/**
 * Wrapper for [DisplayMetrics].
 */
data class LocalDeviceInfo(
    val buildCode: String,
    val density: Float,
    val densityDpi: Float,
    val heightPixels: Int,
    val widthPixels: Int
)

