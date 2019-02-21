package com.helloandroid.dagger.appcomponent.chatcomponent.screenChatComponent

import dagger.Subcomponent

@Subcomponent(modules = [ScreenChatModule::class])
@ScreenChatScope
interface ScreenChatComponent {

    fun inject(singleChatFragment: SingleChatFragment)

}