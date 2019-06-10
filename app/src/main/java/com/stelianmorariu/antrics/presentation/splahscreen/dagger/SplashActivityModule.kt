/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.splahscreen.dagger


import com.stelianmorariu.antrics.presentation.splahscreen.SplashscreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class SplashActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashscreenActivity
}
