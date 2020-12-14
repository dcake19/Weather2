package com.example.data_storage_location.db

import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationEntity: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(locationEntities: List<LocationEntity>)

    @Delete
    fun delete(locationEntity: LocationEntity)

    @Query("delete from ${LocationTables.LOCATIONS} where ${LocationColumns.PLACE_ID} in (:placeIds)")
    fun delete(placeIds: List<String>)

    @Query("select * from ${LocationTables.LOCATIONS}")
    fun getLocations(): List<LocationEntity>

    @Query("select * from ${LocationTables.LOCATIONS}")
    fun getLocationsSingle(): Single<List<LocationEntity>>

    @Query("select * from ${LocationTables.LOCATIONS} where ${LocationColumns.PLACE_ID} = :placeId")
    fun getLocation(placeId: String):Single<LocationEntity>

    @Query("select * from ${LocationTables.LOCATIONS} where ${LocationColumns.NORTHEAST_LAT} >= :lat and ${LocationColumns.SOUTHWEST_LAT} <= :lat and ${LocationColumns.NORTHEAST_LNG} >= :lng and ${LocationColumns.SOUTHWEST_LNG} <= :lng")
    fun getLocationsBoundingSingle(lat: Double, lng: Double): Single<List<LocationEntity>>

    @Query("select count(*) from ${LocationTables.LOCATIONS}")
    fun getNumberOfLocations(): Int

}