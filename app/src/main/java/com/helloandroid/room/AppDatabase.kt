package com.helloandroid.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(
    version = 1,
    entities = [
        World::class,
        Game::class,
        Skill::class,
        Thing::class,
        GameCharacter::class,
        GameSession::class,
        HealthPointDiff::class,
        SkillDiff::class]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun worldDao(): WorldDao
    abstract fun gameDao(): GameDao
    abstract fun skillDao(): SkillDao
    abstract fun thingDao(): ThingDao
    abstract fun characterDao(): CharacterDao
    abstract fun gameSessionDao(): GameSessionDao
    abstract fun hpDiffDao(): HpDiffDao
    abstract fun skillDiffDao(): SkillDiffDao
}