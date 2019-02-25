package com.helloandroid.coffeeComponent

import com.helloandroid.CoffeeFragment
import com.helloandroid.appcomponent.AppComponent
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [CoffeeModule::class]
)
//@Singleton
interface CoffeeComponent {

    fun coffeeService(): CoffeeService

    fun foo(coffeeFragment: CoffeeFragment)
}