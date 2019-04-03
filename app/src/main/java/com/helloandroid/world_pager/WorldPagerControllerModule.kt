package com.helloandroid.world_pager

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [WorldPagerSubcomponenet::class])
interface WorldPagerControllerModule {
    @Binds
    @IntoMap
    @ControllerKey(WorldPagerController::class)
    fun provideInjectionFactory(builder: WorldPagerSubcomponenet.Builder): AndroidInjector.Factory<out Controller>
}

@Subcomponent
interface WorldPagerSubcomponenet : AndroidInjector<WorldPagerController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<WorldPagerController>()
}