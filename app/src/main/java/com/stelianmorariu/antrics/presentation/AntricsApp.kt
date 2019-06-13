/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation

import DaggerAppComponent
import com.stelianmorariu.antrics.domain.dagger.AppInjector
import com.stelianmorariu.antrics.domain.model.Configuration
import com.stelianmorariu.antrics.domain.rx.scheduler.WorkerSchedulerProvider
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class AntricsApp : DaggerApplication() {

    @Inject
    lateinit var configuration: Configuration

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    /**
     * Implementations should return an [AndroidInjector] for the concrete [ ]. Typically, that injector is a [dagger.Component].
     */
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val daggerAppComponent = DaggerAppComponent.builder()
            .application(this)
            .schedulerProvider(WorkerSchedulerProvider())
            .build()

        daggerAppComponent.inject(this)

        AppInjector.init(this)

        return daggerAppComponent
    }

    private fun initTimber() {
        if (configuration.logsEnabled) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseLogTree())
        }
    }
}

