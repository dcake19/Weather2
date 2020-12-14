package com.example.data_storage_location

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data_storage_location.db.LocationDao
import com.example.data_storage_location.db.LocationDatabase
import com.example.data_storage_location.db.LocationEntity
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*

@RunWith(AndroidJUnit4::class)
class LocationDaoTest {

    private lateinit var db: LocationDatabase
    private lateinit var dao: LocationDao

    @Before
    fun init(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,LocationDatabase::class.java).build()
        dao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun singleInsertion(){
        val location = TestUtil.createLocation()
        dao.insert(location)

        val locations = dao.getLocations()

        assertThat(locations.size, `is`(1))
        assertThat(locations[0], `is`(location))
    }

    @Test
    fun singleInsertionReplace(){
        val location = TestUtil.createLocation(0)
        dao.insert(location)
        val locationNew = TestUtil.createLocation(0,1.0,1.0)
        dao.insert(locationNew)

        val locations = dao.getLocations()

        assertThat(locations.size, `is`(1))
        assertThat(locations[0], `is`(locationNew))
    }

    @Test
    fun multipleInsertions(){
        val locationsIn = (0..20).map { TestUtil.createLocation(it) }
        dao.insert(locationsIn)

        val locationsOut = dao.getLocations()

        assertThat(locationsOut, `is`(locationsIn))
    }

    @Test
    fun multipleInsertionsReplace(){
        val locationsIn = (0..20).map { TestUtil.createLocation(it) }
        dao.insert(locationsIn)

        val locationsInNew = locationsIn.takeLast(11) + locationsIn.take(10)
        dao.insert(locationsInNew)

        val locationsOut = dao.getLocations()

        assertThat(locationsOut, `is`(locationsInNew))
    }

    @Test
    fun getLocationsSingle(){
        val locationsIn = (0..20).map { TestUtil.createLocation(it) }
        dao.insert(locationsIn)

        val locationsSingle = dao.getLocationsSingle()

        assertThat(locationsSingle.test().values(), `is`(Single.just(locationsIn).test().values()))
    }

    @Test
    fun getLocationByPlaceId1(){
        val locationsIn = (0..20).map { TestUtil.createLocation(it) }
        dao.insert(locationsIn)

        val locationMaybe = dao.getLocation(locationsIn[0].placeId)

        assertThat(locationMaybe.test().values(), `is`(Single.just(locationsIn[0]).test().values()))
    }

    @Test
    fun numberOfLocations(){
        val locationsIn = (0..20).map { TestUtil.createLocation(it) }
        dao.insert(locationsIn)

        assertThat(dao.getNumberOfLocations(), `is`(locationsIn.size))
    }

    @Test
    fun deleteSingle(){
        val locationsIn = (0..20).map { TestUtil.createLocation(it) }
        dao.insert(locationsIn)
        val delete = locationsIn[5]
        dao.delete(delete)

        val locationsOut = dao.getLocations()

        assertThat(locationsOut, `is`(locationsIn.filterNot { it == delete }))
    }

    @Test
    fun deleteMultiple(){
        val locationsIn = (0..20).map { TestUtil.createLocation(it) }
        dao.insert(locationsIn)
        val delete = locationsIn.subList(4,12).map { it.placeId }
        dao.delete(delete)

        val locationsOut = dao.getLocations()

        assertThat(locationsOut, `is`(locationsIn.filterNot { delete.contains(it.placeId) }))
    }

    @Test
    fun locationBounding(){
        val locationsIn = mutableListOf<LocationEntity>()

        for(i in (1..5))
            for(j in(1..5))
                locationsIn.add(TestUtil.createLocation("$i,$j",i.toDouble(),j.toDouble(),1.0))

        dao.insert(locationsIn)

        val locationsBounding = dao.getLocationsBoundingSingle(3.0,3.0).test().values()[0]

        val locationsExpected = mutableListOf<LocationEntity>()

        for(i in (2..4))
            for(j in(2..4))
                locationsExpected.add(TestUtil.createLocation("$i,$j",i.toDouble(),j.toDouble(),1.0))

        assertThat(locationsBounding, `is`(locationsExpected.toList()))
    }

}