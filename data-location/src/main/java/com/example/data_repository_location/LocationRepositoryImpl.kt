package com.example.data_repository_location

import com.example.api_location.LocationApi
import com.example.api_location.locations.AddressComponent
import com.example.api_location.locations.LocationResponse
import com.example.api_location.locations.Result
import com.example.data_storage_location.db.LocationDatabaseProvider
import com.example.data_storage_location.db.LocationEntity
import com.example.domain.Location
import com.example.domain.LocationRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocationRepositoryImpl(private val locationApi: LocationApi,
                             private val locationDatabase: LocationDatabaseProvider):
    LocationRepository {

    private val locationDao = locationDatabase.getLocationDao()

    override fun getLocation(latitude: Double, longitude: Double): Single<Location> {
        return locationDao.getLocationsBounding(latitude,longitude)
                    .flatMap { locations -> if (locations.isEmpty()) flatMapSaveLocation(locationApi.getLocations(latitude, longitude))
                    else Single.just(getNearestLocation(latitude,longitude,locations)) }
                    .map { locationEntity ->  mapToLocation(locationEntity) }
                    .subscribeOn(Schedulers.io())
    }

    private fun getNearestLocation(latitude: Double, longitude: Double,locationEntities: List<LocationEntity>): LocationEntity{
        return locationEntities.sortedBy { Math.pow(latitude - it.latitudeNorthEast,2.0)
                    + Math.pow(latitude - it.latitudeSouthWest,2.0)
                    + Math.pow(longitude - it.longitudeNorthEast,2.0)
                    + Math.pow(longitude - it.longitudeSouthWest,2.0) }.first()
    }

    override fun getLocation(placeId: String): Single<Location> {
        return flatMapSaveLocation(locationApi.getLocationsByPlaceId(placeId))
                    .map { locationEntity ->  mapToLocation(locationEntity) }
                    .subscribeOn(Schedulers.io())
    }

    private fun flatMapSaveLocation(single: Single<LocationResponse>): Single<LocationEntity>{
        return single.map { locationResponse -> locationResponse.results[0] }
                    .map { location -> mapToLocationEntity(location)}
                    .doOnSuccess { locationEntity ->  locationDao.insert(locationEntity) }
            }

    private fun mapToLocation(locationEntity: LocationEntity): Location{
        return Location(locationEntity.placeId,locationEntity.locality?:"",
                    locationEntity.adminArea1?:"",locationEntity.country?:"",
                    locationEntity.latitude, locationEntity.longitude)
    }

    private fun mapToLocationEntity(location: Result): LocationEntity{
        val northEastLng = location.geometry.viewport.northeast.lng +
                        if (location.geometry.viewport.northeast.lng < location.geometry.viewport.southwest.lng) 360 else 0

        return LocationEntity(location.placeId,
                    getLocationComponentName(location.addressComponents, listOf("locality","administrative_area_level_2")),
                    getLocationComponentName(location.addressComponents,"administrative_area_level_1"),
                    getLocationComponentName(location.addressComponents,"country"),
                    location.geometry.location.lat,location.geometry.location.lng,
                    location.geometry.viewport.northeast.lat,northEastLng,
                    location.geometry.viewport.southwest.lat,location.geometry.viewport.southwest.lng)
    }

    private fun getLocationComponentName(addressComponents: List<AddressComponent>, type: String): String?{
        return addressComponents.firstOrNull { addressComponent -> addressComponent.types.contains(type) }?.longName
    }

    private fun getLocationComponentName(addressComponents: List<AddressComponent>, types: List<String>): String?{
        for (type in types){
            val name = addressComponents.firstOrNull { addressComponent -> addressComponent.types.contains(type) }?.longName
            if (name != null) return name
        }
        return null
    }

    override fun getStoredLocations(): Single<List<Location>> {
        return locationDao.getLocations()
            .map { locationEntities -> locationEntities.map { le -> mapToLocation(le) } }
            .subscribeOn(Schedulers.io())
    }

    override fun deleteLocation(placeId: String) {
        locationDao.delete(placeId)
    }

}