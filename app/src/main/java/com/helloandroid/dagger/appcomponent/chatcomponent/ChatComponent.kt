package com.helloandroid.dagger.appcomponent.chatcomponent

import android.content.Context
import com.helloandroid.dagger.appcomponent.AppComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [ChatModule::class]
)
@ChatScope
interface ChatComponent {

    fun context(): Context
    fun iChatInteract(): IChatInteract
}