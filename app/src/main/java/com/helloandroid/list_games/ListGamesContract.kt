package com.helloandroid.list_games

import android.view.ViewGroup

interface ListGamesContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<String>)
        fun showAddGameDialog()
        fun addedAt(pos: Int, gameName: String)
        fun archivedAt(pos: Int)
    }

    interface Controller {
        fun onItemClick(pos: Int)
        fun getWorldName(): String
        fun createGame(gameName: String)
        fun archiveGameAt(pos: Int)
    }
}