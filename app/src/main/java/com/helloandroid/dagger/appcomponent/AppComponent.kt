package com.helloandroid.dagger.appcomponent

import com.helloandroid.dagger.appcomponent.chatcomponent.ChatComponent
import com.helloandroid.dagger.appcomponent.chatcomponent.ChatModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, UtilsModule::class])
@Singleton
interface AppComponent {

    fun plusChatComponent(chatModule: ChatModule): ChatComponent
}