package com.helloandroid.dagger.components

import com.helloandroid.MainActivity
import com.helloandroid.dagger.modules.AppModule
import com.helloandroid.dagger.modules.ReceiversModule
import com.helloandroid.dagger.modules.UtilsModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, UtilsModule::class, ReceiversModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}