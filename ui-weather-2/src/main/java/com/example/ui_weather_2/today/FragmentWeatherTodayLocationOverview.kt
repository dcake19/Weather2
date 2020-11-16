package com.example.ui_weather_2.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation_weather_2.LocationView
import com.example.presentation_weather_2.WeatherTodayView
import com.example.ui_weather_2.R
import com.example.ui_weather_2.mapToImageResource
import com.example.ui_weather_2.mapWindDirection

class FragmentWeatherTodayLocationOverview : Fragment() {

    private var location: LocationView? = null
    private var forecast: WeatherTodayView? = null

    private lateinit var hourlyAdapter: WeatherTodayHourlyAdapter
    private lateinit var dailyAdapter: WeatherTodayDailyAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_today_pager_item,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()
        createRecyclerViews(view)

        view.findViewById<TextView>(R.id.text_location).text = location?.placeName

        displayForecast()
    }

    fun setLocation(location: LocationView){
        this.location = location
    }

    fun setForecast(forecast: WeatherTodayView?){
        this.forecast = forecast
        displayForecast()
    }

    private fun setAdapters(){
        val l = location
        if (l!=null) {
            if (!::hourlyAdapter.isInitialized) hourlyAdapter = WeatherTodayHourlyAdapter(l.placeId)
            if (!::dailyAdapter.isInitialized) dailyAdapter = WeatherTodayDailyAdapter()
        }
    }

    private fun createRecyclerViews(view: View){
        val listHourly = view.findViewById<RecyclerView>(R.id.list_hourly)
        val listDaily = view.findViewById<RecyclerView>(R.id.list_daily)

        listDaily.layoutManager =  LinearLayoutManager(activity,
            RecyclerView.HORIZONTAL, false)
        listHourly.layoutManager = LinearLayoutManager(activity,
            RecyclerView.HORIZONTAL, false)

        listHourly.adapter = hourlyAdapter
        listDaily.adapter = dailyAdapter
    }

    private fun displayForecast(){
        val f = forecast

        if (f!=null){
            view?.findViewById<ConstraintLayout>(R.id.layout_weather_forecast)?.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.text_date_time)?.text = f.dateTime
            view?.findViewById<TextView>(R.id.text_temp)?.text = f.temperature
            view?.findViewById<TextView>(R.id.text_temp_feel)?.text = f.feelsLike
            view?.findViewById<TextView>(R.id.text_summary)?.text = f.description
            view?.findViewById<TextView>(R.id.text_rain_quantity)?.text = f.rain
            view?.findViewById<TextView>(R.id.text_sunrise_time)?.text = f.sunrise
            view?.findViewById<TextView>(R.id.text_sunset_time)?.text = f.sunset
            view?.findViewById<TextView>(R.id.text_wind_speed)?.text = f.windSpeed
            view?.findViewById<TextView>(R.id.text_wind_direction)?.text =  mapWindDirection(context,f.windDirection)
            view?.findViewById<TextView>(R.id.text_cloud_coverage_pct)?.text = f.cloudCoverage
            view?.findViewById<TextView>(R.id.text_pressure)?.text = f.pressure
            view?.findViewById<TextView>(R.id.text_humidity_pct)?.text = f.humidity

            val d = mapToImageResource(f.weatherId)
            view?.findViewById<ImageView>(R.id.image_weather_icon)?.setImageResource(d)

            if (::hourlyAdapter.isInitialized && ::dailyAdapter.isInitialized ){//&& f.placeId=="placeId_5") {
                hourlyAdapter.items = f.hourly
                dailyAdapter.items = f.daily
            }
        }
    }

}