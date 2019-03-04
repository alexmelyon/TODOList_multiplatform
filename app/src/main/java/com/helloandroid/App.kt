package com.helloandroid

import android.app.Activity
import android.app.Application
import com.helloandroid.dagger.AppComponent
import com.helloandroid.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        println("GET ACTIVITY INJECTOR")
        return dispatchingAndroidInjector
    }

    companion object {
        lateinit var instance: App
            private set
        lateinit var appComponent: AppComponent
            private set
    }

    val worlds = mutableListOf<World>()
    val games = mutableListOf<Game>()
    val gameSessions = mutableListOf<GameSession>()

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .build()
        appComponent.inject(this)

        initWorld()
    }

    fun initWorld() {
        (1..3).forEach { worldId ->
            worlds.add(World(worldId, "$worldId world"))
            (1..3).forEach { gameId ->
                games.add(Game(gameId, "$gameId game", worldId))
                (1..3).forEach { sessionId ->
                    gameSessions.add(GameSession(sessionId, "$sessionId session", worldId, gameId))
                }
            }
        }
    }
}

data class World(val id: Int, val name: String)

data class Game(val id: Int, val name: String, val worldGroup: Int)

data class GameSession(val id: Int, val name: String, val worldGroup: Int, val gameGroup: Int)