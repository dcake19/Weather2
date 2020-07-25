package com.example.domain.use_cases.autocomplete

import io.reactivex.Single

interface AutoCompleteRepository {
    fun searchLocations(term: String): Single<Predictions>
}