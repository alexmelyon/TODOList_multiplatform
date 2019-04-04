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
        (1..1L).forEach { worldId ->
            if (db.worldDao().getFull().isEmpty()) {
                db.worldDao().insert(World("$worldId world", now))
            }
            val skillId = 1L
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
            val thingId = 1L
            if (db.thingDao().getFull().isEmpty()) {
                db.thingDao().insert(Thing("First thing", worldId, now))
                db.thingDao().insert(Thing("Second thing", worldId, now))
                db.thingDao().insert(Thing("Third thing", worldId, now))
            }
            (1L..1).forEach { gameId ->
                if (db.gameDao().getFull().isEmpty()) {
                    db.gameDao().insert(Game("$gameId game", worldId, now))
                }
                val characterId = 1L
                if (db.characterDao().getFull().isEmpty()) {
                    db.characterDao().insert(GameCharacter("First Character", gameId, worldId))
                    db.characterDao().insert(GameCharacter("Second Character", gameId, worldId))
                    db.characterDao().insert(GameCharacter("Third Character", gameId, worldId))
                }
                (1..1L).forEach { sessionId ->
                    val minusHour = Calendar.getInstance().apply {
                        add(Calendar.HOUR, -sessionId.toInt())
                    }
                    if (db.gameSessionDao().getFull().isEmpty()) {
                        db.gameSessionDao().insert(GameSession("$sessionId session", worldId, gameId, minusHour.time, true, now))
                    }
                    if(db.hpDiffDao().getFull().isEmpty()) {
                        db.hpDiffDao().insert(HealthPointDiff(1, now, characterId, sessionId, gameId, worldId))
                        db.hpDiffDao().insert(HealthPointDiff(2, now, characterId, sessionId, gameId, worldId))
                    }
                    if(db.skillDiffDao().getFull().isEmpty()) {
                        db.skillDiffDao().insert(SkillDiff(2, now, characterId, skillId, sessionId, gameId, worldId))
                        db.skillDiffDao().insert(SkillDiff(3, now, characterId, skillId, sessionId, gameId, worldId))
                    }
                    if(db.thingDiffDao().getFull().isEmpty()) {
                        db.thingDiffDao().insert(ThingDiff(3, now, characterId, thingId, sessionId, gameId, worldId))
                        db.thingDiffDao().insert(ThingDiff(4, now, characterId, thingId, sessionId, gameId, worldId))
                    }
                    commentDiffs += CommentDiff(4, "Comment", now, sessionId, gameId, worldId)
                }
            }
        }
    }
}

class CommentDiff(
    val id: Long,
    var comment: String,
    val time: Date,
    val sessionGroup: Long,
    val gameGroup: Long,
    val worldGroup: Long,
    var archived: Boolean = false
)
// TODO Комментарий по персонажу, Состояния, особенности (плюсы минусы), дополнительные скиллы, заклинания, баффы, дебаффы, ачивки
// TODO Пресеты по DND, Fallout, SW:KotOR
// TODO Room