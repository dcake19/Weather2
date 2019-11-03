package com.example.domain

class LocationInteractor(private val locationRepository: LocationRepository): LocationRepository by locationRepository {
}