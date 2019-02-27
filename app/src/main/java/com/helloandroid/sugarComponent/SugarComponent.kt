package com.helloandroid.sugarComponent

import com.helloandroid.SugarFragment
import com.helloandroid.coffeeComponent.CoffeeComponent
import dagger.Component

@Component(
    dependencies = [CoffeeComponent::class/*, TeaComponent::class*/],
    modules = [SugarModule::class]
)
interface SugarComponent {

    fun inject(sugarFragment: SugarFragment)

}