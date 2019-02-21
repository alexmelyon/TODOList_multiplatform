package com.helloandroid.dagger.appcomponent

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun provideRxUtils(context: Context): RxUtilsAbs {
        return RxUtils(context)
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(context: Context): NetworkUtils {
        return NetworkUtils(context)
    }
}