package com.helloandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.helloandroid.dagger.App
import com.helloandroid.dagger.NetworkUtils
import com.helloandroid.dagger.RxUtilsAbs
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var rxUtilsAbs: RxUtilsAbs
    @Inject
    lateinit var networkUtils: NetworkUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)
    }
}
