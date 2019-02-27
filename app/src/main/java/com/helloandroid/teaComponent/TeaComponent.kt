package com.helloandroid.teaComponent

import com.helloandroid.TeaFragment
import com.helloandroid.sugarComponent.ISugarService
import dagger.Component

@Component(
    modules = [TeaModule::class]
)
interface TeaComponent {

    fun sugar(): ISugarService

    fun inject(teaFragment: TeaFragment)

}