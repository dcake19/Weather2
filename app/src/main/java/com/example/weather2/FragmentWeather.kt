package com.example.weather2

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class FragmentWeather : Fragment() {

    //@Inject
    //lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        //(activity!!.application as WeatherApplication).inject(this)
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.init()


        view.findViewById<Button>(R.id.button_forecast).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_weather_to_forecast)
        }


        view.findViewById<Button>(R.id.button_locations).setOnClickListener {
           Navigation.findNavController(view).navigate(R.id.action_weather_to_locations)
        }

        view.findViewById<Button>(R.id.button_map).setOnClickListener {
            val uri = Uri.parse("weatherApp://fragmentMap")
            Navigation.findNavController(view).navigate(uri)
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
}