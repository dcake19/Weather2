package com.example.data_storage_location

import com.example.data_repository_location.LocationData
import com.example.data_repository_location.LocationDataCache
import com.example.data_storage_location.db.LocationDatabaseProvider
import com.example.data_storage_location.db.LocationEntity
import io.reactivex.Single

class LocationCache(private val locationDatabase: LocationDatabaseProvider) : LocationDataCache {

   private val locationDao = locationDatabase.getLocationDao()

    override fun getLocationsBounding(lat: Double, lng: Double): Single<List<LocationData>> {
        return locationDao.getLocationsBoundingSingle(lat, lng)
            .map { locations -> locations.map { mapToLocationData(it) } }
    }

    override fun getLocations(): Single<List<LocationData>> {
        return locationDao.getLocationsSingle()
            .map { locations -> locations.map { mapToLocationData(it) } }
    }

    override fun deleteLocation(placeId: String) {
        locationDao.delete(placeId)
    }

    override fun updateLocations(locations: List<String>){
        val currentCachedLocations = locationDao.getLocations()
        val updatedCachedLocations = mutableListOf<LocationEntity>()
        for (location in locations){
            val locationEntity = currentCachedLocations.firstOrNull { it.placeId == location }
            if (locationEntity!=null) updatedCachedLocations.add(locationEntity)
        }

        locationDao.insert(updatedCachedLocations)
    }

    override fun insert(locationData: LocationData): LocationData {
        locationDao.insert(LocationEntity(locationData))
        return locationData
    }

    private fun mapToLocationData(locationEntity: LocationEntity): LocationData{
        return LocationData(locationEntity.placeId,locationEntity.locality?:"",
            locationEntity.adminArea1?:"",locationEntity.country?:"",
            locationEntity.latitude,locationEntity.longitude,
            locationEntity.latitudeNorthEast,locationEntity.longitudeNorthEast,
            locationEntity.latitudeSouthWest,locationEntity.longitudeSouthWest)
    }
}