package com.example.weather2

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.weather2.dagger.AppComponent
import com.example.weather2.dagger.DaggerAppComponent
import com.example.weather2.dagger.DaggerFeatureLocationComponent
import com.example.weather2.dagger.FeatureLocationComponent

class WeatherApplication: Application() {

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

    fun inject(fragment: Fragment) {
        when (fragment) {
            is FragmentLocation -> {
                if (locationComponent == null) locationComponent = DaggerFeatureLocationComponent.builder().appComponent(appComponent).build()
                locationComponent?.inject(fragment)
            }
        }
    }

}