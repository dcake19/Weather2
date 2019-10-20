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
    fun getLocations(): Single<List<LocationEntity>>

    @Query("select * from ${LocationTables.LOCATIONS} where ${LocationColumns.NORTHEAST_LAT} >= :lat and ${LocationColumns.SOUTHWEST_LAT} <= :lat and ${LocationColumns.NORTHEAST_LNG} >= :lng and ${LocationColumns.SOUTHWEST_LNG} <= :lng")
    fun getLocationsBounding(lat: Double,lng: Double): Single<List<LocationEntity>>
}