package com.helloandroid.dagger

import android.app.Activity
import com.helloandroid.App
import com.helloandroid.MainActivity
import com.helloandroid.list_games.ListGamesControllerModule
import com.helloandroid.list_sessions.ListSessionsControllerModule
import com.helloandroid.list_worlds.ListWorldsControllerModule
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.android.ActivityKey
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [
    ListWorldsControllerModule::class,
    ListGamesControllerModule::class,
    ListSessionsControllerModule::class
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

@Component(modules = [
    MainActivityModule::class,
    AndroidInjectionModule::class])
interface AppComponent {
    fun inject(app: App)
}