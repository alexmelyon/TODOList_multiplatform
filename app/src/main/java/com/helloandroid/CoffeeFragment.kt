package com.helloandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helloandroid.coffeeComponent.CoffeeService
import kotlinx.android.synthetic.main.coffee_fragment.view.*
import javax.inject.Inject

class CoffeeFragment : Fragment() {

    @Inject
    lateinit var coffeeService: CoffeeService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater.inflate(R.layout.coffee_fragment, container, false)
        App.instance.coffeeComponent.inject(this)

        v.coffeeText.text = coffeeService.makeCoffee()
        return v
    }

//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        App.instance.coffeeComponent.inject(this)
//
//        coffeeText.text = coffeeService.makeCoffee()
//    }
}