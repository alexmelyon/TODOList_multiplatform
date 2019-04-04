package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class GameSession(var name: String, val gameGroup: Int, val worldGroup: Int, val startTime: Date, var open: Boolean, var endTime: Date, var archived: Boolean = false) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString() = name
}

@Dao
interface GameSessionDao {
    @Query("SELECT * FROM gamesession")
    fun getFull(): List<GameSession>

    @Query("SELECT * FROM gamesession WHERE worldGroup = :worldId AND gameGroup = :gameId AND archived = :archived")
    fun getAll(worldId: Int, gameId: Int, archived: Boolean): List<GameSession>

    @Query("SELECT * FROM gamesession WHERE worldGroup = :worldId AND gameGroup = :gameId AND id = :id LIMIT 1")
    fun get(worldId: Int, gameId: Int, id: Int): GameSession

    @Insert
    fun insert(gameSession: GameSession)

    @Update
    fun update(gameSession: GameSession)
}