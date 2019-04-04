package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class Game(var name: String, val worldGroup: Int, val time: Date, var archived: Boolean = false) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface GameDao {

    @Query("SELECT * FROM game")
    fun getFull(): List<Game>

    @Query("SELECT * FROM game WHERE worldGroup = :worldId AND archived = :archived")
    fun getAll(worldId: Int, archived: Boolean): List<Game>

    @Query("SELECT * FROM game WHERE id = :gameId AND worldGroup = :worldId LIMIT 1")
    fun getAll(gameId: Int, worldId: Int): Game

    @Insert
    fun insert(game: Game)

    @Update
    fun update(game: Game)
}