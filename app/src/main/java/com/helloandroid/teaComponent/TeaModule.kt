package com.helloandroid.teaComponent

import com.helloandroid.sugarComponent.CaneSugarImpl
import com.helloandroid.sugarComponent.ISugarService
import dagger.Module
import dagger.Provides

@Module
class TeaModule {

    @Provides
    fun provideTeaService(): TeaService {
        return TeaService()
    }

    @Provides
    fun provideSugar(): ISugarService {
        return CaneSugarImpl()
    }
}