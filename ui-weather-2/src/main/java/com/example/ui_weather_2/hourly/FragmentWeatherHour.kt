package com.example.ui_weather_2.hourly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.presentation_weather_2.WeatherHourForecastView
import com.example.ui_weather_2.R
import com.example.ui_weather_2.mapToImageResource
import com.example.ui_weather_2.mapWindDirection

class FragmentWeatherHour: Fragment() {

    private var weather: WeatherHourForecastView? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_hour_pager_item,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val w = weather
        if (w!=null){
            view.findViewById<TextView>(R.id.text_time).text = w.time
            view.findViewById<TextView>(R.id.text_temp).text = w.temperature
            view.findViewById<TextView>(R.id.text_temp_feel).text = context?.getString(R.string.feels_like,w.feelsLike)
            view.findViewById<TextView>(R.id.text_summary).text = w.description
            view.findViewById<TextView>(R.id.text_rain_quantity).text = w.rain
            view.findViewById<TextView>(R.id.text_wind_speed).text = w.windSpeed
            view.findViewById<TextView>(R.id.text_wind_direction).text = mapWindDirection(context,w.windDirection)
            view.findViewById<TextView>(R.id.text_cloud_coverage_pct).text = w.cloudCoverage

            val d = mapToImageResource(w.weatherId)
            view.findViewById<ImageView>(R.id.image_weather_icon)
                .setImageResource(d)
        }
    }

    fun setWeather(weather: WeatherHourForecastView){
        this.weather = weather
    }
}