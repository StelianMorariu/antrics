/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */
import com.stelianmorariu.antrics.data.firebase.FirebaseDataSource
import com.stelianmorariu.antrics.domain.dagger.ViewModelModule
import com.stelianmorariu.antrics.domain.model.Configuration
import com.stelianmorariu.antrics.domain.repositories.MetricsRepository
import com.stelianmorariu.antrics.domain.rx.SchedulersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 * By using Dagger Android we do not need to pass our Application instance to any module,
 * we simply need to expose our Application as Context.
 * One of the advantages of Dagger.Android is that your
 * Application & Activities are provided into your graph for you (via subcomponents)
 */
@Module(includes = [ViewModelModule::class])
class AppModule {

    /**
     * Provide a [Configuration] object across the whole app.
     */
    @Provides
    @Singleton
    fun provideConfiguration(): Configuration = Configuration()

    /**
     * Provide a [FirebaseDataSource] object across the whole app.
     */
    @Provides
    @Singleton
    fun provideFirebaseDataSource(): FirebaseDataSource = FirebaseDataSource()

    /**
     * Provide a [MetricsRepository] object across the whole app.
     */
    @Provides
    @Singleton
    fun provideMetricsRepository(
        firebaseDataSource: FirebaseDataSource,
        schedulersProvider: SchedulersProvider
    ): MetricsRepository =
        MetricsRepository(firebaseDataSource, schedulersProvider)


}