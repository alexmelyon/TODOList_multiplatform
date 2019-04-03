package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class World(var name: String, val createTime: Date, var archived: Boolean = false) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    override fun toString() = name
}

@Dao
interface WorldDao {

    @Query("SELECT * FROM world WHERE archived = :archived")
    fun getAll(archived: Boolean): List<World>

    @Query("SELECT * FROM world WHERE id = :worldId LIMIT 1")
    fun getWorldById(worldId: Int): World

    @Insert
    fun insert(world: World)

    @Update
    fun update(world: World)
}