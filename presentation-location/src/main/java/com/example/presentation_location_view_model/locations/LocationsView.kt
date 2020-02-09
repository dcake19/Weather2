package com.example.presentation_location_view_model.locations

import com.example.domain.Location

data class LocationsView(val placeId: String, val position: Int, val name: String, val region: String, val country: String){

    constructor(location: Location) : this(location.placeId,location.position,
        location.name,location.region, location.country)
}