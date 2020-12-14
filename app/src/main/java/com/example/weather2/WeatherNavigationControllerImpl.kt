package com.example.weather2

import androidx.navigation.NavController

class WeatherNavigationControllerImpl: WeatherNavigationController {
    override fun initialize(navController: NavController){
        val navGraph = navController.navInflater.inflate(R.navigation.main_graph)
        navGraph.startDestination = R.id.weather_graph
        navController.graph = navGraph
    }
}