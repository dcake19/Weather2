package com.example.ui_weather_2.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.presentation_weather_2.LocationView
import com.example.presentation_weather_2.WeatherTodayView
import com.example.presentation_weather_2.main.WeatherMainForecastViewModel
import com.example.ui_weather_2.ForecastNavigation
import com.example.ui_weather_2.R
import com.example.ui_weather_2.application.ApplicationFeatureWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FragmentWeatherTodayOverview: Fragment() {

    @Inject lateinit var viewModel: WeatherMainForecastViewModel
    private lateinit var pagerAdapter: WeatherTodayPagerAdapter

    private var initialPlaceId: String? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireActivity().application as ApplicationFeatureWeather).injectWeather(this)
        return inflater.inflate(R.layout.weather_today_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            arguments?.let{
                val args = FragmentWeatherTodayOverviewArgs.fromBundle(it)
                initialPlaceId = args.placeId
            }

    }

    override fun onStart() {
        super.onStart()
        (activity as ForecastNavigation).mainForecastOpened()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocationsObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onLocationsReady(it) }
        viewModel.getWeatherObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pagerAdapter.addWeatherForecast(it) }
        viewModel.start()
    }

    private fun onLocationsReady(locations: List<LocationView>){
        val viewPager = view?.findViewById<ViewPager>(R.id.pager_weather_day)
        pagerAdapter = WeatherTodayPagerAdapter(locations)
        viewPager?.adapter = pagerAdapter

       // val index = locations.indexOfFirst { it.placeId == initialPlaceId }
        viewPager?.currentItem = if (initialPlaceId!=null) {
            val index = locations.indexOfFirst { it.placeId == initialPlaceId }
            if (index<0) 0 else index
        }else 0
    }

    inner class WeatherTodayPagerAdapter(private val locations: List<LocationView>)
        : FragmentStatePagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        private val forecasts: HashMap<String,WeatherTodayView> = HashMap()

        override fun getItem(position: Int): Fragment {
            val fragment = FragmentWeatherTodayLocationOverview()
            fragment.setLocation(locations[position])

            val forecast = forecasts[locations[position].placeId]
            fragment.setForecast(forecast)

            if (forecast==null) viewModel.getWeather(locations[position].placeId)

            return fragment
        }

        override fun getCount(): Int {
            return locations.size
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }

        fun addWeatherForecast(weather: WeatherTodayView){
            forecasts[weather.placeId] = weather
            notifyDataSetChanged()
        }
    }
}