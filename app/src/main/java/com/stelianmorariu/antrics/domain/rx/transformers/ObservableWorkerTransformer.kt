/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */



import com.stelianmorariu.antrics.domain.rx.SchedulersProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer


class ObservableWorkerTransformer<T>(private val schedulersProvider: SchedulersProvider) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): Observable<T> {
        // subscribeOn will cause all upstream calls to run on an io thread.
        // observeOn will cause all the downstream calls to run on the main thread.
        return upstream.subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }
}
