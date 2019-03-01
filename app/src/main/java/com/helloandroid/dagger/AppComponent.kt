package com.helloandroid.dagger

import com.helloandroid.App
import com.helloandroid.MainActivity
import com.helloandroid.world.WorldContract
import com.helloandroid.world.WorldController
import com.helloandroid.world.WorldView
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


@Component(modules = [AndroidInjectionModule::class, MainActivityModule::class])
interface AppComponent {
    fun inject(app: App)
}

@Subcomponent
interface MainActivitySubComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

@Module(subcomponents = [MainActivitySubComponent::class])
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubComponent.Builder): AndroidInjector.Factory<*>
}

@Component(modules = [WorldControllerModule::class])
interface worldComponent

@Module
interface WorldControllerModule {
    @Binds
    fun bindView(view: WorldView): WorldContract.View

    @Binds
    fun bindController(controller: WorldController): WorldContract.Controller
}