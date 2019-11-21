package com.example.data_repository_location.auto_complete

import io.reactivex.Single

interface AutoCompleteDataNetwork {

    fun getAutocompleteLocation(searchTerm: String): Single<List<LocationPredictionData>>

}