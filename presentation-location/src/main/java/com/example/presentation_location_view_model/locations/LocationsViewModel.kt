package com.example.presentation_location_view_model.locations

import io.reactivex.Observable


interface LocationsViewModel {
    fun init()
    fun getLocationsObservable(): Observable<List<LocationsView>>
    fun getStoredLocations()
    fun deleteLocation(placeId: String)
    fun updatePositions(locations: List<LocationsView>)
    fun updateLocations(locations: List<LocationsView>)
}