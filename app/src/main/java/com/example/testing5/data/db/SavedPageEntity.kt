package com.example.testing5.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_pages")
data class SavedPageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val title: String,
    val date: String,
    val filePath: String
)
