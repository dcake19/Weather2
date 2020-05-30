package com.example.data_storage_location

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data_storage_location.db.LocationDao
import com.example.data_storage_location.db.LocationDatabaseProvider
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*

class LocationCacheTests {

    //mock-maker-inline added to mock final classes

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var cache: LocationCache

    @Mock lateinit var dbProvider: LocationDatabaseProvider
    @Mock lateinit var dao: LocationDao

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        `when`(dbProvider.getLocationDao()).thenReturn(dao)
        cache = LocationCache(dbProvider)
    }

    @Test
    fun insert(){
        val locationData = CacheTestUtil.createLocationData()
        val locationEntity = CacheTestUtil.createLocationEntity()
        cache.insert(locationData)
        verify(dao).insert(locationEntity)
    }

    @Test
    fun delete(){
        val placeIds = (0..20).map { "place_id_$it" }
        cache.deleteLocation(placeIds)
        verify(dao).delete(placeIds)
    }

    @Test
    fun locationsBounding(){
        val locationEntities = (0..20).map { CacheTestUtil.createLocationEntity(it)}
        val locationDataExpected = (0..20).map { CacheTestUtil.createLocationData(it)}
        `when`(dao.getLocationsBoundingSingle(3.0,3.0)).thenReturn(Single.just(locationEntities))

        val locationDataActual = cache.getLocationsBounding(3.0,3.0).test().values()[0]

        assertThat(locationDataExpected, `is`(locationDataActual))
    }

    @Test
    fun locations(){
        val locationEntities = (0..20).map { CacheTestUtil.createLocationEntity(it)}
        val locationDataExpected = (0..20).map { CacheTestUtil.createLocationData(it)}
        `when`(dao.getLocationsSingle()).thenReturn(Single.just(locationEntities))

        val locationDataActual = cache.getLocations().test().values()[0]

        assertThat(locationDataExpected, `is`(locationDataActual))
    }

    @Test
    fun updateLocations(){
        val locationEntities = (0..20).map { CacheTestUtil.createLocationEntity(it)}
        `when`(dao.getLocations()).thenReturn(locationEntities)

        val placeIds = (10..20).map { "place_id_$it" } + (0..9).map { "place_id_$it" }
        cache.updateLocationsOrder(placeIds)

        val locationsEntitiesReordered = locationEntities.takeLast(11) + locationEntities.take(10)

        verify(dao).insert(locationsEntitiesReordered)
    }

}