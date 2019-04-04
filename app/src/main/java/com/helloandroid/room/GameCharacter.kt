package com.helloandroid.room

import android.arch.persistence.room.*

@Entity
class GameCharacter(var name: String, val gameGroup: Int, val worldGroup: Int, var archived: Boolean = false) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface CharacterDao {
    @Query("SELECT * FROM gamecharacter")
    fun getFull(): List<GameCharacter>

    @Query("SELECT * FROM gamecharacter WHERE worldGroup = :worldId AND gameGroup = :gameId AND archived = :archived")
    fun getAll(worldId: Int, gameId: Int, archived: Boolean): List<GameCharacter>

    @Query("SELECT * FROM gamecharacter WHERE worldGroup = :worldId AND gameGroup = :gameId AND id = :characterId LIMIT 1")
    fun get(worldId: Int, gameId: Int, characterId: Int): GameCharacter

    @Insert
    fun insert(character: GameCharacter)

    @Update
    fun update(character: GameCharacter)
}