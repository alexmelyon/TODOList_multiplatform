package com.helloandroid.room

import android.arch.persistence.room.*
import java.util.*

@Entity
class CommentDiff(
    var comment: String,
    val time: Date,
    val sessionGroup: Long,
    val gameGroup: Long,
    val worldGroup: Long,
    var archived: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface CommentDiffDao {
    @Query("SELECT * FROM commentdiff")
    fun getFull(): List<CommentDiff>

    @Query("SELECT * FROM commentdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND archived = :archived")
    fun getAll(worldId: Long, gameId: Long, sessionId: Long, archived: Boolean): List<CommentDiff>

    @Query("SELECT * FROM commentdiff WHERE worldGroup = :worldId AND gameGroup = :gameId AND sessionGroup = :sessionId AND id = :id LIMIT 1")
    fun get(worldId: Long, gameId: Long, sessionId: Long, id: Long): CommentDiff

    @Insert
    fun insert(commentDiff: CommentDiff): Long

    @Update
    fun update(commentDiff: CommentDiff)
}
