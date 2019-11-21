package com.example.domain.autocomplete

import io.reactivex.Single

interface AutoCompleteRepository {
    fun searchLocations(term: String): Single<Predictions>
}