package com.helloandroid.dagger.modules

import android.content.Context
import android.support.annotation.NonNull
import com.helloandroid.dagger.NetworkChannel
import com.helloandroid.dagger.NetworkUtils
import com.helloandroid.dagger.RxUtils
import com.helloandroid.dagger.RxUtilsAbs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @NonNull
    @Singleton
    fun provideRxUtils(context: Context): RxUtilsAbs {
        return RxUtils(context)
    }

    @Provides
    @NonNull
    @Singleton
    fun provideNetworkUtils(context: Context, networkChannel: NetworkChannel): NetworkUtils {
        return NetworkUtils(context, networkChannel)
    }
}