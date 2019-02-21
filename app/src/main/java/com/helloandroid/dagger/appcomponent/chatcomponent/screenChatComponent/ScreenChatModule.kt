package com.helloandroid.dagger.appcomponent.chatcomponent.screenChatComponent

import android.content.Context
import com.helloandroid.dagger.appcomponent.chatcomponent.IChatInteract
import dagger.Module
import dagger.Provides

@Module
class ScreenChatModule {

    @Provides
    @ScreenChatScope
    fun provideIScreenChatPresenter(context: Context, iChatInteract: IChatInteract): IScreenChatPresenter {
        return ScreenChatPresenter(context, iChatInteract)
    }
}