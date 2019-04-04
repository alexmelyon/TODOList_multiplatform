package com.helloandroid

import android.app.Activity
import android.app.Application
import com.helloandroid.dagger.AppComponent
import com.helloandroid.dagger.ContextModule
import com.helloandroid.dagger.DaggerAppComponent
import com.helloandroid.room.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import java.util.*
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var db: AppDatabase

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

    val gameSessions = mutableListOf<GameSession>()
    val characters = mutableListOf<Character>()
    val hpDiffs = mutableListOf<HealthPointDiff>()
    val skillDiffs = mutableListOf<SkillDiff>()
    val thingDiffs = mutableListOf<ThingDiff>()
    val commentDiffs = mutableListOf<CommentDiff>()

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
        appComponent.inject(this)

        initWorld()
    }

    fun initWorld() {
        val now = Calendar.getInstance().time
        (1..1).forEach { worldId ->
            if (db.worldDao().getFull().isEmpty()) {
                db.worldDao().insert(World("$worldId world", now))
            }
            val skillId = 1
            if (db.skillDao().getFull().isEmpty()) {
                db.skillDao().insert(Skill("First skill", worldId, now))
                db.skillDao().insert(Skill("Second skill", worldId, now))
                db.skillDao().insert(Skill("Third skill", worldId, now))
                db.skillDao().insert(Skill("4 skill", worldId, now))
                db.skillDao().insert(Skill("5 skill", worldId, now))
                db.skillDao().insert(Skill("6 skill", worldId, now))
                db.skillDao().insert(Skill("7 skill", worldId, now))
                db.skillDao().insert(Skill("8 skill", worldId, now))
                db.skillDao().insert(Skill("9 skill", worldId, now))
                db.skillDao().insert(Skill("10 skill", worldId, now))
                db.skillDao().insert(Skill("11 skill", worldId, now))
                db.skillDao().insert(Skill("12 skill", worldId, now))
                db.skillDao().insert(Skill("13 skill", worldId, now))
            }
            val thingId = 1
            if(db.thingDao().getFull().isEmpty()) {
                db.thingDao().insert(Thing("First thing", worldId, now))
                db.thingDao().insert(Thing("Second thing", worldId, now))
                db.thingDao().insert(Thing("Third thing", worldId, now))
            }
            (1..1).forEach { gameId ->
                if (db.gameDao().getFull().isEmpty()) {
                    db.gameDao().insert(Game("$gameId game", worldId, now))
                }
                val characterId = 0
                characters.add(Character(characterId, "First Character", gameId, worldId))
                characters.add(Character(1, "Second Character", gameId, worldId))
                characters.add(Character(2, "Third Character", gameId, worldId))
                (1..6).forEach { sessionId ->
                    val minusHour = Calendar.getInstance().apply {
                        add(Calendar.HOUR, -sessionId)
                    }
                    gameSessions.add(GameSession(sessionId, "$sessionId session", worldId, gameId, minusHour.time, true, now))
                    hpDiffs += HealthPointDiff(1, 1, now, characterId, sessionId, gameId, worldId)
                    skillDiffs += SkillDiff(2, 2, now, characterId, skillId, sessionId, gameId, worldId)
                    skillDiffs += SkillDiff(22, 3, now, characterId, skillId, sessionId, gameId, worldId)
                    thingDiffs += ThingDiff(3, 3, now, characterId, thingId, sessionId, gameId, worldId)
                    thingDiffs += ThingDiff(33, 4, now, characterId, thingId, sessionId, gameId, worldId)
                    commentDiffs += CommentDiff(4, "Comment", now, sessionId, gameId, worldId)
                }
            }
        }
    }
}

class GameSession(val id: Int, var name: String, val gameGroup: Int, val worldGroup: Int, val startTime: Date, var open: Boolean, var endTime: Date, var archived: Boolean = false) {
    override fun toString() = name
}

class Character(val id: Int, var name: String, val gameGroup: Int, val worldGroup: Int, var archived: Boolean = false)

class HealthPointDiff(val id: Int, var value: Int, val time: Date, val characterGroup: Int, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int, var archived: Boolean = false)

class SkillDiff(
    val id: Int,
    var value: Int,
    val time: Date,
    val characterGroup: Int,
    val skillGroup: Int,
    val sessionGroup: Int,
    val gameGroup: Int,
    val worldGroup: Int,
    var archived: Boolean = false
)

class ThingDiff(
    val id: Int,
    var value: Int,
    val time: Date,
    val characterGroup: Int,
    val thingGroup: Int,
    val sessionGroup: Int,
    val gameGroup: Int,
    val worldGroup: Int,
    var archived: Boolean = false
)

class CommentDiff(val id: Int, var comment: String, val time: Date, val sessionGroup: Int, val gameGroup: Int, val worldGroup: Int, var archived: Boolean = false)
// TODO Комментарий по персонажу, Состояния, особенности (плюсы минусы), дополнительные скиллы, заклинания, баффы, дебаффы, ачивки
// TODO Пресеты по DND, Fallout, SW:KotOR
// TODO Room