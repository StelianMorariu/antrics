/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */



import android.app.Application
import android.content.Context
import com.stelianmorariu.antrics.domain.model.Configuration
import dagger.Binds
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
@Module
abstract class ApplicationModule {

    /**
     * The @Binds annotation is... binding (duh!) the input of the function to the return type.
     * We use this to bind our Application class as a Context in the AppComponent,
     * so our Module can inject/provide Context
     */
    @Binds
    abstract fun bindContext(application: Application): Context


    /**
     * Kotlin doesn't allow abstract classes to have method implementations, so we add a companion
     * object that will provide the dependencies we need.
     */
    @Module
    companion object {

        /**
         * Provide a [Configuration] object across the whole app.
         */
        @JvmStatic
        @Provides
        @Singleton
        fun provideConfiguration(): Configuration = Configuration()

    }

}