package com.example.presentation_location_view_model.locations


interface LocationsViewModel {
    fun init()
    fun getStoredLocations()
    fun deleteLocation(placeId: String)
}