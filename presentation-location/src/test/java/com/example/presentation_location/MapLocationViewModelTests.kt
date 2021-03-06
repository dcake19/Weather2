package com.example.presentation_location

import com.example.domain.use_cases.location.LocationInteractor
import com.example.presentation_location_view_model.map.MapLocationMapper
import com.example.presentation_location_view_model.map.MapLocationView
import com.example.presentation_location_view_model.map.MapLocationsViewModelImpl
import com.example.presentation_location_view_model.map.NewMapLocationView
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProviderTrampoline
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MapLocationViewModelTests {

    @Mock lateinit var interactor: LocationInteractor
    @Mock lateinit var mapLocationEmitter: ViewModelEmitter<List<MapLocationView>>
    @Mock lateinit var newMapLocationEmitter: ViewModelEmitter<NewMapLocationView>
    @Mock lateinit var errorEmitter: ViewModelEmitter<String>

    private lateinit var viewModel: MapLocationsViewModelImpl

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)

        viewModel = MapLocationsViewModelImpl(interactor,
            RxSchedulerProviderTrampoline(), MapLocationMapper(),
            mapLocationEmitter,newMapLocationEmitter,errorEmitter)
    }

    @Test
    fun getStoredLocations(){
        val storedLocations = (0..9).map { PresentationTestUtil.createLocation(it)}
        val mapLocations = (0..9).map { PresentationTestUtil.createMapLocationView(it)}
        `when`(interactor.getStoredLocations()).thenReturn(Single.just(storedLocations))

        viewModel.getStoredLocations()

        verify(mapLocationEmitter).post(mapLocations)
    }

    @Test
    fun getNewLocationByPlaceId(){
        val placeId = "place_0"
        val location = PresentationTestUtil.createLocation()

        val newMapLocation = PresentationTestUtil.createNewMapLocationView()

        `when`(interactor.getLocationByPlaceId(placeId)).thenReturn(Single.just(location))

        viewModel.getNewLocationByPlaceId(placeId)

        verify(newMapLocationEmitter).post(newMapLocation)
    }

    @Test
    fun getNewLocationBySearchTerm(){
        val term = "term"
        val location = PresentationTestUtil.createLocation()
        val newMapLocation = PresentationTestUtil.createNewMapLocationView()

        `when`(interactor.getLocationByName(term)).thenReturn(Single.just(location))

        viewModel.getNewLocationBySearchTerm(term)

        verify(newMapLocationEmitter).post(newMapLocation)
    }
}