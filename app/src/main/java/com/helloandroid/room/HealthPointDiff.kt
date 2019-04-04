package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class HealthPointDiff(
    var value: Int,
    val time: Date,
    val characterGroup: Int,
    val sessionGroup: Int,
    val gameGroup: Int,
    val worldGroup: Int,
    var archived: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface HpDiffDao {
    @Query("SELECT * FROM healthpointdiff")
    fun getFull(): List<HealthPointDiff>

    @Query("SELECT * FROM healthpointdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND characterGroup = :characterId AND archived = :archived")
    fun getAllByCharacter(worldId: Int, gameId: Int, characterId: Int, archived: Boolean): List<HealthPointDiff>

    @Query("SELECT * FROM healthpointdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND archived = :archived")
    fun getAllBySession(worldId: Int, gameId: Int, sessionId: Int, archived: Boolean): List<HealthPointDiff>

    @Query("SELECT * FROM healthpointdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND characterGroup = :characterId AND id = :id LIMIT 1")
    fun get(worldId: Int, gameId: Int, sessionId: Int, characterId: Int, id: Int): HealthPointDiff

    @Insert
    fun insert(hpDiff: HealthPointDiff)

    @Update
    fun update(hpDiff: HealthPointDiff)
}