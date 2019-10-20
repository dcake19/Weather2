package com.example.api_location.repository.db

import androidx.room.*
import io.reactivex.Single

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationEntity: LocationEntity)

    @Delete
    fun delete(locationEntity: LocationEntity)

    @Query("select * from ${LocationTables.LOCATIONS}")
    fun getLocations(): Single<LocationEntity>
}