package com.example.weather2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.api_location.LocationApiImpl
import com.example.api_location.RetrofitClient
import com.example.api_location.RetrofitLocationApi
import com.example.api_location.TestLocation
import com.example.api_location.repository.LocationRepositoryImpl
import com.example.api_location.repository.db.LocationDaoProvider
import com.example.darkskiesapi.TestDarkSky

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val tds = TestDarkSky()
        //tds.get()

       val tl = TestLocation()
        tl.get(this)


    }
}
