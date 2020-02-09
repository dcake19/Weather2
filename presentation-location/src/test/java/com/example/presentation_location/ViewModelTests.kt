package com.example.presentation_location

import com.example.domain.Location
import com.example.domain.LocationInteractor
import com.example.presentation_location_view_model.locations.LocationsMapper
import com.example.presentation_location_view_model.locations.LocationsView
import com.example.presentation_location_view_model.locations.LocationsViewModelImpl
import com.example.utils.schedulers.RxSchedulerProviderTrampoline
import com.example.utils.ViewModelEmitter
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ViewModelTests {

//    @Test
//    fun addition_isCorrect() {
//        Assert.assertEquals(4, 2 + 2)
//    }

    @Mock lateinit var interactor: LocationInteractor
    @Mock lateinit var emitter: ViewModelEmitter<List<LocationsView>>

    private lateinit var viewModel: LocationsViewModelImpl


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        viewModel = LocationsViewModelImpl(interactor,
            RxSchedulerProviderTrampoline(),
            LocationsMapper(),emitter)
    }

    @Test
    fun getStoredLocations(){
        val locationsCache = listOf(Location("place-id-1",2,
            "Location 1","Region 1","Country 1",0.0,0.0),
            Location("place-id-2",1,
                "Location 2","Region 2","Country 2",0.0,0.0))

        val locationsView = listOf(LocationsView(locationsCache[1]),LocationsView(locationsCache[0]))

        Mockito.`when`(interactor.getStoredLocations()).thenReturn(Single.just(locationsCache))

        viewModel.getStoredLocations()

        Mockito.verify(emitter).post(locationsView)
    }

}