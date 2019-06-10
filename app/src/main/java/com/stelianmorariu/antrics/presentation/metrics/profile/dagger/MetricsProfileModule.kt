/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.splahscreen.dagger


import com.stelianmorariu.antrics.presentation.metrics.profile.MetricsProfileActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MetricsProfileModule {

    @ContributesAndroidInjector
    abstract fun contributeMetricsProfileActivity(): MetricsProfileActivity
}
