/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */




import android.app.Application
import com.stelianmorariu.antrics.presentation.AntricsApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton


/**
 *
 *
 *
 * Taken From https://github.com/googlesamples/android-architecture/blob/todo-mvp-dagger/
 *
 *
 * This is our top-level (top of the dependency graph) component and the only component we need to define.
 *
 *
 * Here we can find 2 new Modules that weren't used previous to Dagger v2.11.
 *
 *
 * [ActivityComponentBindModule]: Defines a mapping between Modules and Activities/Fragments who use them.
 * This way Dagger can generate subcomponents that will be added in the graph below their parent-component. In our
 * case, the AppComponent.
 *
 *
 * [AndroidInjectionModule]: is the module from Dagger.Android that helps with the generation
 * and location of subcomponents.
 */
@Singleton
@Component(modules = arrayOf(
    ApplicationModule::class,
        ActivityComponentBindModule::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class))
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: AntricsApp)

    /**
     * This interface is used to provide parameters for modules.
     *
     *  Every method annotated with [BindsInstance] will link the method return type
     * to the method input type for the entire dependency graph - this means that we can't have
     * 2 methods with [BindsInstance] that accept the same type of parameters !!
     */
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}
