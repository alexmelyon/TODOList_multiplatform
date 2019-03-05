package com.helloandroid.home

import android.view.ViewGroup

interface HomeContract {

    interface View {
        fun createView(container: ViewGroup): android.view.View
    }

    interface Controller {

        fun buttonClick()
    }
}