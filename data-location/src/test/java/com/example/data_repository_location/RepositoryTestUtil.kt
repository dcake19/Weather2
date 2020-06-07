package com.example.data_repository_location

import com.example.domain.Location

object RepositoryTestUtil {

    fun createLocation(i: Int=0,lat: Double=0.0,lng: Double=0.0) =
        Location("place_id_$i","name_$i","region_$i","country_$i",lat,lng)

    fun createLocationData(i: Int=0,lat: Double=0.0,lng: Double=0.0,boundingSize: Double=0.5) =
        LocationData("place_id_$i","name_$i",
            "region_$i","country_$i",lat,lng,
            lat+boundingSize,lng+boundingSize,
            lat-boundingSize,lng-boundingSize)

}