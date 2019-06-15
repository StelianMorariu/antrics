/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.splahscreen

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stelianmorariu.antrics.domain.model.LocalDeviceInfo
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.StatefulResource
import com.stelianmorariu.antrics.domain.repositories.MetricsRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val metricsRepository: MetricsRepository) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _localDeviceInfo: MutableLiveData<LocalDeviceInfo> = MutableLiveData()

    private val _metricsProfile: MutableLiveData<StatefulResource<MetricsProfile>> = MutableLiveData()

    val metricsProfile: LiveData<StatefulResource<MetricsProfile>>
        get() = _metricsProfile

    fun setLocalDeviceInfo(info: LocalDeviceInfo) {
        if (_localDeviceInfo.value == info) {
            return
        } else {
            _localDeviceInfo.value = info
            getMetricsProfile(info)
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    @SuppressLint("CheckResult")
    private fun getMetricsProfile(info: LocalDeviceInfo) {
        metricsRepository.generateProfileIfRequired(info)
            .doOnSubscribe {
                disposables.add(it)
                _metricsProfile.postValue(StatefulResource.loading(null))
            }
            .subscribe({ results ->
                _metricsProfile.postValue(StatefulResource.success(results))
            },
                { error ->
                    Timber.e(error)
                    _metricsProfile.postValue(StatefulResource.error(error.localizedMessage, null))
                }
            )
    }


}