package com.helloandroid.list_sessions

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [ListSessionsSubcomponent::class])
interface ListSessionsControllerModule {
    @Binds
    @IntoMap
    @ControllerKey(ListSessionsController::class)
    fun provideInjectorFactory(builder: ListSessionsSubcomponent.Builder) : AndroidInjector.Factory<out Controller>

    @Binds
    fun provideView(view: ListSessionsView) : ListSessionsContract.View

    @Binds
    fun provideController(controller: ListSessionsController) : ListSessionsContract.Controller
}

@Subcomponent
interface ListSessionsSubcomponent : AndroidInjector<ListSessionsController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ListSessionsController>()
}