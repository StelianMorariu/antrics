/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.rx.transformers

import com.stelianmorariu.antrics.domain.rx.SchedulersProvider
import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.CompletableTransformer

/**
 * Created by stelian on 04/02/2018.
 */

class CompletableWorkerTransformer(private val schedulersProvider: SchedulersProvider) : CompletableTransformer {

    override fun apply(upstream: Completable): CompletableSource {
        // subscribeOn will cause all upstream calls to run on an io thread.
        // observeOn will cause all the downstream calls to run on the main thread.
        return upstream.subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }
}
