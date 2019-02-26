package com.helloandroid.sugarComponent

import kotlin.random.Random

class WhiteSugarImpl : ISugarService {

    val number = Random.nextInt()

    override fun addSugar(): String {
        return "White sugar. $number"
    }
}