package com.helloandroid.dagger

import com.helloandroid.App
import com.helloandroid.MainActivity
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Subcomponent
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeActivityAndroidInjector(): MainActivity
//    @Binds
//    @IntoMap
//    @ClassKey(MainActivity::class)
//    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubcomponent.Builder): AndroidInjector.Factory<*>
}

@Component(modules = [MainActivityModule::class, AndroidInjectionModule::class/*, WorldControllerModule::class*/])
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