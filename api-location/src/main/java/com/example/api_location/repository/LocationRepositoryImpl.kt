package com.example.api_location.repository

import android.content.Context
import com.example.api_location.LocationApi
import com.example.api_location.locations.AddressComponent
import com.example.api_location.locations.LocationResponse
import com.example.api_location.locations.Result
import com.example.api_location.repository.db.LocationDao
import com.example.api_location.repository.db.LocationDaoProvider
import com.example.api_location.repository.db.LocationEntity
import io.reactivex.Single

class LocationRepositoryImpl(private val locationApi: LocationApi,
                             private val locationDaoProvider: LocationDaoProvider): LocationRepository {

    private lateinit var locationDao: LocationDao

    override fun initialize(context: Context) {
        locationDao = locationDaoProvider.getLocationDao(context)
    }

    override fun getLocation(latitude: Double, longitude: Double): Single<LocationEntity> {
        return locationDao.getLocationsBounding(latitude,longitude)
            .flatMap { locations -> if (locations.isEmpty()) map(locationApi.getLocations(latitude, longitude))
            else Single.just(getNearestLocation(latitude,longitude,locations)) }
    }

    private fun getNearestLocation(latitude: Double, longitude: Double,locationEntities: List<LocationEntity>): LocationEntity{
        return locationEntities.sortedBy { Math.pow(latitude - it.latitudeNorthEast,2.0)
            + Math.pow(latitude - it.latitudeSouthWest,2.0)
            + Math.pow(longitude - it.longitudeNorthEast,2.0)
            + Math.pow(longitude - it.longitudeSouthWest,2.0) }.first()
    }

    override fun getLocation(placeId: String): Single<LocationEntity> {
        return map(locationApi.getLocationsByPlaceId(placeId))
    }

    private fun map(single: Single<LocationResponse>): Single<LocationEntity>{
        return single.map { locationResponse -> locationResponse.results[0] }
            .map { location -> mapToLocationEntity(location)}
            .doOnSuccess { locationEntity ->  locationDao.insert(locationEntity) }
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

    override fun getStoredLocations(): Single<List<LocationEntity>> {
        return locationDao.getLocations()
    }

    override fun deleteLocation(locationEntity: LocationEntity) {
        locationDao.delete(locationEntity)
    }

}