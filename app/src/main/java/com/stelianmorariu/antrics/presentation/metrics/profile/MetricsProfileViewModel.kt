/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.metrics.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.StatefulResource
import com.stelianmorariu.antrics.domain.repositories.MetricsRepository
import javax.inject.Inject

class MetricsProfileViewModel @Inject constructor(metricsRepository: MetricsRepository) : ViewModel() {

    private val _deviceBuildCode: MutableLiveData<String> = MutableLiveData()

    val metricsProfile: LiveData<StatefulResource<MetricsProfile>> = Transformations
        .switchMap(_deviceBuildCode) { code ->
            metricsRepository.getDeviceMetricsProfile(code)
        }

    fun setDeviceBuildCode(buildCode: String) {
        if (_deviceBuildCode.value == buildCode) {
            return
        } else {
            _deviceBuildCode.value = buildCode
        }
    }
}