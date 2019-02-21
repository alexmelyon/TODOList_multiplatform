package com.helloandroid.dagger

import android.app.Application
import com.helloandroid.dagger.appcomponent.AppComponent
import com.helloandroid.dagger.appcomponent.AppModule
import com.helloandroid.dagger.appcomponent.DaggerAppComponent
import com.helloandroid.dagger.appcomponent.chatcomponent.ChatComponent
import com.helloandroid.dagger.appcomponent.chatcomponent.DaggerChatComponent
import com.helloandroid.dagger.appcomponent.chatcomponent.screenChatComponent.DaggerScreenChatComponent
import com.helloandroid.dagger.appcomponent.chatcomponent.screenChatComponent.ScreenChatComponent

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
            private set
        lateinit var appComponent: AppComponent
            private set
        var chatComponent: ChatComponent? = null
            private set
        var screenChatComponent: ScreenChatComponent? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun plusChatComponent(): ChatComponent? {
        if(chatComponent == null) {
            chatComponent = DaggerChatComponent.builder()
                .appComponent(appComponent)
                .build()
        }
        return chatComponent
    }

    fun clearChatComponent() {
        chatComponent = null
    }

    fun plusScreenChatComponent(): ScreenChatComponent? {
        if(screenChatComponent == null) {

            screenChatComponent = DaggerScreenChatComponent.builder()
                .chatComponent(chatComponent)
                .build()
        }
        return screenChatComponent
    }

    fun clearScreenChatComponent() {
        screenChatComponent = null
    }
}