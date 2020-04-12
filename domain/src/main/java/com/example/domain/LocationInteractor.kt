package com.example.domain

class LocationInteractor (private val locationsRepository: LocationsRepository)
    : LocationsRepository by locationsRepository {



}