package com.example.presentation_location

import com.example.domain.use_cases.location.LocationInteractor
import com.example.presentation_location_view_model.locations.LocationsMapper
import com.example.presentation_location_view_model.locations.LocationsView
import com.example.presentation_location_view_model.locations.LocationsViewModelImpl
import com.example.utils.schedulers.RxSchedulerProviderTrampoline
import com.example.utils.ViewModelEmitter
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LocationViewModelTests {

    @Mock lateinit var interactor: LocationInteractor
    @Mock lateinit var emitter: ViewModelEmitter<List<LocationsView>>
    @Mock lateinit var errorEmitter: ViewModelEmitter<String>

    private lateinit var viewModel: LocationsViewModelImpl

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        viewModel = LocationsViewModelImpl(interactor,
            RxSchedulerProviderTrampoline(),
            LocationsMapper(),emitter,errorEmitter)
    }

    @Test
    fun getStoredLocations(){
        val locations = (0..9).map { PresentationTestUtil.createLocation(it) }

        val locationsView = locations.map { LocationsView(it) }

        Mockito.`when`(interactor.getStoredLocations()).thenReturn(Single.just(locations))

        viewModel.getStoredLocations()

        Mockito.verify(emitter).post(locationsView)
    }

    @Test
    fun updateLocations(){
        val locationsView = (0..9).map { LocationsView(PresentationTestUtil.createLocation(it)) }
        val placeIds = locationsView.map { it.placeId }

        viewModel.updateLocationsOrder(locationsView)

        verify(interactor).updateLocationsOrder(placeIds)
    }

    @Test
    fun deleteLocations(){
        val selected = listOf(3,4,5,8)
        val locationsView = (0..9).map { LocationsView(PresentationTestUtil.createLocation(it)) }
        selected.forEach { locationsView[it].selected = true }

        val placeIds = locationsView.filter { it.selected }.map { it.placeId }

        viewModel.deleteLocations(locationsView)

        verify(interactor).deleteLocations(placeIds)
    }

}