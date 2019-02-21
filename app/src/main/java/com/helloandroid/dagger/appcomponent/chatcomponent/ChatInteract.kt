package com.helloandroid.dagger.appcomponent.chatcomponent

import android.content.Context
import com.helloandroid.dagger.appcomponent.IDataRepository

class ChatInteract(
    context: Context,
    iDataRepository: IDataRepository,
    iChatStateController: IChatStateController
) : IChatInteract