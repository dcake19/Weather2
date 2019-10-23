package com.example.weather2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.api_location.LocationApiImpl
import com.example.api_location.RetrofitClient
import com.example.api_location.RetrofitLocationApi
import com.example.api_location.TestLocation
import com.example.api_location.repository.LocationRepository
import com.example.api_location.repository.LocationRepositoryImpl
import com.example.api_location.repository.db.LocationDaoProvider
import com.example.api_location.repository.db.LocationEntity
import com.example.darkskiesapi.TestDarkSky
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var locationRepository: LocationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val tds = TestDarkSky()
        //tds.get()

      // val tl = TestLocation()
        //tl.get(this)
        DaggerMyComponent.create().inject(this)

        locationRepository.initialize(this)
    }
}
