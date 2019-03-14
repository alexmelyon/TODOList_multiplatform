package com.helloandroid.game

import android.view.ViewGroup

interface GameContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: List<String>)
    }

    interface Controller {
        fun onItemClick(pos: Int)
    }
}