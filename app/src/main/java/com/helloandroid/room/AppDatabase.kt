package com.helloandroid.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [World::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun worldDao(): WorldDAO
}