package com.example.data_storage_location.db

import android.content.Context
import androidx.room.*

class LocationDatabaseProvider(private val context: Context) {

    fun getLocationDao(): LocationDao {
        return Room.databaseBuilder(context, LocationDatabase::class.java,DatabaseName.LOCATION)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
            .locationDao()
    }
}