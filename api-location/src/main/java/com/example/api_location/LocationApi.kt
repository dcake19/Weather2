package com.example.api_location

import com.example.api_location.autocomplete.LocationAutocomplete
import com.example.api_location.locations.Locations
import io.reactivex.Single

interface LocationApi {
    fun getAutocompleteLocation(searchTerm: String): Single<LocationAutocomplete>
    fun getLocations(latitude: Double, longitude: Double): Single<Locations>
    fun getLocations(address: String): Single<Locations>
    fun getLocationsByPlaceId(placeId: String): Single<Locations>
}