package com.helloandroid.teaComponent

import dagger.Module
import dagger.Provides

@Module
class TeaModule {

    @Provides
    fun provideTeaService(): TeaService {
        return TeaService()
    }
}