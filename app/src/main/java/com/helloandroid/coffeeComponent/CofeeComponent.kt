package com.helloandroid.coffeeComponent

import com.helloandroid.CoffeeFragment
import com.helloandroid.appcomponent.AppComponent
import dagger.Component
import dagger.Provides

@Component(
    dependencies = [AppComponent::class],
    modules = [CoffeeModule::class]
)
interface CofeeComponent {

    fun inject(coffeeFragment: CoffeeFragment)
}