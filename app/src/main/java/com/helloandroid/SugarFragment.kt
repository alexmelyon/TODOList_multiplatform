package com.helloandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helloandroid.sugarComponent.ISugarService
import kotlinx.android.synthetic.main.sugar_fragment.view.*
import javax.inject.Inject

class SugarFragment : Fragment() {

    @Inject
    lateinit var sugarService: ISugarService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.instance.sugarComponent.inject(this)

        val v = inflater.inflate(R.layout.sugar_fragment, container)
        v.sugarText.text = sugarService.addSugar()
        return v
    }
}