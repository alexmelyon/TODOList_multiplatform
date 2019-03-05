package com.helloandroid.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.MainActivity
import com.helloandroid.second.SecondController
import javax.inject.Inject

class HomeController: Controller(), HomeContract.Controller {

    lateinit var component: HomeComponent

    @Inject
    lateinit var view: HomeContract.View

    override fun onContextAvailable(context: Context) {
        component = DaggerHomeComponent.builder()
            .activityComponent((activity as MainActivity).activityComponent)
            .build()
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun buttonClick() {
        router.pushController(RouterTransaction.with(SecondController()))
    }
}