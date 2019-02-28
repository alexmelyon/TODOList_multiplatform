package com.helloandroid

import android.app.Application

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    val worlds = mutableListOf<World>()
    val games = mutableListOf<Game>()
    val gameSessions = mutableListOf<GameSession>()

    override fun onCreate() {
        super.onCreate()
        instance = this
        (1..20).forEach { worldId ->
            worlds.add(World(worldId, "$worldId world"))
            (1..3).forEach { gameId ->
                games.add(Game(gameId, "$gameId game", worldId))
                (1..3).forEach { sessionId ->
                    gameSessions.add(GameSession(sessionId, "$sessionId session", worldId, gameId))
                }
            }
        }
    }
}

data class World(val id: Int, val name: String)

data class Game(val id: Int, val name: String, val worldGroup: Int)

data class GameSession(val id: Int, val name: String, val worldGroup: Int, val gameGroup: Int)