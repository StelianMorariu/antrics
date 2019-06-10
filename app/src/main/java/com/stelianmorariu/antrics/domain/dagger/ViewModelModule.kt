/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stelianmorariu.antrics.presentation.metrics.profile.MetricsProfileViewModel


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MetricsProfileViewModel::class)
    abstract fun bindUserViewModel(metricsProfileViewModel: MetricsProfileViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AntricsViewModelFactory): ViewModelProvider.Factory
}
