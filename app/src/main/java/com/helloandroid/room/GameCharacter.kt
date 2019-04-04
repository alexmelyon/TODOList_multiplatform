package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class GameCharacter(var name: String, val gameGroup: Long, val worldGroup: Long, var archived: Boolean = false) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface CharacterDao {
    @Query("SELECT * FROM gamecharacter")
    fun getFull(): List<GameCharacter>

    @Query("SELECT * FROM gamecharacter WHERE worldGroup = :worldId AND gameGroup = :gameId AND archived = :archived")
    fun getAll(worldId: Long, gameId: Long, archived: Boolean): List<GameCharacter>

    @Query("SELECT * FROM gamecharacter WHERE worldGroup = :worldId AND gameGroup = :gameId AND id = :characterId LIMIT 1")
    fun get(worldId: Long, gameId: Long, characterId: Long): GameCharacter

    @Insert
    fun insert(character: GameCharacter): Long

    @Update
    fun update(character: GameCharacter)
}