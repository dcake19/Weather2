package com.example.data_storage_location

import com.example.data_repository_location.LocationData
import com.example.data_storage_location.db.LocationEntity

object CacheTestUtil {
    fun createLocationEntity(i: Int=0, lat: Double=0.0, lng: Double=0.0, boundingSize: Double=0.5) =
        LocationEntity("place_id_$i","locality_$i",
            "admin_area_1_$i","country_$i",lat,lng,
            lat+boundingSize,lng+boundingSize,
            lat-boundingSize,lng-boundingSize)

    fun createLocationEntity(i: String, lat: Double=0.0, lng: Double=0.0, boundingSize: Double=0.5) =
        LocationEntity("place_id_$i","locality_$i",
            "admin_area_1_$i","country_$i",lat,lng,
            lat+boundingSize,lng+boundingSize,
            lat-boundingSize,lng-boundingSize)

    fun createLocationData(i: Int=0,lat: Double=0.0,lng: Double=0.0,boundingSize: Double=0.5) =
        LocationData("place_id_$i","locality_$i",
            "admin_area_1_$i","country_$i",lat,lng,
            lat+boundingSize,lng+boundingSize,
            lat-boundingSize,lng-boundingSize)

    fun createLocationData(i: String,lat: Double=0.0,lng: Double=0.0,boundingSize: Double=0.5) =
        LocationData("place_id_$i","locality_$i",
            "admin_area_1_$i","country_$i",lat,lng,
            lat+boundingSize,lng+boundingSize,
            lat-boundingSize,lng-boundingSize)

}