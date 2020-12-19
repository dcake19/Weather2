package com.example.presentation_location_view_model.search

import io.reactivex.Observable

interface SearchLocationViewModel {
    fun searchLocation(term: String)
    fun getSearchResultsObservable(): Observable<SearchResultsView>
    fun getLocationAddedObservable(): Observable<Boolean>
    fun getErrorObservable(): Observable<String>
    fun addLocation(placeId: String)
}