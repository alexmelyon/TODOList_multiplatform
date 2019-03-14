package com.helloandroid.world

import android.view.ViewGroup

interface WorldContract {

    interface Controller {
        fun onItemClick(pos: Int)
    }

    interface View {
        fun createView(container: ViewGroup): android.view.View

        fun setData(items: List<String>)
    }
}