package com.helloandroid.sugarComponent

import com.helloandroid.SugarFragment
import dagger.Component

@Component(modules = [SugarModule::class])
interface SugarComponent {

    fun inject(sugarFragment: SugarFragment)

}