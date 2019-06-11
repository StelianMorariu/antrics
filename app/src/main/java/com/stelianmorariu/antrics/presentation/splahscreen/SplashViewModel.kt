/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.splahscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.stelianmorariu.antrics.domain.model.LocalDeviceInfo
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.StatefulResource
import com.stelianmorariu.antrics.domain.repositories.MetricsRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(metricsRepository: MetricsRepository) : ViewModel() {

    private val _localDeviceInfo: MutableLiveData<LocalDeviceInfo> = MutableLiveData()

    val metricsProfile: LiveData<StatefulResource<MetricsProfile>> = Transformations
        .switchMap(_localDeviceInfo) { localInfo ->
            metricsRepository.generateProfileIfRequired(localInfo)
        }

    fun setLocalDeviceInfo(info: LocalDeviceInfo) {
        if (_localDeviceInfo.value == info) {
            return
        } else {
            _localDeviceInfo.value = info
        }
    }
}