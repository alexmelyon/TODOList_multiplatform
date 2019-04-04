package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class SkillDiff(
    var value: Int,
    val time: Date,
    val characterGroup: Int,
    val skillGroup: Int,
    val sessionGroup: Int,
    val gameGroup: Int,
    val worldGroup: Int,
    var archived: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface SkillDiffDao {
    @Query("SELECT * FROM skilldiff")
    fun getFull(): List<SkillDiff>

    @Query("SELECT * FROM skilldiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND characterGroup = :characterId AND archived = :archived")
    fun getAllByCharacter(worldId: Int, gameId: Int, characterId: Int, archived: Boolean): List<SkillDiff>

    @Query("SELECT * FROM skilldiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND archived = :archived")
    fun getAllBySession(worldId: Int, gameId: Int, sessionId: Int, archived: Boolean): List<SkillDiff>

    @Query("SELECT * FROM skilldiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND characterGroup = :characterId AND skillGroup = :skillId LIMIT 1")
    fun get(worldId: Int, gameId: Int, sessionId: Int, characterId: Int, skillId: Int): SkillDiff

    @Insert
    fun insert(skillDiff: SkillDiff)

    @Update
    fun update(skillDiff: SkillDiff)
}