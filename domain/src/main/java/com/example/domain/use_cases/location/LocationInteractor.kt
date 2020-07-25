package com.example.domain.use_cases.location

class LocationInteractor (private val locationsRepository: LocationsRepository)
    : LocationsRepository by locationsRepository {



}