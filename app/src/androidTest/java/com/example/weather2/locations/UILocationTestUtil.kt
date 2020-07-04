package com.example.weather2.locations

import com.example.presentation_location_view_model.locations.LocationsView

object UILocationTestUtil {

    fun createLocation(i: Int=0) = LocationsView("place_id_$i","name_$i",
        "region_$i","country_$i")

}