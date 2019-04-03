package com.helloandroid.room

import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Entity
class World(val id: Int, var name: String, val createTime: Date, var archived: Boolean = false) {
    override fun toString() = name
}

interface WorldDAO {
    @Query("SELECT * FROM world")
    fun getAll(): List<World>

    @Insert
    fun add(world: World)
}