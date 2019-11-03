package com.example.api_location

import com.example.api_location.autocomplete.LocationAutocompleteResponse
import com.example.api_location.key.GEOCODING_API_KEY
import com.example.api_location.locations.LocationResponse
import io.reactivex.Single

class LocationApiImpl(private val retrofitLocationApi: RetrofitLocationApi): LocationApi{

    override fun getLocations(latitude: Double, longitude: Double): Single<LocationResponse> {
        return retrofitLocationApi.getLocationsByLatLong("$latitude,$longitude",GEOCODING_API_KEY)
    }

    override fun getLocations(address: String): Single<LocationResponse> {
        return retrofitLocationApi.getLocationsByAddress(address,GEOCODING_API_KEY)
    }

    override fun getLocationsByPlaceId(placeId: String): Single<LocationResponse> {
        return retrofitLocationApi.getLocationsByPlaceId(placeId,GEOCODING_API_KEY)
    }

    override fun getAutocompleteLocation(searchTerm: String): Single<LocationAutocompleteResponse> {
        return retrofitLocationApi.getAutocompleteLocation(searchTerm,"geocode",GEOCODING_API_KEY)
    }
}