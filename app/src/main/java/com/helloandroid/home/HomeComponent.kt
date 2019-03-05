package com.helloandroid.home

import com.helloandroid.ActivityComponent
import dagger.Binds
import dagger.Component
import dagger.Module

@Component(dependencies = [ActivityComponent::class], modules = [HomeControllerModule::class])
interface HomeComponent {
    fun inject(controller: HomeController)
//    fun inject(view: HomeView)
}

@Module
abstract class HomeControllerModule {

    @Binds
    abstract fun provideView(view: HomeView): HomeContract.View

//    @Binds
//    abstract fun provideController(controller: HomeController): HomeContract.Controller
}