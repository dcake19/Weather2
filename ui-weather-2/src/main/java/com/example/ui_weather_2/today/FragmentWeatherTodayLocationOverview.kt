package com.example.ui_weather_2.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.presentation_weather_2.LocationView
import com.example.ui_weather_2.R

class FragmentWeatherTodayLocationOverview : Fragment() {

    private var location: LocationView? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_today_pager_item,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.text_location).text = location?.placeName
    }

    fun setLocation(location: LocationView){
        this.location = location
    }

}