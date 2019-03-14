package com.helloandroid.list_worlds

import com.bluelinelabs.conductor.Controller
import com.helloandroid.dagger.ControllerKey
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [ListWorldsControllerModule.WorldControllerContractModule::class])
class ListWorldsControllerModule {

    @Module(subcomponents = [ListWorldsSubcomponent::class])
    interface WorldControllerContractModule {
        @Binds
        @IntoMap
        @ControllerKey(ListWorldsController::class)
        fun bindInjectorFactory(builder: ListWorldsSubcomponent.Builder): AndroidInjector.Factory<out Controller>

        @Binds
        fun bindView(view: ListWorldsView): ListWorldsContract.View

        @Binds
        fun bindController(controller: ListWorldsController): ListWorldsContract.Controller
    }
}

@Subcomponent
interface ListWorldsSubcomponent : AndroidInjector<ListWorldsController> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ListWorldsController>()
}