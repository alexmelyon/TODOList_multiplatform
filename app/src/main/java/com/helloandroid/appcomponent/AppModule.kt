package com.helloandroid.appcomponent

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(var context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }
}