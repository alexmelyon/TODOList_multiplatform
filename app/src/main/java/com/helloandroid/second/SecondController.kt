package com.helloandroid.second

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.R
import com.helloandroid.tab_page.TabPageController

interface SecondControllerDelegate {
    fun left()
    fun right()
}

class SecondController : Controller(), SecondControllerDelegate {

    var pageNum = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_second, container, false)
        getChildRouter(view.findViewById<FrameLayout>(R.id.tab_wrapper_container)).setRoot(RouterTransaction.with(
            TabPageController().apply {
            secondController = this@SecondController
            pageNum = this@SecondController.pageNum
        }))
        return view
    }

    override fun left() {
        if(pageNum > 0) {
            pageNum--
        }
        getChildRouter(view!!.findViewById<FrameLayout>(R.id.tab_wrapper_container)).setRoot(RouterTransaction.with(
            TabPageController().apply {
            secondController = this@SecondController
            pageNum = this@SecondController.pageNum
        }))
    }

    override fun right() {
        pageNum++
        getChildRouter(view!!.findViewById<FrameLayout>(R.id.tab_wrapper_container)).setRoot(RouterTransaction.with(
            TabPageController().apply {
            secondController = this@SecondController
            pageNum = this@SecondController.pageNum
        }))
    }
}