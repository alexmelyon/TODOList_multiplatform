package com.helloandroid.sugarComponent

import com.helloandroid.SugarFragment
import com.helloandroid.coffeeComponent.CofeeComponent
import com.helloandroid.teaComponent.TeaComponent
import dagger.Component

@Component(dependencies = [CofeeComponent::class, TeaComponent::class],
    modules = [SugarModule::class])
interface SugarComponent {

    fun inject(sugarFragment: SugarFragment)

}