package com.example.api_location

import com.example.api_location.autocomplete.LocationAutocompleteResponse
import com.example.api_location.locations.LocationResponse
import io.reactivex.Single

interface LocationApi {
    fun getAutocompleteLocation(searchTerm: String): Single<LocationAutocompleteResponse>
    fun getLocations(latitude: Double, longitude: Double): Single<LocationResponse>
    fun getLocations(address: String): Single<LocationResponse>
    fun getLocationsByPlaceId(placeId: String): Single<LocationResponse>
}