package com.helloandroid.session

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [SessionSubcomponent::class])
interface SessionControllerModule {

    @Binds
    @IntoMap
    @ControllerKey(SessionController::class)
    fun provideInjectorFactory(builder: SessionSubcomponent.Builder): AndroidInjector.Factory<out Controller>

    @Binds
    fun provideView(view: SessionView): SessionContract.View

    @Binds
    fun provideController(controller: SessionController): SessionContract.Controller
}

@Subcomponent
interface SessionSubcomponent : AndroidInjector<SessionController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SessionController>()
}