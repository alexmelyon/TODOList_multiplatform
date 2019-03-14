package com.helloandroid.dagger

import android.app.Activity
import com.helloandroid.App
import com.helloandroid.MainActivity
import com.helloandroid.game.GameControllerModule
import com.helloandroid.world.WorldControllerModule
import com.helloandroid.world.WorldProvider
import dagger.*
import dagger.android.ActivityKey
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Subcomponent(modules = [
    WorldControllerModule::class,
    GameControllerModule::class
])
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>
}

@Singleton
@Component(modules = [
    MainActivityModule::class,
    FuckModule::class,
    AndroidInjectionModule::class])
interface AppComponent {
    fun inject(app: App)
}

@Module
class FuckModule {
    @Provides
    @Singleton
    fun provideWorldProvider(): WorldProvider = WorldProvider()
}