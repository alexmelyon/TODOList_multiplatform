package com.helloandroid.dagger.appcomponent

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideIDataRepository(): IDataRepository {
        return DataRepository()
    }
}