package com.helloandroid.world

import android.view.ViewGroup

interface WorldContract {

    interface Controller {

        fun setView(view: WorldContract.View)
        fun onItemClick(pos: Int)
    }

    interface View {

        fun setController(controller: Controller)

        fun createView(container: ViewGroup): android.view.View

        fun setData(complexes: List<String>)
    }
}