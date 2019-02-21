package com.helloandroid.dagger

import android.app.Application
import com.helloandroid.dagger.appcomponent.AppComponent
import com.helloandroid.dagger.appcomponent.AppModule
import com.helloandroid.dagger.appcomponent.DaggerAppComponent
import com.helloandroid.dagger.appcomponent.chatcomponent.ChatComponent
import com.helloandroid.dagger.appcomponent.chatcomponent.DaggerChatComponent
import com.helloandroid.dagger.appcomponent.chatcomponent.screenChatComponent.DaggerScreenChatComponent
import com.helloandroid.dagger.appcomponent.chatcomponent.screenChatComponent.ScreenChatComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
        lateinit var chatComponent: ChatComponent
            private set
        lateinit var screenChatComponent: ScreenChatComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        chatComponent = DaggerChatComponent.builder()
            .appComponent(appComponent)
            .build()
        screenChatComponent = DaggerScreenChatComponent.builder()
            .chatComponent(chatComponent)
            .build()
    }
}