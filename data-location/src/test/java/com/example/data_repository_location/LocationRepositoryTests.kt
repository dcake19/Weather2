package com.example.data_repository_location

import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class LocationRepositoryTests {

    private lateinit var repository: LocationsRepositoryImpl
    @Mock lateinit var locationDataNetwork: LocationDataNetwork
    @Mock lateinit var locationDataCache: LocationDataCache

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)

        repository = LocationsRepositoryImpl(locationDataNetwork, locationDataCache)
    }

    @Test
    fun getLocationLatLngEmptyCache(){
        val lat = 0.0
        val lng = 0.0
        val locationData = RepositoryTestUtil.createLocationData()

        `when`(locationDataCache.getLocationsBounding(lat, lng)).thenReturn(Single.just(listOf()))
        `when`(locationDataNetwork.getLocations(lat,lng))
            .thenReturn(Single.just(locationData))

        val location = repository.getLocation(lat,lng).test().values()[0]
        verify(locationDataCache).insert(locationData)

        assertThat(location, `is`(RepositoryTestUtil.createLocation()))
    }

    @Test
    fun getLocationsLatLngMultipleCacheEntries(){
        val lat = 0.0
        val lng = 0.0

        val cachedLocations = listOf(RepositoryTestUtil.createLocationData(1,-1.0,-1.0,5.0),
            RepositoryTestUtil.createLocationData(2,2.0,1.0,5.0))

        `when`(locationDataCache.getLocationsBounding(lat, lng)).thenReturn(Single.just(cachedLocations))

        val location = repository.getLocation(lat,lng).test().values()[0]
        verifyNoInteractions(locationDataNetwork)
        verify(locationDataCache, times(0)).insert(any()?:cachedLocations[0])

        assertThat(location, `is`(RepositoryTestUtil.createLocation(1,-1.0,-1.0)))
    }

}