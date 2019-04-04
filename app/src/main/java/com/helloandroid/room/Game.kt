package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class Game(var name: String, val worldGroup: Long, val time: Date, var archived: Boolean = false) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString() = name
}

@Dao
interface GameDao {

    @Query("SELECT * FROM game")
    fun getFull(): List<Game>

    @Query("SELECT * FROM game WHERE worldGroup = :worldId AND archived = :archived")
    fun getAll(worldId: Long, archived: Boolean): List<Game>

    @Query("SELECT * FROM game WHERE id = :gameId AND worldGroup = :worldId LIMIT 1")
    fun getAll(gameId: Long, worldId: Long): Game

    @Insert
    fun insert(game: Game): Long

    @Update
    fun update(game: Game)
}