package com.helloandroid.appcomponent

import android.content.Context
import com.helloandroid.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun context(): Context

    fun inject(mainActivity: MainActivity)
}