package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class ThingDiff(
    var value: Int,
    val time: Date,
    val characterGroup: Long,
    val thingGroup: Long,
    val sessionGroup: Long,
    val gameGroup: Long,
    val worldGroup: Long,
    var archived: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface ThingDiffDao {
    @Query("SELECT * FROM thingdiff")
    fun getFull(): List<ThingDiff>

    @Query("SELECT * FROM thingdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND characterGroup = :characterid AND archived = :archived")
    fun getAllByCharacter(worldId: Long, gameId: Long, characterid: Long, archived: Boolean): List<ThingDiff>

    @Query("SELECT * FROM thingdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND archived = :archived")
    fun getAllBySession(worldId: Long, gameId: Long, sessionId: Long, archived: Boolean): List<ThingDiff>

    @Query("SELECT * FROM thingdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND characterGroup = :characterId AND id = :id LIMIT 1")
    fun get(worldId: Long, gameId: Long, sessionId: Long, characterId: Long, id: Long): ThingDiff

    @Insert
    fun insert(thingDiff: ThingDiff): Long

    @Update
    fun update(thingDiff: ThingDiff)
}