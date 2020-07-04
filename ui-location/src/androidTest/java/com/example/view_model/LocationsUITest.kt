package com.example.view_model

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.locations.FragmentLocations
import com.example.presentation_location_view_model.locations.LocationsView
import com.example.presentation_location_view_model.locations.LocationsViewModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
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

    @Test
    fun test1(){
        Mockito.`when`(mockLocationsViewModel.getLocationsObservable())
            .thenReturn(Observable.create{emitter = it})
        Mockito.`when`(mockLocationsViewModel.getStoredLocations())
            .then { emitter.onNext((1..10).map { LocationUITestUtil.createLocation(it) }) }

        launchFragmentInContainer(Bundle(), R.style.Theme_AppCompat){
            getFragment()
        }
        Thread.sleep(1500)
    }

}