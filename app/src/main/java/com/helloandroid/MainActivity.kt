package com.helloandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.helloandroid.dagger.App
import com.helloandroid.dagger.appcomponent.NetworkUtils
import com.helloandroid.dagger.appcomponent.RxUtilsAbs
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
