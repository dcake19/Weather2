package com.example.api_location.repository.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
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
    @ColumnInfo(name = LocationColumns.SOUTHWEST_LNG)var longitudeSouthWest: Double)
