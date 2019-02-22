package com.helloandroid.coffeeComponent

import dagger.Module
import dagger.Provides

@Module
class CoffeeModule {

    @Provides
    fun provideCoffeeService(): CoffeeService {
        return CoffeeService()
    }
}