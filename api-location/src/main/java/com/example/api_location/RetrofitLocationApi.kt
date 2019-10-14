package com.example.api_location

import com.example.api_location.autocomplete.LocationAutocomplete
import com.example.api_location.locations.Locations
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitLocationApi {

    @GET("geocode/json")
    fun getLocationsByAddress(@Query("address") address: String,
                              @Query("key") key: String): Single<Locations>


    @GET("geocode/json")
    fun getLocationsByLatLong(@Query("latlng") latLng: String,
                     @Query("key") key: String): Single<Locations>

    @GET("geocode/json")
    fun getLocationsByPlaceId(@Query("place_id") placeId: String,
                              @Query("key") key: String): Single<Locations>

    @GET("place/autocomplete/json")
    fun getAutocompleteLocation(@Query("input") input: String,
                                @Query("types") types: String,
                                @Query("key") key: String): Single<LocationAutocomplete>

}