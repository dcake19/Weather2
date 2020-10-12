package com.example.ui_weather_2.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.presentation_weather_2.WeatherDayForecastView
import com.example.ui_weather_2.R
import com.example.ui_weather_2.mapWindDirection

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
            view.findViewById<TextView>(R.id.text_date).text = w.date
            view.findViewById<TextView>(R.id.text_high_temp).text = w.temperatureHigh
            view.findViewById<TextView>(R.id.text_low_temp).text = w.temperatureLow
            view.findViewById<TextView>(R.id.text_summary).text = w.description
            view.findViewById<TextView>(R.id.text_rain_quantity).text = w.rain
            view.findViewById<TextView>(R.id.text_sunrise_time).text = w.sunrise
            view.findViewById<TextView>(R.id.text_sunset_time).text = w.sunset
            view.findViewById<TextView>(R.id.text_wind_speed).text = w.windSpeed
            view.findViewById<TextView>(R.id.text_wind_direction).text = mapWindDirection(context,w.windDirection)
            view.findViewById<TextView>(R.id.text_cloud_coverage_pct).text = w.cloudCoverage
            view.findViewById<TextView>(R.id.text_pressure).text = w.pressure
            view.findViewById<TextView>(R.id.text_humidity_pct).text = w.humidity
        }
    }

    fun setWeather(weather: WeatherDayForecastView){
        this.weather = weather
    }

}