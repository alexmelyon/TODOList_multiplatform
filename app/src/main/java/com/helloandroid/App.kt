package com.helloandroid

import android.app.Application

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    val worlds = mutableListOf<World>()

    override fun onCreate() {
        super.onCreate()
        instance = this
        (1..20).forEach { worlds.add(World(it, "$it world")) }
    }
}

data class World(val id: Int, val name: String)
