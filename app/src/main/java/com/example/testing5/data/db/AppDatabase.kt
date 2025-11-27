package com.example.testing5.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedPageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedPageDao(): SavedPageDao
}