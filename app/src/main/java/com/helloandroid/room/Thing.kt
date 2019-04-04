package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class Thing(var name: String, val worldGroup: Int, val lastUsed: Date, var archived: Boolean = false) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    override fun toString() = name
}

@Dao
interface ThingDao {

    @Query("SELECT * FROM thing")
    fun getFull(): List<Thing>

    @Query("SELECT * FROM thing WHERE worldGroup = :worldId AND archived = :archived")
    fun getAll(worldId: Int, archived: Boolean): List<Thing>

    @Insert
    fun insert(thing: Thing)

    @Update
    fun update(thing: Thing)
}