package com.helloandroid.list_sessions

import android.view.ViewGroup

interface ListSessionsContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<String>)
    }

    interface Controller {
        fun onItemClick(pos: Int)
        fun getGameName(): String
    }
}