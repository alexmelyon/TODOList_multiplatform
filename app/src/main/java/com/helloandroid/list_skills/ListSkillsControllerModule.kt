package com.helloandroid.list_skills

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [ListSkillsSubcomponent::class])
interface ListSkillsControllerModule {

    @Binds
    @IntoMap
    @ControllerKey(ListSkillsController::class)
    fun provideInjectorFactory(builder: ListSkillsSubcomponent.Builder): AndroidInjector.Factory<out Controller>

    @Binds
    fun provideView(view: ListSkillsView): ListSkillsContract.View

    @Binds
    fun provideController(controller: ListSkillsController): ListSkillsContract.Controller
}

@Subcomponent
interface ListSkillsSubcomponent : AndroidInjector<ListSkillsController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ListSkillsController>()
}