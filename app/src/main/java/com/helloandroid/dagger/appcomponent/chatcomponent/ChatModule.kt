package com.helloandroid.dagger.appcomponent.chatcomponent

import android.content.Context
import com.helloandroid.dagger.appcomponent.IDataRepository
import com.helloandroid.dagger.appcomponent.RxUtilsAbs
import dagger.Module
import dagger.Provides

@Module
class ChatModule {

    @Provides
    @ChatScope
    fun provideChatInteract(context: Context, iDataRepository: IDataRepository, iChatStateController: IChatStateController): IChatInteract {
        return ChatInteract(context, iDataRepository, iChatStateController)
    }

    @Provides
    @ChatScope
    fun provideIChatStateController(rxUtilAbs: RxUtilsAbs): IChatStateController {
        return ChatStateController(rxUtilAbs)
    }
}