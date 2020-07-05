package com.example.view_model

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.RecyclerViewMatcher.withRecyclerView
import com.example.locations.FragmentLocations
import com.example.presentation_location_view_model.locations.LocationsView
import com.example.presentation_location_view_model.locations.LocationsViewModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class LocationsUITest {

    @Mock
    lateinit var mockLocationsViewModel: LocationsViewModel
    private lateinit var emitter: ObservableEmitter<List<LocationsView>>

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
    }

    private fun getFragment(): FragmentLocations{
        val fragment = FragmentLocations()
        fragment.viewModel = mockLocationsViewModel
        return fragment
    }

    private fun launchFragment(navController: NavController?=null){
        val fragment = FragmentLocations()
        fragment.viewModel = mockLocationsViewModel
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
    fun openSearch(){
        val navController = Mockito.mock(NavController::class.java)
        Mockito.`when`(mockLocationsViewModel.getLocationsObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(mockLocationsViewModel.getStoredLocations())
            .then { emitter.onNext(listOf()) }

        launchFragment(navController)

        onView(withId(R.id.button_add)).perform(click())
        verify(navController).navigate(R.id.action_locations_to_search)
    }

    @Test
    fun backPressed(){
        val navController = Mockito.mock(NavController::class.java)
        Mockito.`when`(mockLocationsViewModel.getLocationsObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(mockLocationsViewModel.getStoredLocations())
            .then { emitter.onNext(listOf()) }

        launchFragment(navController)

        onView(withId(R.id.button_back)).perform(click())
        verify(navController).popBackStack()
    }

    @Test
    fun displayLocations(){
        val locations = (1..20).map { LocationUITestUtil.createLocation(it) }

        Mockito.`when`(mockLocationsViewModel.getLocationsObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(mockLocationsViewModel.getStoredLocations())
            .then { emitter.onNext(locations) }

        launchFragment()

        Thread.sleep(500)
        check(locations)

        Thread.sleep(1500)
    }

    @Test
    fun test(){
        val navController = Mockito.mock(NavController::class.java)
        Mockito.`when`(mockLocationsViewModel.getLocationsObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(mockLocationsViewModel.getStoredLocations())
            .then { emitter.onNext((1..20).map { LocationUITestUtil.createLocation(it) }) }

        launchFragment(navController)

        Thread.sleep(500)
        onView(withId(R.id.button_back)).perform(click())
        verify(navController).popBackStack()
        Thread.sleep(1500)
    }

    private fun check(locations: List<LocationsView>){
        for (i in locations.indices){
            onView(withId(R.id.list_locations))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(i))
            onView(withRecyclerView(R.id.list_locations)
                .atPositionOnView(i,R.id.location_text_place_name))
                .check(matches(withText(locations[i].name)))
            onView(withRecyclerView(R.id.list_locations)
                .atPositionOnView(i,R.id.location_text_region_name))
                .check(matches(withText("${locations[i].region}, ${locations[i].country}")))
        }
    }

}