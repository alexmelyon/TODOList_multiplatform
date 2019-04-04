package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class Skill(var name: String, val worldGroup: Long, val lastUsed: Date, var archived: Boolean = false) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    override fun toString() = name
}

@Dao
interface SkillDao {
    @Query("SELECT * FROM skill")
    fun getFull(): List<Skill>

    @Query("SELECT * FROM skill WHERE worldGroup = :worldId AND archived = :archived")
    fun getAll(worldId: Long, archived: Boolean): List<Skill>

    @Insert
    fun insert(skill: Skill): Long

    @Update
    fun update(skill: Skill)
}