package com.helloandroid.sugarComponent

import kotlin.random.Random

class SugarService {

    val number = Random.nextInt()

    fun addSugar(): String {
        return "Sugar includes. $number"
    }
}