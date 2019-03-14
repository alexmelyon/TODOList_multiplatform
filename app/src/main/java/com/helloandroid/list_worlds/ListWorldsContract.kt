package com.helloandroid.list_worlds

import android.view.ViewGroup

interface ListWorldsContract {

    interface Controller {
        fun onItemClick(pos: Int)
    }

    interface View {
        fun createView(container: ViewGroup): android.view.View

        fun setData(items: List<String>)
    }
}