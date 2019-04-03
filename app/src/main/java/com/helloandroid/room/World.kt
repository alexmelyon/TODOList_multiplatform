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
interface WorldDAO {
    @Query("SELECT * FROM world")
    fun getAll(): List<World>

    @Insert
    fun add(world: World)
}