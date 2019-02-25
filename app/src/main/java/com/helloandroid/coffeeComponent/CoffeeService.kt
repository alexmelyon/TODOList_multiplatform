package com.helloandroid.coffeeComponent

import kotlin.random.Random

class CoffeeService {

    val number = Random.nextInt()

    fun makeCoffee(): String {
        return "Let's make coffee! $number"
    }
}