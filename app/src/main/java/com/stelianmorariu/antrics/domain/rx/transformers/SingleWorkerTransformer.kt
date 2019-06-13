/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */


package com.stelianmorariu.antrics.domain.rx.transformers

import com.stelianmorariu.antrics.domain.rx.SchedulersProvider
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer


class SingleWorkerTransformer<T>(private val schedulersProvider: SchedulersProvider) : SingleTransformer<T, T> {

    override fun apply(upstream: Single<T>): SingleSource<T> {
        // subscribeOn will cause all upstream calls to run on an io thread.
        // observeOn will cause all the downstream calls to run on the main thread.
        return upstream.subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }
}
