package com.helloandroid.sugarComponent

import com.helloandroid.SugarFragment
import com.helloandroid.coffeeComponent.CoffeeComponent
import com.helloandroid.teaComponent.TeaComponent
import dagger.Component

@SugarScope
@Component(dependencies = [CoffeeComponent::class, TeaComponent::class],
    modules = [SugarModule::class])
interface SugarComponent {

    fun inject(sugarFragment: SugarFragment)

}