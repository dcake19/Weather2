package com.example.ui_weather_2.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.presentation_weather_2.WeatherDayForecastView
import com.example.presentation_weather_2.daily.WeatherDailyForecastViewModel
import com.example.ui_weather_2.R
import com.example.ui_weather_2.application.ApplicationFeatureWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FragmentWeatherDays : Fragment() {

    @Inject lateinit var viewModel: WeatherDailyForecastViewModel

    private var initialDay = 0
    private var placeId = ""

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireActivity().application as ApplicationFeatureWeather).injectWeather(this)
        return inflater.inflate(R.layout.weather_day_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val a = arguments
        if (a!=null){
            val args = FragmentWeatherDaysArgs.fromBundle(a)
            initialDay = args.day
            placeId = args.placeId
        }

        view.findViewById<ImageButton>(R.id.button_back).setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWeatherDaysObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onWeatherReady(it) }
        viewModel.getWeatherDays(placeId)
    }

    private fun onWeatherReady(weather: List<WeatherDayForecastView>){
        val viewPager = view?.findViewById<ViewPager>(R.id.pager_weather_day)
        viewPager?.adapter = WeatherDayPagerAdapter(weather)
        viewPager?.currentItem = initialDay
    }

    inner class WeatherDayPagerAdapter(private val weather: List<WeatherDayForecastView>): FragmentPagerAdapter(
        childFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        override fun getItem(position: Int): Fragment {
            val fragment = FragmentWeatherDay()
            fragment.setWeather(weather[position])
            return fragment
        }

        override fun getCount(): Int {
            return weather.size
        }

    }
}