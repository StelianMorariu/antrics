/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.data.analytics


import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import javax.inject.Inject

/**
 * Custom Fireabse analytics wrapper for Antrics
 */
class MetricsAnalytics @Inject constructor(private val context: Context) {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    /**
     * Log analytics data to understand if the current metrics profile has all the required information:
     * - marketing name
     * - device image
     */
    fun logMetricsProfile(metricsProfile: MetricsProfile) {
        val bundle = Bundle()
        bundle.putString(PARAM_DEVICE_BUILD_CODE, metricsProfile.deviceCode)
        bundle.putString(PARAM_METRICS_MARKETING_NAME, metricsProfile.deviceName)
        bundle.putBoolean(PARAM_METRICS_HAS_DEVICE_IMAGE, metricsProfile.deviceImageUrl.isNotBlank())

        firebaseAnalytics.logEvent(EVENT_METRICS_PROFILE_LOADED, bundle)
    }

    companion object {
        const val EVENT_METRICS_PROFILE_LOADED = "load_metrics_profile"

        const val PARAM_DEVICE_BUILD_CODE = "device_code"
        const val PARAM_METRICS_MARKETING_NAME = "marketing_name"
        const val PARAM_METRICS_HAS_DEVICE_IMAGE = "has_device_image"
    }

}