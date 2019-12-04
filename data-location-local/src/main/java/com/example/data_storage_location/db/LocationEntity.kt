package com.example.data_storage_location.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data_repository_location.LocationData

@Entity(tableName = LocationTables.LOCATIONS)
data class LocationEntity(
    @PrimaryKey @ColumnInfo(name = LocationColumns.PLACE_ID)var placeId: String,
    @ColumnInfo(name = LocationColumns.LOCALITY)var locality: String?,
    @ColumnInfo(name = LocationColumns.ADMIN_AREA_1)var adminArea1: String?,
    @ColumnInfo(name = LocationColumns.COUNTRY)var country: String?,
    @ColumnInfo(name = LocationColumns.LATITUDE)var latitude: Double,
    @ColumnInfo(name = LocationColumns.LONGITUDE)var longitude: Double,
    @ColumnInfo(name = LocationColumns.NORTHEAST_LAT)var latitudeNorthEast: Double,
    @ColumnInfo(name = LocationColumns.NORTHEAST_LNG)var longitudeNorthEast: Double,
    @ColumnInfo(name = LocationColumns.SOUTHWEST_LAT)var latitudeSouthWest: Double,
    @ColumnInfo(name = LocationColumns.SOUTHWEST_LNG)var longitudeSouthWest: Double,
    @ColumnInfo(name = LocationColumns.POSITION)var position: Int){

    constructor(locationData: LocationData): this(locationData.placeId,locationData.name,
        locationData.region,locationData.country,locationData.latitude,locationData.longitude,
        locationData.latitudeNorthEast,locationData.longitudeNorthEast,
        locationData.latitudeSouthWest,locationData.longitudeSouthWest,locationData.position)
}
