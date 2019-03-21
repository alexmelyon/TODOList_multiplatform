package com.helloandroid.list_things

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [ListThingsSubcomponent::class])
interface ListThingsControllerModule {
    @Binds
    @IntoMap
    @ControllerKey(ListThingsController::class)
    fun provideInjectorFactory(builder: ListThingsSubcomponent.Builder): AndroidInjector.Factory<out Controller>

    @Binds
    fun provideView(view: ListThingsView): ListThingsContract.View

    @Binds
    fun provideController(controller: ListThingsController): ListThingsContract.Controller
}

@Subcomponent
interface ListThingsSubcomponent : AndroidInjector<ListThingsController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ListThingsController>()
}