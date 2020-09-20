package com.example.ui_weather_2.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.presentation_weather_2.daily.WeatherDailyForecastViewModel
import com.example.ui_weather_2.R
import com.example.ui_weather_2.application.ApplicationFeatureWeather
import javax.inject.Inject

class FragmentWeatherDays : Fragment() {

    @Inject lateinit var viewModel: WeatherDailyForecastViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireActivity().application as ApplicationFeatureWeather).injectWeather(this)
        return inflater.inflate(R.layout.weather_day_fragment,container,false)
    }

    inner class WeatherDayPagerAdapter: FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        override fun getItem(position: Int): Fragment {
            val fragment = FragmentWeatherDay()
            return fragment
        }

        override fun getCount(): Int {
            return 0
        }

    }
}