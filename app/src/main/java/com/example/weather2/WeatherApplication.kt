package com.example.weather2

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.search.FragmentSearch
import com.example.application.ApplicationFeatureLocation
import com.example.locations.FragmentLocations
import com.example.map.FragmentMap
import com.example.ui_weather_2.application.ApplicationFeatureWeather
import com.example.ui_weather_2.daily.FragmentWeatherDays
import com.example.ui_weather_2.hourly.FragmentWeatherHours
import com.example.ui_weather_2.today.FragmentWeatherTodayOverview
import com.example.weather2.dagger.*

class WeatherApplication: Application(), ApplicationMain,
    ApplicationFeatureLocation,
    ApplicationFeatureWeather {

    companion object{
        var application: WeatherApplication? = null

        fun getContext(): Context? {
            return application?.applicationContext
        }
    }

    private var appComponent: AppComponent? = null
        get() = field?:DaggerAppComponent.builder().build()

    private var locationComponent: FeatureLocationComponent? = null
    private var weatherComponent: FeatureWeatherComponent? = null

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    override fun injectMainActivity(mainActivity: MainActivity){
        mainActivity.weatherNavigationController = WeatherNavigationControllerImpl()
    }

    override fun injectLocation(fragment: Fragment) {
        if (fragment is FragmentLocations || fragment is FragmentSearch || fragment is FragmentMap) {
            if (locationComponent == null)
                locationComponent = DaggerFeatureLocationComponent.builder()
                    .appComponent(appComponent).build()
            when(fragment){
                is FragmentLocations -> locationComponent?.inject(fragment)
                is FragmentSearch -> locationComponent?.inject(fragment)
                is FragmentMap -> locationComponent?.inject(fragment)
            }
        }
    }

    override fun injectWeather(fragment: Fragment) {
        if (fragment is FragmentWeatherHours ||
            fragment is FragmentWeatherDays ||
            fragment is FragmentWeatherTodayOverview){
            if (weatherComponent == null){
                weatherComponent = DaggerFeatureWeatherComponent.builder()
                    .appComponent(appComponent).build()
            }

            when(fragment){
                is FragmentWeatherHours -> weatherComponent?.inject(fragment)
                is FragmentWeatherDays -> weatherComponent?.inject(fragment)
                is FragmentWeatherTodayOverview -> weatherComponent?.inject(fragment)
            }
        }
    }


}