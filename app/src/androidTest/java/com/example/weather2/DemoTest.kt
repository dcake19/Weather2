package com.example.weather2

import android.content.Intent
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.presentation_location_view_model.locations.LocationsPresentation
import com.example.presentation_location_view_model.locations.LocationsViewModel
//import com.example.weather2.locations.ViewModelProvider
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
@LargeTest
class DemoTest {

    @Mock lateinit var mockLocationsViewModel: LocationsViewModel
    private lateinit var emitter: ObservableEmitter<List<LocationsPresentation>>

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java,true,false)

    @Before fun before(){
        MockitoAnnotations.initMocks(this)
       // ViewModelProvider.locationsViewModel = mockLocationsViewModel

    }

    @Test fun demo(){
        Mockito.`when`(mockLocationsViewModel.getLocationsObservable()).thenReturn(Observable.create{emitter = it})
        Mockito.`when`(mockLocationsViewModel.getStoredLocations()).then { emitter.onNext(listOf(LocationsPresentation("place_id,",1,"St. Neots","Cambridgeshire","UK"))) }
        activityRule.launchActivity(Intent())
        Thread.sleep(500)
        Thread.sleep(1000)
    }
}