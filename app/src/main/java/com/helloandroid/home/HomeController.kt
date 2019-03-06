package com.helloandroid.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.dagger.ControllerInjector
import com.helloandroid.second.SecondController
import javax.inject.Inject

class HomeController: Controller(), HomeContract.Controller {

//    lateinit var homeComponent: HomeComponent

    @Inject
    lateinit var view: HomeContract.View

    override fun onContextAvailable(context: Context) {
        ControllerInjector.inject(this)

//        homeComponent = DaggerHomeComponent.builder()
//            .mainActivityComponent((activity as MainActivity).mainActivityComponent)
//            .build()
//        homeComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun buttonClick() {
        router.pushController(RouterTransaction.with(SecondController()))
    }
}