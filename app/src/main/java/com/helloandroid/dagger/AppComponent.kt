package com.helloandroid.dagger

import android.app.Activity
import com.bluelinelabs.conductor.Controller
import com.helloandroid.App
import com.helloandroid.MainActivity
import com.helloandroid.world.WorldContract
import com.helloandroid.world.WorldController
import com.helloandroid.world.WorldView
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.android.ActivityKey
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Subcomponent(modules = [
    WorldControllerModule::class
])
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>
}

@Component(modules = [MainActivityModule::class, AndroidInjectionModule::class])
interface AppComponent {
    fun inject(app: App)
}

@Module(subcomponents = [WorldSubcomponent::class])
interface WorldControllerModule {

    @Binds
    @IntoMap
    @ControllerKey(WorldController::class)
    abstract fun bindInjectorFactory(builder: WorldSubcomponent.Builder): AndroidInjector.Factory<out Controller>

    @Binds
    fun bindView(view: WorldView): WorldContract.View

    @Binds
    fun bindController(controller: WorldController): WorldContract.Controller
}

@Subcomponent
interface WorldSubcomponent : AndroidInjector<WorldController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<WorldController>()
}
