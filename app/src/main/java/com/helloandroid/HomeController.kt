package com.helloandroid

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bluelinelabs.conductor.Controller

class HomeController : Controller() {

    init {
        Log.i("JCD", "CONSTRUCTOR")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        Log.i("JCD", "ON CREATE VIEW")
        val view = inflater.inflate(R.layout.controller_home, container, false)
        view.findViewById<TextView>(R.id.textView).setText("Wakarimashita")
        return view
    }

    override fun onAttach(view: View) {
        Log.i("JCD", "ON ATTACH")
    }

    override fun onDetach(view: View) {
        Log.i("JCD", "ON DETACH")
    }

    override fun onDestroyView(view: View) {
        Log.i("JCD", "ON DESTROY VIEW")
    }

    override fun onDestroy() {
        Log.i("JCD", "ON DESTROY")
    }
}