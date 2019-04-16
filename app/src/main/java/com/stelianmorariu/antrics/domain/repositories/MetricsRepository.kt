/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.repositories

import android.util.DisplayMetrics
import com.stelianmorariu.antrics.domain.model.MetricsProfile


class MetricsRepository {

    fun getDeviceMetricsProfile(buildCode: String, displayMetrics: DisplayMetrics): MetricsProfile {

        // deviceCode = buildCode
        // deviceName = get device name from somewhere
        // densityQualifier = metrics.density
        // densityDpi = metrics.densityDpi
        // densityBucket = > calculate based on densityQualifier
        // aspectRatio = ?
        // format = long
        // widthPx = metrics.widthPixels
        // heightPx = metrics.heightPixels
        // widthDp = Math.round(widthPx/densityQualifier)
        // heightDp = Math.round(heightPx/densityQualifier)

        return MetricsProfile(
            buildCode,
            buildCode,
            displayMetrics.density,
            displayMetrics.densityDpi,
            getDensityBucket(displayMetrics.density),
            (displayMetrics.heightPixels / displayMetrics.widthPixels).toFloat(),
            "long",
            displayMetrics.widthPixels,
            displayMetrics.heightPixels,
            Math.round(displayMetrics.widthPixels / displayMetrics.density),
            Math.round(displayMetrics.heightPixels / displayMetrics.density)
        )
    }


    private fun getDensityBucket(densityQualifier: Float): String {
        return when {
            densityQualifier <= DENSITY_LDPI_QUALIFIER -> DENSITY_LDPI_LABEL
            densityQualifier <= DENSITY_MDPI_QUALIFIER -> DENSITY_MDPI_LABEL
            densityQualifier <= DENSITY_HDPI_QUALIFIER -> DENSITY_HDPI_LABEL
            densityQualifier <= DENSITY_XHDPI_QUALIFIER -> DENSITY_XHDPI_LABEL
            densityQualifier <= DENSITY_XXHDPI_QUALIFIER -> DENSITY_XXHDPI_LABEL
            densityQualifier <= DENSITY_XXXHDPI_QUALIFIER -> DENSITY_XXXHDPI_LABEL

            else -> DENSITY_HDPI_LABEL
        }
    }


    companion object {
        private const val DENSITY_LDPI_LABEL = "ldpi"
        private const val DENSITY_MDPI_LABEL = "mdpi"
        private const val DENSITY_HDPI_LABEL = "hdpi"
        private const val DENSITY_XHDPI_LABEL = "xhdpi"
        private const val DENSITY_XXHDPI_LABEL = "xxhdpi"
        private const val DENSITY_XXXHDPI_LABEL = "xxxhdpi"

        private const val DENSITY_LDPI_QUALIFIER = 0.75F
        private const val DENSITY_MDPI_QUALIFIER = 1.0F
        private const val DENSITY_HDPI_QUALIFIER = 1.5F
        private const val DENSITY_XHDPI_QUALIFIER = 2.0F
        private const val DENSITY_XXHDPI_QUALIFIER = 3.0F
        private const val DENSITY_XXXHDPI_QUALIFIER = 3.5F
    }

}