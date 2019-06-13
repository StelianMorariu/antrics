/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.rx

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}
