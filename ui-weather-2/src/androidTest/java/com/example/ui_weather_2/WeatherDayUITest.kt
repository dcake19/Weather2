package com.example.ui_weather_2

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.presentation_weather_2.WeatherDayForecastView
import com.example.presentation_weather_2.constants.*
import com.example.presentation_weather_2.daily.WeatherDailyForecastViewModel
import com.example.ui_weather_2.daily.FragmentWeatherDay
import com.example.ui_weather_2.daily.FragmentWeatherDays
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class WeatherDayUITest {

    @Mock lateinit var viewModel: WeatherDailyForecastViewModel
    private lateinit var emitter: ObservableEmitter<List<WeatherDayForecastView>>

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
    }

    private fun launchFragment(navController: NavController?=null){
        val fragment = FragmentWeatherDays()
        fragment.viewModel = viewModel
        val scenario = launchFragmentInContainer(Bundle(), R.style.Theme_AppCompat){
            fragment
        }

        if (navController!=null) {
            scenario.onFragment {
                Navigation.setViewNavController(it.requireView(), navController)
            }
        }

    }

    private fun getWeather(): List<WeatherDayForecastView>{
        val dates = listOf("Mon 5 October","Tue 6 October","Wed 7 October","Thu 8 October",
            "Fri 9 October","Sat 10 October","Sun 11 October")
        val weatherIds = listOf(THUNDERSTORM, DRIZZLE , RAIN_MODERATE, SNOW, CLEAR,CLOUDS_FEW,CLOUDS_OVERCAST)
        val tempHigh = listOf(20,19,16,3,26,23,21)
        val tempLow = listOf(8,4,2,-9,14,12,10)
        val rain = listOf(16,2,4,4,0,0,0)
        val sunrise = listOf("8:24","8:24","8:25","8:25","8:26","8:26","8:27")
        val sunset = listOf("18:09","18:09","18:08","18:08","18:07","18:07","18:06")
        val windSpeed = listOf(15.4,6.2,3.2,1.4,1.2,3.1,4.2)
        val windDirection = listOf(NORTH, NORTH_EAST, NORTH_WEST, SOUTH, SOUTH_EAST, SOUTH_WEST, EAST)
        val cloudCoverage = listOf(89,96,100,100,23,58,71)
        val pressure = listOf(1100,1040,1022,1068,800,900,950)
        val humidity = listOf(78,64,63,41,91,89,81)

        return (0..6).map { WeatherUITestUtil.getDayForecastView(it+1,dates[it],weatherIds[it],
            tempHigh[it],tempLow[it],rain[it],sunrise[it],sunset[it],windSpeed[it],
            windDirection[it], cloudCoverage[it],pressure[it],humidity[it]) }
    }

    @Test
    fun displayWeatherDaily(){
        val navController = Mockito.mock(NavController::class.java)
        Mockito.`when`(viewModel.getWeatherDaysObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(viewModel.getWeatherDays())
            .then{emitter.onNext(getWeather())}
        launchFragment(navController)
    }

}