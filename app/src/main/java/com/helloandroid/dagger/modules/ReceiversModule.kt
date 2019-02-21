package com.helloandroid.dagger.modules

import android.support.annotation.NonNull
import com.helloandroid.dagger.NetworkChannel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReceiversModule {

    @Provides
    @NonNull
//    @Singleton
    fun provideNetworkChannel(): NetworkChannel {
        return NetworkChannel()
    }
}