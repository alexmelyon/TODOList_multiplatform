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
            val skillId = 0
            skills.add(Skill(skillId, "First skill", worldId))
            val thingId = 0
            things.add(Thing(thingId, "First thing", worldId))
            (1..3).forEach { gameId ->
                games.add(Game(gameId, "$gameId game", worldId))
                val characterId = 0
                characters.add(Character(characterId, "First Character", gameId, worldId))
                characters.add(Character(1, "Second Character", gameId, worldId))
                characters.add(Character(2, "Third Character", gameId, worldId))
                (1..3).forEach { sessionId ->
                    val now = Calendar.getInstance().time
                    gameSessions.add(GameSession(sessionId, "$sessionId session", worldId, gameId, now))
                    hpDiffs += HealthPointDiff(1, 0, now, characterId, sessionId, gameId, worldId)
                    skillDiffs += SkillDiff(2, 0, now, characterId, skillId, sessionId, gameId, worldId)
                    thingDiffs += ThingDiff(3, 0, now, characterId, thingId, sessionId, gameId, worldId)
                    commentDiffs += CommentDiff(4, "Comment", now, sessionId, gameId, worldId)
                }
            }
        }
    }
}

class World(val id: Int, val name: String)

class Game(val id: Int, val name: String, val worldGroup: Int)

class GameSession(val id: Int, val name: String, val gameGroup: Int, val worldGroup: Int, val startTime: Date)

class Character(val id: Int, val name: String, val gameGroup: Int, val worldGroup: Int)
// TODO Last used characters descending

class Skill(val id: Int, val name: String, val worldGroup: Int)
// TODO Last used skills descending

class Thing(val id: Int, val name: String, val worldGroup: Int)
// TODO Last used things descending

class HealthPointDiff(val id: Int, var value: Int, val time: Date, val characterGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)

class SkillDiff(val id: Int, var value: Int, val time: Date, val characterGroup: Int, val skillGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)

class ThingDiff(val id: Int, var value: Int, val time: Date, val characterGroup: Int, val thingGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)

class CommentDiff(val id: Int, var comment: String, val time: Date, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int)
// TODO Состояния, особенности (плюсы минусы), дополнительные скиллы, заклинания
