/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */
package com.stelianmorariu.antrics.domain.rx.scheduler

import com.stelianmorariu.antrics.domain.rx.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WorkerSchedulerProvider : SchedulersProvider {

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
