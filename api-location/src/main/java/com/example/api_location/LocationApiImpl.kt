package com.example.api_location

import com.example.api_location.autocomplete.LocationAutocomplete
import com.example.api_location.key.GEOCODING_API_KEY
import com.example.api_location.locations.Locations
import io.reactivex.Single

class LocationApiImpl(private val retrofitLocationApi: RetrofitLocationApi): LocationApi{

    override fun getLocations(latitude: Double, longitude: Double): Single<Locations> {
        return retrofitLocationApi.getLocationsByLatLong("$latitude,$longitude",GEOCODING_API_KEY)
    }

    override fun getLocations(address: String): Single<Locations> {
        return retrofitLocationApi.getLocationsByAddress(address,GEOCODING_API_KEY)
    }

    override fun getLocationsByPlaceId(placeId: String): Single<Locations> {
        return retrofitLocationApi.getLocationsByPlaceId(placeId,GEOCODING_API_KEY)
    }

    override fun getAutocompleteLocation(searchTerm: String): Single<LocationAutocomplete> {
        return retrofitLocationApi.getAutocompleteLocation(searchTerm,"geocode",GEOCODING_API_KEY)
    }
}