package com.helloandroid.dagger.appcomponent

import android.content.Context
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, UtilsModule::class])
@Singleton
interface AppComponent {

    fun context(): Context
    fun iDataRepository(): IDataRepository
    fun rxUtilAbs(): RxUtilsAbs

}