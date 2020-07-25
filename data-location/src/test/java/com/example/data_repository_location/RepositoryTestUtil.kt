package com.example.data_repository_location

import com.example.data_repository_location.auto_complete.LocationPredictionData
import com.example.domain.use_cases.location.Location
import com.example.domain.use_cases.autocomplete.Prediction

object RepositoryTestUtil {

    fun createLocation(i: Int=0,lat: Double=0.0,lng: Double=0.0) =
        Location(
            "place_id_$i",
            "name_$i",
            "region_$i",
            "country_$i",
            lat,
            lng
        )

    fun createLocationData(i: Int=0,lat: Double=0.0,lng: Double=0.0,boundingSize: Double=0.5) =
        LocationData("place_id_$i","name_$i",
            "region_$i","country_$i",lat,lng,
            lat+boundingSize,lng+boundingSize,
            lat-boundingSize,lng-boundingSize)

    fun createPredictionData(i: Int=0,terms: Int=1)
            = LocationPredictionData("place_id_$i",(1..terms).map { "term_$it" })

    fun createPrediction(i: Int=0,terms: Int=1) = Prediction("place_id_$i",(1..terms).map { "term_$it" })

}