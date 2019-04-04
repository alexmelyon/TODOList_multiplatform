package com.helloandroid.list_sessions

import android.view.ViewGroup
import com.helloandroid.room.GameSession

interface ListSessionsContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<GameSession>)
        fun showCreateSessionDialog()
        fun addedAt(pos: Int, session: GameSession)
        fun archivedAt(pos: Int)
    }

    interface Controller {
        fun onItemClick(session: GameSession)
        fun getGameName(): String
        fun createSession(sessionName: String)
        fun archiveSession(pos: Int, session: GameSession)
        fun getDescription(pos: Int): String
        fun getHeader(pos: Int): String
    }
}