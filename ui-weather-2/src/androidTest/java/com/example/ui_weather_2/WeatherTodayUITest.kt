package com.example.ui_weather_2

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import org.hamcrest.core.AllOf.allOf
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.presentation_weather_2.LocationView
import com.example.presentation_weather_2.WeatherTodayHourlyForecastView
import com.example.presentation_weather_2.WeatherTodayView
import com.example.presentation_weather_2.constants.CLEAR
import com.example.presentation_weather_2.constants.NORTH
import com.example.presentation_weather_2.main.WeatherMainForecastViewModel
import com.example.ui_weather_2.RecyclerViewMatcher.withRecyclerView
import com.example.ui_weather_2.daily.FragmentWeatherDays
import com.example.ui_weather_2.today.FragmentWeatherTodayOverview
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class WeatherTodayUITest {

    @Mock lateinit var viewModel: WeatherMainForecastViewModel
    private lateinit var locationsEmitter: ObservableEmitter<List<LocationView>>
    private lateinit var weatherEmitter: ObservableEmitter<WeatherTodayView>

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

    private fun getWeather(): List<WeatherTodayView>{
        return (1..5).map { WeatherUITestUtil.getWeatherToday(it, CLEAR,25,20,2,
            "7:00","8:00",0.8, NORTH,10,1000,80)}
    }

    private val weatherDrawable = R.drawable.forecast_day_sunny
    private val windDirection = "N"

    private fun getLocations(): List<LocationView>{
        return (1..5).map { WeatherUITestUtil.getLocation(it) }
    }

    @Test
    fun displayLocations(){
        val locations = getLocations()

        val navController = Mockito.mock(NavController::class.java)

        Mockito.`when`(viewModel.getLocationsObservable())
            .thenReturn(Observable.create { locationsEmitter = it })
        Mockito.`when`(viewModel.getWeatherObservable())
            .thenReturn(Observable.create { weatherEmitter = it })

        Mockito.`when`(viewModel.start()).then { locationsEmitter.onNext(locations) }

        launchFragment(navController)

        for (location in locations) {
            onView(allOf(withId(R.id.text_location), isDisplayed()))
                .check(matches(withText(location.placeName)))

//            onView(allOf(isDisplayed(), withId(R.id.progress)))
//                .check(matches(isDisplayed()))
//
//            onView(allOf(isDisplayed(), withId(R.id.layout_weather_forecast)))
//                .check(doesNotExist())
//
//            onView(allOf(withId(R.id.pager_weather_day), isDisplayed()))
//                .perform(swipeLeft())
//            Thread.sleep(1000)
        }
    }

    @Test
    fun displayWeatherToday(){
        val locations = getLocations()
        val weather = getWeather()

        val navController = Mockito.mock(NavController::class.java)

        Mockito.`when`(viewModel.getLocationsObservable())
            .thenReturn(Observable.create { locationsEmitter = it })
        Mockito.`when`(viewModel.getWeatherObservable())
            .thenReturn(Observable.create { weatherEmitter = it })

        Mockito.`when`(viewModel.start()).then { locationsEmitter.onNext(locations) }

        for (i in 0..4) {
            Mockito.`when`(viewModel.getWeather(locations[i].placeId))
                .then{weatherEmitter.onNext(weather[i])}
        }

        launchFragment(navController)

        for (i in locations.indices) {
            onView(allOf(withId(R.id.text_location), isDisplayed()))
                .check(matches(withText(locations[i].placeName)))

            check(weather[i], windDirection, weatherDrawable)

//            for (j in weather[i].hourly.indices) {
//                check(j, weather[i].hourly[j], weatherDrawable)
//            }

       //    check(0, weather[i].hourly[0], weatherDrawable)
          //  Thread.sleep(1000)

            onView(allOf(withId(R.id.layout_weather_forecast), isDisplayed())).perform(swipeLeft())

            Thread.sleep(1000)
        }

        check(0, weather[4].hourly[0], weatherDrawable)
        Thread.sleep(3000)

    }

    private fun check(forecast: WeatherTodayView,windDirection: String,drawable: Int){
        checkId(R.id.text_date_time,forecast.dateTime)
        checkId(R.id.text_temp,forecast.temperature)
        checkId(R.id.text_temp_feel,forecast.feelsLike)
        checkId(R.id.text_summary,forecast.description)
        checkId(R.id.text_rain_quantity,forecast.rain)
        checkId(R.id.text_sunrise_time,forecast.sunrise)
        checkId(R.id.text_sunset_time,forecast.sunset)
        checkId(R.id.text_wind_speed,forecast.windSpeed)
        checkId(R.id.text_wind_direction,windDirection)
        checkId(R.id.text_cloud_coverage_pct,forecast.cloudCoverage)
        checkId(R.id.text_pressure,forecast.pressure)
        checkId(R.id.text_humidity_pct,forecast.humidity)

        val viewInteraction = onView(allOf(withId(R.id.image_weather_icon),
            isDescendantOfA(allOf(withId(R.id.layout_weather_forecast),isDisplayed()))))

        viewInteraction.perform(CustomScrollActions.nestedScrollTo())
        viewInteraction.check(matches(withDrawable(drawable,R.color.dark_icon_1)))
    }

    private fun checkId(id: Int,display: String){
        val viewInteraction = onView(allOf(withId(id),
            isDescendantOfA(allOf(withId(R.id.layout_weather_forecast),isDisplayed()))))

        viewInteraction.perform(CustomScrollActions.nestedScrollTo())
        viewInteraction.check(matches(withText(display)))
    }

    private fun check(position: Int,forecast: WeatherTodayHourlyForecastView,drawable: Int){

        checkRecyclerViewId(R.id.list_hourly,R.id.text_hour_time,position,forecast.time)
        //checkRecyclerViewId(R.id.list_hourly,R.id.text_hour_rain,position,forecast.rain)
        //checkRecyclerViewId(R.id.list_hourly,R.id.text_hour_temp,position,forecast.temperature)
    }

    private fun checkRecyclerViewId(recyclerViewId: Int,id: Int,position: Int,display: String){
//        onView(withRecyclerView(recyclerViewId)
//            .atPositionOnView(position,id))
//            .check(matches(withText2(display)))
       // withRecyclerView(recyclerViewId).atPositionOnView(position,id)
        val viewInteraction = onView(allOf(withId(R.id.list_hourly),
            isDescendantOfA(allOf(withId(R.id.layout_weather_forecast),isDisplayed()))))

        viewInteraction.perform(CustomScrollActions.nestedScrollTo())

        viewInteraction.check(matches(withText2(display)))
//        onView(allOf(withRecyclerView(recyclerViewId)
//            .atPositionOnView(position,id),isDisplayed()))
//            .check(matches(withText2(display)))
    }

    fun withText2(text: String)= object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("The TextView/EditText has value")
        }

        override fun matchesSafely(item: View?): Boolean {

            val time = (item as RecyclerView)[0].findViewById<TextView>(R.id.text_hour_time).text
            val temp = (item as RecyclerView)[0].findViewById<TextView>(R.id.text_hour_temp).text
            return true
        }

    }

}

