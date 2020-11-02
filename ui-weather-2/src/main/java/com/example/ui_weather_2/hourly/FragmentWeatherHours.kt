package com.example.ui_weather_2.hourly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.presentation_weather_2.WeatherHourForecastView
import com.example.presentation_weather_2.hourly.WeatherHourlyForecastViewModel
import com.example.ui_weather_2.R
import com.example.ui_weather_2.application.ApplicationFeatureWeather
import com.example.ui_weather_2.daily.FragmentWeatherDaysArgs
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FragmentWeatherHours : Fragment() {

    @Inject lateinit var viewModel: WeatherHourlyForecastViewModel
    private var initialHour = 0

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireActivity().application as ApplicationFeatureWeather).injectWeather(this)
        return inflater.inflate(R.layout.weather_hour_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val a = arguments
        if (a!=null){
            val args = FragmentWeatherDaysArgs.fromBundle(a)
            initialHour = args.day
        }

        view.findViewById<ImageButton>(R.id.button_back).setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWeatherHoursObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onWeatherReady(it) }
        viewModel.getWeatherHours()
    }

    private fun onWeatherReady(weather: List<WeatherHourForecastView>){
        val viewPager = view?.findViewById<ViewPager>(R.id.pager_weather_hour)
        viewPager?.adapter = WeatherHourPagerAdapter(weather)
        viewPager?.currentItem = initialHour
    }

    inner class WeatherHourPagerAdapter(private val weather: List<WeatherHourForecastView>): FragmentPagerAdapter(
        childFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        override fun getItem(position: Int): Fragment {
            val fragment = FragmentWeatherHour()
            fragment.setWeather(weather[position])
            return fragment
        }

        override fun getCount(): Int {
            return weather.size
        }

    }
}