package com.example.presentation_location

import com.example.domain.Location
import com.example.presentation_location_view_model.map.MapLocationView
import com.example.presentation_location_view_model.map.NewMapLocationView

object PresentationTetUtil {

    fun createLocation(i: Int=0,lat: Double=0.0,lng: Double=0.0) =
        Location("place_id_$i","name_$i","region_$i","country_$i",lat,lng)

    fun createMapLocationView(i: Int=0,lat: Double=0.0,lng: Double=0.0)
            = MapLocationView("place_id_$i",lat,lng)

    fun createNewMapLocationView(i: Int=0,lat: Double=0.0,lng: Double=0.0)
            = NewMapLocationView("place_id_$i",lat,lng,"name_$i","region_$i, country_$i")

}