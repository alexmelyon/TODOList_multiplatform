package com.helloandroid.list_worlds

import android.view.ViewGroup
import com.helloandroid.World

interface ListWorldsContract {

    interface Controller {
        fun onItemClick(pos: Int)
        fun createWorld(worldName: String)
        fun archiveWorldAt(pos: Int)
    }

    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<World>)
        fun showCreateWorldDialog()
        fun addedAt(i: Int, world: World)
        fun archivedAt(pos: Int)
    }
}