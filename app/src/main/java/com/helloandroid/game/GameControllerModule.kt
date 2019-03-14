package com.helloandroid.game

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [GameSubcomponent::class])
interface GameControllerModule {

    @Binds
    @IntoMap
    @ControllerKey(GameController::class)
    fun bindInjectorFactory(builder: GameSubcomponent.Builder): AndroidInjector.Factory<out Controller>

    @Binds
    fun bindView(view: GameView): GameContract.View

    @Binds
    fun bindController(controller: GameController): GameContract.Controller
}

@Subcomponent
interface GameSubcomponent : AndroidInjector<GameController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<GameController>()
}