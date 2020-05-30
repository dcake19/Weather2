package com.example.presentation_location_view_model.locations

import io.reactivex.Observable


interface LocationsViewModel {
    fun init()
    fun getLocationsObservable(): Observable<List<LocationsView>>
    fun getStoredLocations()
    fun deleteLocations(locations: List<LocationsView>)
    fun updateLocationsOrder(locations: List<LocationsView>)
}