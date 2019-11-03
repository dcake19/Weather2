package com.example.data_repository_location

import io.reactivex.Single

interface LocationDataNetwork {
    //fun getAutocompleteLocation(searchTerm: String): Single<LocationAutocompleteResponse>
    fun getLocations(latitude: Double, longitude: Double): Single<LocationData>
    fun getLocations(address: String): Single<LocationData>
    fun getLocationsByPlaceId(placeId: String): Single<LocationData>
}