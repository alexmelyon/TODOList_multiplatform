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
    val characters = mutableListOf<Character>()
    val skills = mutableListOf<Skill>()
    val things = mutableListOf<Thing>()
    val hpDiffs = mutableListOf<HealthPointDiff>()
    val skillDiffs = mutableListOf<SkillDiff>()
    val thingDiffs = mutableListOf<ThingDiff>()
    val commentDiffs = mutableListOf<CommentDiff>()

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
            skills.add(Skill(0, "First skill", worldId))
            things.add(Thing(0, "First thing", worldId))
            (1..3).forEach { gameId ->
                games.add(Game(gameId, "$gameId game", worldId))
                characters.add(Character(0, "The Character", gameId, worldId))
                (1..3).forEach { sessionId ->
                    gameSessions.add(GameSession(sessionId, "$sessionId session", worldId, gameId, Calendar.getInstance().time))
                    hpDiffs += HealthPointDiff(0, 0, 0, sessionId, gameId, worldId)
                    skillDiffs += SkillDiff(0, 0, 0, 0, sessionId, gameId, worldId)
                    thingDiffs += ThingDiff(0, 0, 0, 0, sessionId, gameId, worldId)
                    commentDiffs += CommentDiff(0, "Comment", sessionId, gameId, worldId)
                }
            }
        }
    }
}

class World(val id: Int, val name: String)

class Game(val id: Int, val name: String, val worldGroup: Int)

class GameSession(val id: Int, val name: String, val gameGroup: Int, val worldGroup: Int, val startTime: Date)

class Character(val id: Int, val name: String, val gameGroup: Int, val worldGroup: Int)

class Skill(val id: Int, val name: String, val worldGroup: Int)

class Thing(val id: Int, val name: String, val worldGroup: Int)

class HealthPointDiff(val id: Int, var value: Int, val characterGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)

class SkillDiff(val id: Int, var value: Int, val characterGroup: Int, val skillGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)

class ThingDiff(val id: Int, var value: Int, val characterGroup: Int, val thingGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)

class CommentDiff(val id: Int, var comment: String, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)
