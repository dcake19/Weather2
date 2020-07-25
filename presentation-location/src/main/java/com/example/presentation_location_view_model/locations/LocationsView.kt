package com.example.presentation_location_view_model.locations

import com.example.domain.use_cases.location.Location

data class LocationsView(val placeId: String, val name: String, val region: String, val country: String){

    constructor(location: Location) : this(location.placeId,
        location.name,location.region, location.country)

    var selected = false
}