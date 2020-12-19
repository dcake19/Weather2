package com.example.presentation_location_view_model.map

import io.reactivex.Observable

interface MapLocationViewModel {
    fun getLocationsObservable(): Observable<List<MapLocationView>>
    fun getNewLocationObservable(): Observable<NewMapLocationView>
    fun getErrorObservable(): Observable<String>
    fun getStoredLocations()
    fun getNewLocationByPlaceId(placeId: String)
    fun getNewLocationBySearchTerm(term: String)
}