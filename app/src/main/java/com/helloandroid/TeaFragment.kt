package com.helloandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helloandroid.teaComponent.TeaService
import kotlinx.android.synthetic.main.tea_fragment.view.*
import javax.inject.Inject

class TeaFragment : Fragment() {

    @Inject
    lateinit var teaService: TeaService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater.inflate(R.layout.tea_fragment, container, false)
        App.instance.teaComponent.inject(this)

        v.teaText.text = teaService.makeTea()
        return v
    }

//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        App.instance.teaComponent.inject(this)
//
//        teaText.text = teaService.makeTea()
//    }
}