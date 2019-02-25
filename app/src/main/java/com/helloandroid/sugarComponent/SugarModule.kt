package com.helloandroid.sugarComponent

import dagger.Module
import dagger.Provides

@Module
class SugarModule {

    @Provides
    @SugarScope
    fun provideSugar(): SugarService {
        return SugarService()
    }
}