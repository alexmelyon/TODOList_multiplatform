package com.helloandroid.teaComponent

import com.helloandroid.TeaFragment
import com.helloandroid.appcomponent.AppComponent
import dagger.Component

@Component(dependencies = [AppComponent::class],
    modules = [TeaModule::class])
interface TeaComponent {

    fun inject(teaFragment: TeaFragment)

}