package com.example.ui_weather_2

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.presentation_weather_2.WeatherDayForecastView
import com.example.presentation_weather_2.daily.WeatherDailyForecastViewModel
import com.example.ui_weather_2.daily.FragmentWeatherDay
import com.example.ui_weather_2.daily.FragmentWeatherDays
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

    @Test
    fun displayWeatherDaily(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragment(navController)
    }

}