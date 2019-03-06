package com.helloandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.home.HomeController


class MainActivity : AppCompatActivity()/*, HasControllerInjector*/ {

//    @Inject
//    lateinit var dispatchingControllerInjector: DispatchingAndroidInjector<Controller>

    lateinit var mainActivityComponent: MainActivityComponent
    private lateinit var router: Router

//    override fun controllerInjector(): DispatchingAndroidInjector<Controller> {
//        return dispatchingControllerInjector
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityComponent = DaggerMainActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()
        mainActivityComponent.inject(this)

        setContentView(R.layout.activity_main)

        val container = findViewById<ViewGroup>(R.id.controller_container)
        router = Conductor.attachRouter(this, container, savedInstanceState)
        if(!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(HomeController()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
