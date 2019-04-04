package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class World(var name: String, val createTime: Date, var archived: Boolean = false) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    override fun toString() = name
}

@Dao
interface WorldDao {

    @Query("SELECT * FROM world")
    fun getFull(): List<World>

    @Query("SELECT * FROM world WHERE archived = :archived")
    fun getAll(archived: Boolean): List<World>

    @Query("SELECT * FROM world WHERE id = :worldId LIMIT 1")
    fun getWorldById(worldId: Long): World

    @Insert
    fun insert(world: World): Long

    @Update
    fun update(world: World)
}