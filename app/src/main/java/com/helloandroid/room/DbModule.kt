package com.helloandroid.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
class DbModule {
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "MasterCharlistDB").build()
    }
}