package com.example.ui_weather_2.hourly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.ui_weather_2.R

class FragmentWeatherHours : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_hour_fragment,container,false)
    }

    inner class WeatherHourPagerAdapter: FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        override fun getItem(position: Int): Fragment {
            val fragment = FragmentWeatherHour()
            return fragment
        }

        override fun getCount(): Int {
            return 0
        }

    }
}