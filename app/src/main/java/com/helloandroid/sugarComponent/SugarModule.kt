package com.helloandroid.sugarComponent

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SugarModule {

    @Provides
    @Named("Coffee")
    fun provideWhiteSugar(): ISugarService {
        return WhiteSugarImpl()
    }

    @Provides
    @Named("Tea")
    fun provideCaneSugar(): ISugarService {
        return CaneSugarImpl()
    }
}