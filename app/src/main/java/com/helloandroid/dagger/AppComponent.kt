package com.helloandroid.dagger

import android.app.Activity
import com.helloandroid.App
import com.helloandroid.MainActivity
import com.helloandroid.list_characters.ListCharactersControllerModule
import com.helloandroid.list_games.ListGamesControllerModule
import com.helloandroid.list_sessions.ListSessionsControllerModule
import com.helloandroid.list_skills.ListSkillsControllerModule
import com.helloandroid.list_things.ListThingsControllerModule
import com.helloandroid.list_worlds.ListWorldsControllerModule
import com.helloandroid.room.DbModule
import com.helloandroid.session.SessionControllerModule
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
    ListSkillsControllerModule::class,
    ListThingsControllerModule::class,
    ListCharactersControllerModule::class,
    ListSessionsControllerModule::class,
    SessionControllerModule::class,
    DbModule::class
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