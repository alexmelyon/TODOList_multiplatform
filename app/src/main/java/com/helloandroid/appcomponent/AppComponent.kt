package com.helloandroid.appcomponent

import com.helloandroid.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun context(): Context

    fun foo(mainActivity: MainActivity)
}