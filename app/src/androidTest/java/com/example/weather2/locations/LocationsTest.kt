package com.example.weather2.locations

import android.app.Activity
import android.content.Intent
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.presentation_location_view_model.locations.LocationsView
import com.example.presentation_location_view_model.locations.LocationsViewModel
import com.example.weather2.MainActivity
import com.example.weather2.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
@LargeTest
class LocationsTest<T:Activity> {

    @Mock
    lateinit var mockLocationsViewModel: LocationsViewModel
    private lateinit var emitter: ObservableEmitter<List<LocationsView>>

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java,true,false)

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        ViewModelProvider.locationsViewModel = mockLocationsViewModel
    }

    @Test
    fun demo(){
        Mockito.`when`(mockLocationsViewModel.getLocationsObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(mockLocationsViewModel.getStoredLocations())
            .then { emitter.onNext(listOf()) }
        activityRule.launchActivity(Intent())
        Thread.sleep(500)
        //Espresso.onView(ViewMatchers.withId(R.id.button_locations)).perform(ViewActions.click())
        Thread.sleep(1000)
    }

    @Test
    fun demo2(){
        val navController = mock(NavController::class.java)
        Mockito.`when`(mockLocationsViewModel.getLocationsObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(mockLocationsViewModel.getStoredLocations())
            .then { emitter.onNext((1..10).map { UILocationTestUtil.createLocation(it) }) }
        activityRule.launchActivity(Intent())
        Thread.sleep(500)
        onView(withId(R.id.button_add)).perform(click())
        Thread.sleep(1000)
    }
}