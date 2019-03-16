/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */



import com.stelianmorariu.antrics.presentation.metrics.list.MetricsProfileActivity
import com.stelianmorariu.antrics.presentation.metrics.list.dagger.MetricsProfileModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 *
 * This Module's purpose, is to define which Activity depends on which Module.
 * By providing this Module to our [AppComponent]
 * we are allowing Dagger to generate SubComponents and inject our activities.
 * The benefit of this approach, is we don't have to define Dagger Components for our Modules, with
 * the exception of our top-level AppComponent.
 * Subcomponents are components that live below the AppComponent in our graph.
 */
@Module
abstract class ActivityComponentBindModule {
    
//    @ContributesAndroidInjector(modules = arrayOf(MetricsProfileModule::class))
//    abstract fun bindMetricsProfileActivity(): MetricsProfileActivity

}
