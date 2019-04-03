package com.helloandroid.room

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class DbModule {
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "MasterCharlistDB")
            .allowMainThreadQueries()
            .build()
    }
}