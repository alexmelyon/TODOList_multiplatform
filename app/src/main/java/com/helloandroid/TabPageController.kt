package com.helloandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bluelinelabs.conductor.Controller

class TabPageController : Controller() {

    lateinit var secondController: SecondControllerDelegate
    var pageNum: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_tab_page, container, false)
        view.findViewById<TextView>(R.id.textView).text = pageNum.toString()
        view.findViewById<Button>(R.id.leftButton).setOnClickListener { secondController.left() }
        view.findViewById<Button>(R.id.rightButton).setOnClickListener { secondController.right() }
        return view
    }

}