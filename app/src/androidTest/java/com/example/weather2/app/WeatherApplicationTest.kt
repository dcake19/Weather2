package com.example.weather2.app

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.application.ApplicationFeatureLocation
import com.example.locations.FragmentLocations
import com.example.weather2.ApplicationMain
import com.example.weather2.MainActivity
import com.example.weather2.R
import com.example.weather2.WeatherNavigationController
import com.example.weather2.locations.ViewModelProvider
//import com.example.utils.ViewModelEmitter

class WeatherApplicationTest: Application(), ApplicationMain,ApplicationFeatureLocation {

    override fun injectMainActivity(mainActivity: MainActivity) {
        mainActivity.weatherNavigationController = object : WeatherNavigationController {
            override fun initialize(navController: NavController) {
                val navGraph = navController.navInflater.inflate(R.navigation.location_graph)
                navGraph.startDestination = R.id.locations
                navController.graph = navGraph
            }
        }
    }

    override fun injectLocation(fragment: Fragment) {
        if (fragment is FragmentLocations) fragment.viewModel = ViewModelProvider.locationsViewModel
    }

}