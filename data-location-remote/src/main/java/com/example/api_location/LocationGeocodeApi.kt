package com.example.api_location

import com.example.api_location.key.GEOCODING_API_KEY
import com.example.api_location.locations.AddressComponent
import com.example.api_location.locations.Result
import com.example.data_repository_location.LocationData
import com.example.data_repository_location.LocationDataNetwork
import io.reactivex.Single

class LocationGeocodeApi(private val retrofitLocationApi: RetrofitLocationApi): LocationDataNetwork{

    override fun getLocations(latitude: Double, longitude: Double): Single<LocationData> {
        return retrofitLocationApi.getLocationsByLatLong("$latitude,$longitude",GEOCODING_API_KEY)
            .map { locationResponse -> mapToLocationData(locationResponse.results[0]) }
    }

    private fun getLocationComponentName(addressComponents: List<AddressComponent>, types: List<String>): String{
        for (type in types){
            val name = addressComponents.firstOrNull { addressComponent -> addressComponent.types.contains(type) }?.longName
            if (name != null) return name
        }
        return ""
    }

    override fun getLocations(address: String): Single<LocationData> {
        return retrofitLocationApi.getLocationsByAddress(address,GEOCODING_API_KEY)
            .map { locationResponse -> mapToLocationData(locationResponse.results[0]) }
    }

    override fun getLocationsByPlaceId(placeId: String): Single<LocationData> {
        return retrofitLocationApi.getLocationsByPlaceId(placeId,GEOCODING_API_KEY)
            .map { locationResponse -> mapToLocationData(locationResponse.results[0]) }
    }

//    override fun getAutocompleteLocation(searchTerm: String): Single<LocationAutocompleteResponse> {
//        return retrofitLocationApi.getAutocompleteLocation(searchTerm,"geocode",GEOCODING_API_KEY)
//    }

    private fun mapToLocationData(location: Result): LocationData{
        val northEastLng = location.geometry.viewport.northeast.lng +
                if (location.geometry.viewport.northeast.lng < location.geometry.viewport.southwest.lng) 360 else 0

        return LocationData(location.placeId,
            getLocationComponentName(location.addressComponents, listOf("locality","postal_town","administrative_area_level_2")),
            getLocationComponentName(location.addressComponents,listOf("administrative_area_level_1")),
            getLocationComponentName(location.addressComponents,listOf("country")),
            location.geometry.location.lat,location.geometry.location.lng,
            location.geometry.viewport.northeast.lat,northEastLng,
            location.geometry.viewport.southwest.lat,location.geometry.viewport.southwest.lng)
    }
}