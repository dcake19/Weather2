package com.example.data_storage_location.db

import android.content.Context
import androidx.room.*
import javax.inject.Inject
import javax.inject.Singleton

class LocationDatabaseProvider(private val context: Context) {

    fun getLocationDao(): LocationDao {
        return Room.databaseBuilder(context, LocationDatabase::class.java,DatabaseName.LOCATION)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
            .locationDao()
    }
}