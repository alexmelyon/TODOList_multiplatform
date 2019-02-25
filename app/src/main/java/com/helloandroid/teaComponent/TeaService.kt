package com.helloandroid.teaComponent

import kotlin.random.Random

class TeaService {

    val number = Random.nextInt()

    fun makeTea(): String {
        return "Make tea. $number"
    }
}