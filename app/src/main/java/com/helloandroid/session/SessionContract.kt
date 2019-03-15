package com.helloandroid.session

import android.view.ViewGroup

interface SessionContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
    }
    interface Controller {
        fun getSessionDatetime(): String

    }
}