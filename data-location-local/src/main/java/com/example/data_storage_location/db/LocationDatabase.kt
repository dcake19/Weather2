package com.example.data_storage_location.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(LocationEntity::class),version = 1)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun locationDao(): LocationDao
}