package com.example.ui_weather_2

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.presentation_weather_2.WeatherTodayView
import com.example.presentation_weather_2.main.WeatherMainForecastViewModel
import com.example.ui_weather_2.daily.FragmentWeatherDays
import com.example.ui_weather_2.today.FragmentWeatherTodayOverview
import io.reactivex.ObservableEmitter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class WeatherTodayUITest {

    @Mock lateinit var viewModel: WeatherMainForecastViewModel
    private lateinit var emitter: ObservableEmitter<WeatherTodayView>

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
    }

    private fun launchFragment(navController: NavController?=null){
        val fragment = FragmentWeatherTodayOverview()
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
    fun displayWeatherToday(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragment(navController)


    }

}