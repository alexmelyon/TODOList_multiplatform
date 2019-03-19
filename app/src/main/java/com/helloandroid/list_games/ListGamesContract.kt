package com.helloandroid.list_games

import android.view.ViewGroup

interface ListGamesContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<String>)
    }

    interface Controller {
        fun onItemClick(pos: Int)
        fun getWorldName(): String
    }
}