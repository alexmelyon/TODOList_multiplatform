package com.helloandroid.list_games

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [ListGamesSubcomponent::class])
interface ListGamesControllerModule {

    @Binds
    @IntoMap
    @ControllerKey(ListGamesController::class)
    fun bindInjectorFactory(builder: ListGamesSubcomponent.Builder): AndroidInjector.Factory<out Controller>

    @Binds
    fun bindView(view: ListGamesView): ListGamesContract.View

    @Binds
    fun bindController(controller: ListGamesController): ListGamesContract.Controller
}

@Subcomponent
interface ListGamesSubcomponent : AndroidInjector<ListGamesController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ListGamesController>()
}