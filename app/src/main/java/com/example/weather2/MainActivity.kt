package com.example.weather2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import com.example.open_weather.TestOpenWeather
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var weatherNavigationController: WeatherNavigationController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as ApplicationMain).injectMainActivity(this)
        weatherNavigationController.initialize(Navigation.findNavController(this, R.id.nav_host_fragment))

//        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        val navGraph = navController.navInflater.inflate(R.navigation.location_graph)
//        navGraph.startDestination = R.id.locations
//        navController.graph = navGraph


//        val navController = Navigation.findNavController(this,R.id.nav_host_fragment)
//        val navGraph = navController.navInflater.inflate(R.navigation.main_graph)
//       // navGraph.startDestination = R.id.location_graph
//        navGraph.startDestination = R.id.location_graph
//        navController.graph = navGraph

       // val ft = supportFragmentManager.beginTransaction()
      //  ft.replace(R.id.container, FragmentLocations())
      //  ft.commit()
        //val tds = TestDarkSky()
        //tds.get()

      // val tl = TestLocation()
        //tl.get(this)
        //DaggerMyComponent.create().inject(this)
       // locationViewModel.initialize(this)
        Log.v("","")
    }
}
