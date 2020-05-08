package com.example.presentation_location_view_model.map

import com.example.domain.Location

class MapLocationMapper {

    fun map(location: Location): MapLocationView{
        return MapLocationView(location.placeId,location.latitude,location.longitude)
    }

    fun map(locations: List<Location>): List<MapLocationView>{
        return locations.map { map(it) }
    }
}