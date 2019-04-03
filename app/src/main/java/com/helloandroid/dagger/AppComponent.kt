package com.helloandroid.dagger

import android.app.Activity
import android.content.Context
import com.helloandroid.App
import com.helloandroid.MainActivity
import com.helloandroid.game_pager.GamePagerControllerModule
import com.helloandroid.list_characters.ListCharactersControllerModule
import com.helloandroid.list_games.ListGamesControllerModule
import com.helloandroid.list_sessions.ListSessionsControllerModule
import com.helloandroid.list_skills.ListSkillsControllerModule
import com.helloandroid.list_things.ListThingsControllerModule
import com.helloandroid.list_worlds.ListWorldsControllerModule
import com.helloandroid.room.DbModule
import com.helloandroid.session.SessionControllerModule
import com.helloandroid.world_pager.WorldPagerControllerModule
import dagger.*
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
    WorldPagerControllerModule::class,
    GamePagerControllerModule::class
])
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule(val context: Context) {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>
}

@Module
class ContextModule(val context: Context) {
    @Provides
    fun provideContext() = context
}

@Component(modules = [
    MainActivityModule::class,
    AndroidInjectionModule::class,
    ContextModule::class,
    DbModule::class])
interface AppComponent {
    fun inject(app: App)
}