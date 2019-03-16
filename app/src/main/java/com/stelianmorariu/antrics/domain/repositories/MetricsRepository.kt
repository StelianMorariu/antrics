/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.repositories

import android.util.DisplayMetrics
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.emptyMetricsProfile

class MetricsRepository {

    fun getDeviceMetricsProfile(displayMetrics: DisplayMetrics): MetricsProfile {

        // todo : get device info
        // todo: calculate all metrics info
        return emptyMetricsProfile()
    }
}