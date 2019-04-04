package com.helloandroid.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(
    version = 1,
    entities = [
        World::class,
        Game::class,
        Skill::class]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun worldDao(): WorldDao
    abstract fun gameDao(): GameDao
    abstract fun skillDao(): SkillDao
}