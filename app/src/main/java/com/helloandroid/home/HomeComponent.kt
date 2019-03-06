package com.helloandroid.home

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Subcomponent
interface HomeSubcomponent: AndroidInjector<HomeController> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<HomeController>()
}

@Module(subcomponents = arrayOf(HomeSubcomponent::class))
abstract class HomeControllerModule {
    @Binds
    @IntoMap
    @ControllerKey(HomeController::class)
    abstract fun bindIjectorFactory(builder: HomeSubcomponent.Builder): AndroidInjector.Factory<out Controller>

    @Binds
    abstract fun provideView(view: HomeView): HomeContract.View

    @Binds
    abstract fun provideController(controller: HomeController): HomeContract.Controller
}