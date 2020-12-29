package com.example.ui_weather_2.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.presentation_weather_2.LocationView
import com.example.presentation_weather_2.WeatherTodayView
import com.example.presentation_weather_2.main.WeatherMainForecastViewModel
import com.example.ui_weather_2.ForecastNavigation
import com.example.ui_weather_2.R
import com.example.ui_weather_2.application.ApplicationFeatureWeather
import com.example.ui_weather_2.idling_resource.EspressoIdlingResource
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FragmentWeatherTodayOverview: Fragment(){

    @Inject lateinit var viewModel: WeatherMainForecastViewModel
    @Inject lateinit var locationSetter: LocationSetter
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
        if(activity is ForecastNavigation) (activity as ForecastNavigation).mainForecastOpened()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLocationsObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { EspressoIdlingResource.decrement() }
            .subscribe { onLocationsReady(it) }
        viewModel.getWeatherObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { EspressoIdlingResource.decrement() }
            .subscribe { pagerAdapter.addWeatherForecast(it) }
        viewModel.getErrorObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                context?.let{c -> Toast.makeText(c,it,Toast.LENGTH_SHORT)}
                pagerAdapter.notifyDataSetChanged()
            })
        viewModel.getPendingObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pagerAdapter.notifyDataSetChanged() }
        viewModel.start()
    }

    private fun onLocationsReady(locations: List<LocationView>){
        if (locations.isEmpty()){
            EspressoIdlingResource.increment()
            activity?.let {locationSetter.getLocation(it,viewModel)}
        }else {
            val viewPager = view?.findViewById<ViewPager>(R.id.pager_weather_day)
            pagerAdapter = WeatherTodayPagerAdapter(locations)
            viewPager?.adapter = pagerAdapter

            // val index = locations.indexOfFirst { it.placeId == initialPlaceId }
            viewPager?.currentItem = if (initialPlaceId != null) {
                val index = locations.indexOfFirst { it.placeId == initialPlaceId }
                if (index < 0) 0 else index
            } else 0
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==LocationSetter.LOCATION_REQUEST_CODE)
            locationSetter.setLocation()
    }

    inner class WeatherTodayPagerAdapter(private val locations: List<LocationView>)
        : FragmentStatePagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        private val forecasts: HashMap<String,WeatherTodayView> = HashMap()

        override fun getItem(position: Int): Fragment {
            val fragment = FragmentWeatherTodayLocationOverview(viewModel)
            locations[position]?.let {
                fragment.setLocation(it)

                val forecast = forecasts[it.placeId]
                fragment.setForecast(forecast)

                fragment.pending = viewModel.isPending(it.placeId)

                if (forecast == null){
                    EspressoIdlingResource.increment()
                    viewModel.getWeather(it.placeId,false)
                }

            }
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