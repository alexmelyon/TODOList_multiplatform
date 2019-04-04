package com.helloandroid.list_games

import android.view.ViewGroup
import com.helloandroid.room.Game

interface ListGamesContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<Game>)
        fun showAddGameDialog()
        fun addedAt(pos: Int, game: Game)
        fun archivedAt(pos: Int)
    }

    interface Controller {
        fun onItemClick(game: Game)
        fun getWorldName(): String
        fun createGame(gameName: String)
        fun archiveGameAt(pos: Int)
    }
}