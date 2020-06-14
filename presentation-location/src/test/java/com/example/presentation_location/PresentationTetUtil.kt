package com.example.presentation_location

import com.example.domain.Location

object PresentationTetUtil {

    fun createLocation(i: Int=0,lat: Double=0.0,lng: Double=0.0) =
        Location("place_id_$i","name_$i","region_$i","country_$i",lat,lng)

    //fun createLocationView
}