package com.example.presentation_location_view_model.locations

import io.reactivex.Observable


interface LocationsViewModel {
    fun init()
    fun getLocationsObservable(): Observable<List<LocationsPresentation>>
    fun getStoredLocations()
    fun deleteLocation(placeId: String)
}