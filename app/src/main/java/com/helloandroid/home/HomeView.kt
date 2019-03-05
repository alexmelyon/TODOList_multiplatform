package com.helloandroid.home

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.helloandroid.R
import org.jetbrains.anko._FrameLayout
import javax.inject.Inject

class HomeView : _FrameLayout, HomeContract.View {

    @Inject
    constructor(activity: AppCompatActivity) : super(activity) {
        Log.i("JCD", "HOME VIEW CONSTRUCTOR $activity")
    }

    override fun createView(container: ViewGroup): View {
        val inflater = LayoutInflater.from(container.context)

        Log.i("JCD", "ON CREATE VIEW")
        val view = inflater.inflate(R.layout.controller_home, container, false)
        view.findViewById<TextView>(R.id.textView).setText("Wakarimashita")
//        view.button.setOnClickListener { controller.buttonClick() }
        return view
    }

}