package com.helloandroid.dagger.appcomponent.chatcomponent.screenChatComponent

import com.helloandroid.dagger.appcomponent.chatcomponent.ChatComponent
import dagger.Component

@Component(
    dependencies = [ChatComponent::class],
    modules = [ScreenChatModule::class]
)
@ScreenChatScope
interface ScreenChatComponent {

    fun inject(singleChatFragment: SingleChatFragment)

}