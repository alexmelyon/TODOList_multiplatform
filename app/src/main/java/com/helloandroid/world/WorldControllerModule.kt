package com.helloandroid.world

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [WorldSubcomponent::class])
interface WorldControllerModule {

    @Binds
    @IntoMap
    @ControllerKey(WorldController::class)
    fun bindInjectorFactory(builder: WorldSubcomponent.Builder): AndroidInjector.Factory<out Controller>

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