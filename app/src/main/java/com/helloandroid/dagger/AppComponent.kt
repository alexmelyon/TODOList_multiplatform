package com.helloandroid.dagger

import android.app.Activity
import com.helloandroid.App
import com.helloandroid.MainActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.android.ActivityKey
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Subcomponent
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule {
//    @ContributesAndroidInjector
//    abstract fun contributeActivityAndroidInjector(): MainActivity
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
//    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>
}

@Component(modules = [MainActivityModule::class, AndroidInjectionModule::class])
interface AppComponent {
    fun inject(app: App)
}




//@Component(modules = [WorldControllerModule::class])
//interface WorldComponent
//
//@Module
//interface WorldControllerModule {
//    @Binds
//    fun bindView(view: WorldView): WorldContract.View
//
//    @Binds
//    fun bindController(controller: WorldController): WorldContract.Controller
//}