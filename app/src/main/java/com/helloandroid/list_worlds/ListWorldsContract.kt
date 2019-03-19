package com.helloandroid.list_worlds

import android.view.ViewGroup

interface ListWorldsContract {

    interface Controller {
        fun onItemClick(pos: Int)
        fun createWorld(worldName: String)
        fun removeWorldAt(pos: Int)
    }

    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<String>)
        fun showCreateWorldDialog()
        fun addedAt(i: Int, name: String)
        fun removedAt(pos: Int)
    }
}