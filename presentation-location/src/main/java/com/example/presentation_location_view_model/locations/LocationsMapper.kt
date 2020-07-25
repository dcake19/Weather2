package com.example.presentation_location_view_model.locations

import com.example.domain.use_cases.location.Location

class LocationsMapper {

    fun map(locations: List<Location>): List<LocationsView>{
        return locations.map {
            LocationsView(it.placeId,it.name,it.region,it.country) }
    }

}