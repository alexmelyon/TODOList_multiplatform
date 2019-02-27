package com.helloandroid.coffeeComponent

import com.helloandroid.CoffeeFragment
import com.helloandroid.appcomponent.AppComponent
import com.helloandroid.sugarComponent.ISugarService
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [CoffeeModule::class]
)
interface CoffeeComponent {

    fun sugar(): ISugarService

    fun inject(coffeeFragment: CoffeeFragment)
}