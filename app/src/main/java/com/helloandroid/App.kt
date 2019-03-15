package com.helloandroid

import android.app.Activity
import android.app.Application
import com.helloandroid.dagger.AppComponent
import com.helloandroid.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import java.util.*
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
                    gameSessions.add(GameSession(sessionId, "$sessionId session", worldId, gameId, Calendar.getInstance().time))
                }
            }
        }
    }
}

class World(val id: Int, val name: String)

class Game(val id: Int, val name: String, val worldGroup: Int)

class GameSession(val id: Int, val name: String, val gameGroup: Int, val worldGroup: Int, val startTime: Date)

class Character(val id: Int, val name: String, val gameGroup: Int, val worldGroup: Int)

class Skill(val id: Int, val name: String, var value: Int, val worldGroup: Int)

class Thing(val id: Int, val name: String, val worldGroup: Int)

class HealthPointDiff(val id: Int, val diff: Int, val characterGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)

class SkillDiff(val id: Int, val diff: Int, val characterGroup: Int, val skillGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)

class ThingDiff(val id: Int, val diff: Int, val characterGroup: Int, val thingGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)

class CommentDiff(val id: Int, var comment: String, val characterGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)
