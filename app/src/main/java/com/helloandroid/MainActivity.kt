package com.helloandroid

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var context: Context
//    lateinit var coffeeFragment: CoffeeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instance.appComponent.inject(this)

        Log.i("JCD", "PACKAGE NAME ${context.packageName}")
    }
}
