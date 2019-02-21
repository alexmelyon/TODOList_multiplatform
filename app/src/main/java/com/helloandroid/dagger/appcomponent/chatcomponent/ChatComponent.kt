package com.helloandroid.dagger.appcomponent.chatcomponent

import com.helloandroid.dagger.appcomponent.chatcomponent.screenChatComponent.ScreenChatModule
import dagger.Subcomponent

@Subcomponent(modules = [ChatModule::class])
@ChatScope
interface ChatComponent {

    fun plusScreenChatComponent(screenChatModule: ScreenChatModule)

}