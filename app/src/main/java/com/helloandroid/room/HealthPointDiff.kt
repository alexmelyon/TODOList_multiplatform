package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class HealthPointDiff(
    var value: Int,
    val time: Date,
    val characterGroup: Long,
    val sessionGroup: Long,
    val gameGroup: Long,
    val worldGroup: Long,
    var archived: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface HpDiffDao {
    @Query("SELECT * FROM healthpointdiff")
    fun getFull(): List<HealthPointDiff>

    @Query("SELECT * FROM healthpointdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND characterGroup = :characterId AND archived = :archived")
    fun getAllByCharacter(worldId: Long, gameId: Long, characterId: Long, archived: Boolean): List<HealthPointDiff>

    @Query("SELECT * FROM healthpointdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND archived = :archived")
    fun getAllBySession(worldId: Long, gameId: Long, sessionId: Long, archived: Boolean): List<HealthPointDiff>

    @Query("SELECT * FROM healthpointdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND characterGroup = :characterId AND id = :id LIMIT 1")
    fun get(worldId: Long, gameId: Long, sessionId: Long, characterId: Long, id: Long): HealthPointDiff

    @Insert
    fun insert(hpDiff: HealthPointDiff): Long

    @Update
    fun update(hpDiff: HealthPointDiff)
}