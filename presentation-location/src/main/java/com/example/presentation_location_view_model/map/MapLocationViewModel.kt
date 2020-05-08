package com.example.presentation_location_view_model.map

import io.reactivex.Observable

interface MapLocationViewModel {
    fun getLocationsObservable(): Observable<List<MapLocationView>>
    fun getNewLocationObservable(): Observable<MapLocationView>
    fun getStoredLocations()
    fun getNewLocation(placeId: String)
}