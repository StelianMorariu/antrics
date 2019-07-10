/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.metrics.profile.dagger


import com.stelianmorariu.antrics.presentation.metrics.profile.MetricsProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MetricsProfileModule {

    @ContributesAndroidInjector
    abstract fun contributeMetricsProfileActivity(): MetricsProfileFragment
}
