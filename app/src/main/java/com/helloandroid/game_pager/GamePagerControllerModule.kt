package com.helloandroid.game_pager

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [GamePagerSubcomponent::class])
interface GamePagerControllerModule {
    @Binds
    @IntoMap
    @ControllerKey(GamePagerController::class)
    fun provideInjectorFactory(builder: GamePagerSubcomponent.Builder): AndroidInjector.Factory<out Controller>
}

@Subcomponent
interface GamePagerSubcomponent : AndroidInjector<GamePagerController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<GamePagerController>()
}