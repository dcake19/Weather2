package com.example.weather2

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.open_weather.TestOpenWeather
import com.example.ui_weather_2.ForecastNavigation
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ForecastNavigation {

    lateinit var weatherNavigationController: WeatherNavigationController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as ApplicationMain).injectMainActivity(this)
        weatherNavigationController.initialize(Navigation.findNavController(this, R.id.nav_host_fragment))

        setUpToolbar()

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

    private fun setUpToolbar(){
        findViewById<Button>(R.id.button_locations).setOnClickListener {
            mainForecastClosed()
            Navigation.findNavController(this,R.id.nav_host_fragment)
                .navigate(R.id.action_weather_to_locations)
        }

        findViewById<ImageButton>(R.id.button_search).setOnClickListener {
            mainForecastClosed()
            val uri = Uri.parse("weatherApp://fragmentMap")
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(uri)
//            val navController = Navigation.findNavController(activity as Activity,R.id.nav_host_fragment)
//            val navGraph = navController.navInflater.inflate(R.navigation.main_graph)
//            val navGraph2 = navController.navInflater.inflate(R.navigation.location_graph)
//            navGraph2.startDestination = R.id.map
//            navGraph.addAll(navGraph2)
//            //navGraph.startDestination = R.id.map
//            navController.graph = navGraph
//            Navigation.findNavController(view).navigate(R.id.action_weather_to_map)
        }
    }

    override fun mainForecastOpened() {
        findViewById<ConstraintLayout>(R.id.top_bar).visibility = View.VISIBLE
    }

    override fun mainForecastClosed() {
        findViewById<ConstraintLayout>(R.id.top_bar).visibility = View.GONE
    }
}
