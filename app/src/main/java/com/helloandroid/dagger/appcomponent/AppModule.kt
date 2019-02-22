package com.helloandroid.dagger.appcomponent

import android.content.Context
import dagger.Module
import dagger.Provides
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Named
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

    @Provides
    @Singleton
    @Named("SingleThread")
    fun provideSingleThreadExecutor(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    @Named("Multithread")
    fun provideMultithreadExecutor(): ExecutorService? {
        return Executors.newCachedThreadPool()
    }
}