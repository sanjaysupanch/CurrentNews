package com.example.testing5.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedPageDao {
    @Query("SELECT * FROM saved_pages ORDER BY id DESC")
    fun getAll(): Flow<List<SavedPageEntity>>

    @Query("SELECT * FROM saved_pages WHERE id = :id")
    suspend fun getById(id: Int): SavedPageEntity?

    @Insert
    suspend fun insert(e: SavedPageEntity)
}
