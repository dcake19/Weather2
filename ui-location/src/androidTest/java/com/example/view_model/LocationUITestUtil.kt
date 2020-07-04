package com.example.view_model

import com.example.presentation_location_view_model.locations.LocationsView

object LocationUITestUtil {

    fun createLocation(i: Int=0) = LocationsView("place_id_$i","name_$i",
        "region_$i","country_$i")

}