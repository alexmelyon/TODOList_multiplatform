package com.helloandroid.coffeeComponent

import android.content.Context
import javax.inject.Inject
import kotlin.random.Random

class CoffeeService @Inject constructor(val context: Context) : ICoffeeService {

    val number = Random.nextInt()

    override fun makeCoffee(): String {
        return "Let's make coffee! $number"
    }
}