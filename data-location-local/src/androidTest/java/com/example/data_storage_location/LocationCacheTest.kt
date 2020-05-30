package com.example.data_storage_location

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data_storage_location.db.LocationDao
import com.example.data_storage_location.db.LocationDatabase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationCacheTest {

    private lateinit var db: LocationDatabase
    private lateinit var dao: LocationDao

    @Before
    fun init(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LocationDatabase::class.java).build()
        dao = db.locationDao()
    }

    @Test
    fun testSingleInsertion(){
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun test2(){

    }


}