package com.helloandroid.sugarComponent

import kotlin.random.Random

class CaneSugarImpl : ISugarService {

    val number = Random.nextInt()

    override fun addSugar(): String {
        return "Cane sugar $number"
    }

}