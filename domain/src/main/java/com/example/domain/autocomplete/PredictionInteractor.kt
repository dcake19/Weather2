package com.example.domain.autocomplete

import com.example.domain.LocationsRepository

class PredictionInteractor(private val autoCompleteRepository: AutoCompleteRepository,
                           private val locationsRepository: LocationsRepository)
    : AutoCompleteRepository by autoCompleteRepository, LocationsRepository by locationsRepository