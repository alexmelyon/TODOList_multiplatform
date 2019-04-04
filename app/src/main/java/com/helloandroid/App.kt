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
        if (db.worldDao().getFull().isNotEmpty()) {
            return
        }
        (1..3).forEach { worldId ->
            db.worldDao().insert(World("$worldId world", now))
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

            if (db.gameDao().getFull().isNotEmpty()) {
                return
            }
            (1..3).forEach { gameId ->
                db.gameDao().insert(Game("$gameId game", worldId, now))
                val characterId = 1
                if(db.characterDao().getFull().isEmpty()) {
                    db.characterDao().insert(GameCharacter("First Character", gameId, worldId))
                    db.characterDao().insert(GameCharacter("Second Character", gameId, worldId))
                    db.characterDao().insert(GameCharacter("Third Character", gameId, worldId))
                }
                if(db.gameSessionDao().getFull().isNotEmpty()) {
                    return
                }
                (1..6).forEach { sessionId ->
                    val minusHour = Calendar.getInstance().apply {
                        add(Calendar.HOUR, -sessionId)
                    }
                    db.gameSessionDao().insert(GameSession("$sessionId session", worldId, gameId, minusHour.time, true, now))
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