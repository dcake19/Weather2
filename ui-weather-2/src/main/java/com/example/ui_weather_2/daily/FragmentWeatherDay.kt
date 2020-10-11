package com.example.ui_weather_2.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.presentation_weather_2.WeatherDayForecastView
import com.example.ui_weather_2.R

class FragmentWeatherDay: Fragment() {

    private var weather: WeatherDayForecastView? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_day_pager_item,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val w  = weather
        if(w!=null){
            view.findViewById<TextView>(R.id.text_rain_quantity).text = w.rain
        }
    }

    fun setWeather(weather: WeatherDayForecastView){
        this.weather = weather
    }

}