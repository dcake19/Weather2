package com.example.data_repository_location

import com.example.domain.Location
import com.example.domain.LocationsRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocationsRepositoryImpl(private val locationDataNetwork: LocationDataNetwork,
                              private val locationDataCache: LocationDataCache): LocationsRepository {

    override fun getLocation(latitude: Double, longitude: Double): Single<Location> {
        return locationDataCache
            .getLocationsBounding(latitude,longitude)
            .subscribeOn(Schedulers.io())
            .flatMap { locations ->
                if (locations.isEmpty())
                    locationDataNetwork.getLocations(latitude, longitude)
                    .doOnSuccess { locationData -> locationDataCache.insert(locationData) }
                else
                    Single.just(getNearestLocation(latitude, longitude, locations))}
            .map { locationData -> mapToLocation(locationData) }
    }

    private fun getNearestLocation(latitude: Double, longitude: Double,
                                   locations: List<LocationData>): LocationData {
        return locations.sortedBy { Math.pow(latitude - it.latitudeNorthEast,2.0)
            + Math.pow(latitude - it.latitudeSouthWest,2.0)
            + Math.pow(longitude - it.longitudeNorthEast,2.0)
            + Math.pow(longitude - it.longitudeSouthWest,2.0) }.first()
    }

    override fun addLocation(placeId: String): Completable {
        return locationDataNetwork
            .getLocationsByPlaceId(placeId)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { locationData -> locationDataCache.insert(locationData) }
            .flatMapCompletable { Completable.complete() }
    }

    override fun getStoredLocations(): Single<List<Location>> {
        return locationDataCache
            .getLocations()
            .map { locations -> locations.map { locationData -> mapToLocation(locationData) }}
    }

    override fun deleteLocations(placeIds: List<String>) {
        locationDataCache.deleteLocation(placeIds)
    }

    override fun updateLocationsOrder(locations: List<String>) {
        locationDataCache.updateLocationsOrder(locations)
    }

    private fun mapToLocation(locationData: LocationData): Location{
        return Location(locationData.placeId,-1,locationData.name,
            locationData.region, locationData.country,locationData.latitude,locationData.longitude)
    }

    override fun getLocationByPlaceId(placeId: String): Single<Location> {
        return locationDataNetwork.getLocationsByPlaceId(placeId)
            .map { locationData -> mapToLocation(locationData) }
    }

    override fun getLocationByName(name: String): Single<Location> {
        return locationDataNetwork.getLocations(name)
            .map { locationData -> mapToLocation(locationData) }
    }
}