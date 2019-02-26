package com.helloandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helloandroid.sugarComponent.ISugarService
import kotlinx.android.synthetic.main.sugar_fragment.view.*
import javax.inject.Inject
import javax.inject.Named

class SugarFragment : Fragment() {

    @Inject
    @field:[Named("Coffee")]
    lateinit var sugarService: ISugarService
    @Inject
    @field:[Named("Tea")]
    lateinit var caneSugarService: ISugarService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        App.instance.sugarComponent.inject(this)

        val v = inflater.inflate(R.layout.sugar_fragment, container)
        v.sugarText.text = sugarService.addSugar() + caneSugarService.addSugar()
        return v
    }

//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        App.instance.sugarComponent.inject(this)
//
//        sugarText.text = sugarService.addSugar()
//    }
}