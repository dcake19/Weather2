package com.example.ui_weather_2

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.presentation_weather_2.WeatherHourForecastView
import com.example.presentation_weather_2.constants.*
import com.example.presentation_weather_2.hourly.WeatherHourlyForecastViewModel

import com.example.ui_weather_2.hourly.FragmentWeatherHours
import com.example.ui_weather_2.hourly.FragmentWeatherHoursArgs
import io.reactivex.ObservableEmitter
import org.hamcrest.core.AllOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.ui_weather_2.daily.FragmentWeatherDaysArgs
import io.reactivex.Observable
import org.hamcrest.core.AllOf.allOf

@RunWith(AndroidJUnit4::class)
class WeatherHourUITest {

    @Mock lateinit var viewModel: WeatherHourlyForecastViewModel
    private lateinit var emitter: ObservableEmitter<List<WeatherHourForecastView>>

    private val placeId = "place_id"

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
    }

    private fun launchFragment(navController: NavController?=null,startHour: Int){
        val fragment = FragmentWeatherHours()
        fragment.viewModel = viewModel
        val scenario = launchFragmentInContainer(FragmentWeatherHoursArgs(placeId,startHour).toBundle(), R.style.Theme_AppCompat){
            fragment
        }

        if (navController!=null) {
            scenario.onFragment {
                Navigation.setViewNavController(it.requireView(), navController)
            }
        }

    }

    private fun getWeather(): List<WeatherHourForecastView>{
        val hours = listOf(21,22,23,0,1,2,3,4,5,6)
        val weatherIds = listOf(
            THUNDERSTORM, DRIZZLE_HEAVY_SHOWER_RAIN , RAIN_MODERATE, SNOW, CLEAR,
            CLOUDS_FEW, CLOUDS_OVERCAST, SAND, FOG, SMOKE)
        val temp = listOf(9,8,7,5,3,5,6,7,8,9)
        val feelLike = listOf(6,5,2,1,0,2,3,7,8,9)
        val rain = listOf(16,2,4,4,0,0,0,0,0,0)
        val windSpeed = listOf(15.4,6.2,3.2,1.4,1.2,3.1,4.2,1.0,0.4,0.8)
        val windDirection = listOf(NORTH, NORTH_EAST, NORTH_WEST, SOUTH, SOUTH_EAST, SOUTH_WEST, EAST, EAST, EAST, EAST)
        val cloudCoverage = listOf(89,96,100,100,23,58,71,60,39,4)
        return (0..9).map { WeatherUITestUtil.getHourForecastView(it+1,hours[it],weatherIds[it],
            temp[it],feelLike[it],rain[it],windSpeed[it],windDirection[it],cloudCoverage[it]) }
    }

    private val winDirection = listOf("N","NE","NW","S","SE","SW","E","E","E","E")

    private val weatherDrawables = listOf(R.drawable.forecast_thunderstorm,
        R.drawable.forecast_day_showers, R.drawable.forecast_rain,R.drawable.forecast_snow,
        R.drawable.forecast_day_sunny,R.drawable.forecast_day_cloudy,R.drawable.ic_wi_cloud,
        R.drawable.forecast_sandstorm,R.drawable.forecast_fog,R.drawable.forecast_smoke)

    @Test
    fun backPressed(){
        val navController = Mockito.mock(NavController::class.java)
        Mockito.`when`(viewModel.getWeatherHoursObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(viewModel.getWeatherHours())
            .then{emitter.onNext(getWeather())}

        launchFragment(navController,3)

        onView(withId(R.id.button_back)).perform(ViewActions.click())
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun displayWeatherHourly(){
        val startHour = 3
        val weather = getWeather()

        val navController = Mockito.mock(NavController::class.java)
        Mockito.`when`(viewModel.getWeatherHoursObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(viewModel.getWeatherHours())
            .then{emitter.onNext(weather)}

        launchFragment(navController,startHour)

        for (hour in (startHour until weather.size)) {
            check(weather[hour], winDirection[hour], weatherDrawables[hour])
            onView(allOf(withId(R.id.layout_weather_hour), isDisplayed())).perform(ViewActions.swipeLeft())
            Thread.sleep(1000)
        }

        for (hour in  weather.size-1 downTo 0){
            check(weather[hour], winDirection[hour], weatherDrawables[hour])
            onView(allOf(withId(R.id.layout_weather_hour), isDisplayed())).perform(ViewActions.swipeRight())
            Thread.sleep(1000)
        }

        check(weather.first(),winDirection.first(),weatherDrawables.first())
    }

    private fun check(forecast: WeatherHourForecastView,windDirection: String,drawable: Int){
        onView(allOf(withId(R.id.text_time),isDisplayed()))
            .check(matches(withText(forecast.time)))

        val viewInteraction = onView(allOf(withId(R.id.image_weather_icon),
            isDescendantOfA(allOf(withId(R.id.layout_weather_hour),isDisplayed()))))

        viewInteraction.perform(CustomScrollActions.nestedScrollTo())
        viewInteraction.check(matches(withDrawable(drawable,R.color.dark_icon_1)))

        checkId(R.id.text_temp,forecast.temperature)
        checkId(R.id.text_temp_feel,"Feels Like ${forecast.feelsLike}")
        checkId(R.id.text_summary,forecast.description)
        checkId(R.id.text_rain_quantity,forecast.rain)
        checkId(R.id.text_wind_speed,forecast.windSpeed)
        checkId(R.id.text_wind_direction,windDirection)
        checkId(R.id.text_cloud_coverage_pct,forecast.cloudCoverage)
    }

    private fun checkId(id: Int,display: String){
        val viewInteraction = onView(allOf(withId(id),
            isDescendantOfA(allOf(withId(R.id.layout_weather_hour), isDisplayed()))))

        viewInteraction.perform(CustomScrollActions.nestedScrollTo())
        viewInteraction.check(matches(withText(display)))
    }

}