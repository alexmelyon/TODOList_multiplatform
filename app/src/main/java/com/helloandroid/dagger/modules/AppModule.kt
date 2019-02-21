package com.helloandroid.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    lateinit var appContext: Context

    constructor(context: Context) {
        appContext = context
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return appContext
    }
}