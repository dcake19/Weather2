package com.example.weather2

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.FragmentSearch
import com.example.application.ApplicationFeatureLocation
import com.example.locations.FragmentLocations
import com.example.weather2.dagger.AppComponent
import com.example.weather2.dagger.DaggerAppComponent
import com.example.weather2.dagger.DaggerFeatureLocationComponent
import com.example.weather2.dagger.FeatureLocationComponent

class WeatherApplication: Application(), ApplicationMain, ApplicationFeatureLocation {

    companion object{

        var application: WeatherApplication? = null

        fun getContext(): Context? {
            return application?.applicationContext
        }
    }

    private var appComponent: AppComponent? = null
        get() = field?:DaggerAppComponent.builder().build()

    private var locationComponent: FeatureLocationComponent? = null

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    override fun injectMainActivity(mainActivity: MainActivity){
        mainActivity.weatherNavigationController = WeatherNavigationControllerImpl()
    }

    override fun injectLocation(fragment: Fragment) {
        if (fragment is FragmentLocations || fragment is FragmentSearch) {
            if (locationComponent == null) locationComponent =
                DaggerFeatureLocationComponent.builder().appComponent(appComponent).build()
            when(fragment){
                is FragmentLocations -> locationComponent?.inject(fragment)
                is FragmentSearch -> locationComponent?.inject(fragment)
            }
        }
    }
}