package com.example.domain.use_cases.autocomplete

import com.example.domain.use_cases.location.LocationsRepository

class PredictionInteractor(private val autoCompleteRepository: AutoCompleteRepository,
                           private val locationsRepository: LocationsRepository
)
    : AutoCompleteRepository by autoCompleteRepository, LocationsRepository by locationsRepository