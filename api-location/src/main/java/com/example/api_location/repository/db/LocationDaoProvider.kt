package com.example.api_location.repository.db

import android.content.Context
import androidx.room.Room

class LocationDaoProvider {

    fun getLocationDao(context: Context):LocationDao{
        return Room.databaseBuilder(context,LocationDatabase::class.java,DatabaseName.LOCATION)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
            .locationDao()
    }
}