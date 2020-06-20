package com.example.presentation_location

import com.example.domain.autocomplete.PredictionInteractor
import com.example.domain.autocomplete.Predictions
import com.example.presentation_location_view_model.search.SearchLocationMapper
import com.example.presentation_location_view_model.search.SearchLocationViewModelImpl
import com.example.presentation_location_view_model.search.SearchResultsView
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProviderTrampoline
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SearchLocationViewModelTests {

    @Mock lateinit var interactor: PredictionInteractor
    @Mock lateinit var searchResultsEmitter: ViewModelEmitter<SearchResultsView>
    @Mock lateinit var locationsAddedEmitter: ViewModelEmitter<Boolean>

    private lateinit var viewModel: SearchLocationViewModelImpl

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        viewModel = SearchLocationViewModelImpl(interactor,
            RxSchedulerProviderTrampoline(), SearchLocationMapper(),
            searchResultsEmitter,locationsAddedEmitter
        )
    }

    @Test
    fun addLocation(){
        val placeId = "place_0"

        `when`(interactor.addLocation(placeId)).thenReturn(Completable.complete())

        viewModel.addLocation(placeId)

        verify(locationsAddedEmitter).post(true)
    }

    @Test
    fun singleSearchTerm(){
        val term = "l"
        val predictionsExpected = SearchResultsView(term,(1..5).map { PresentationTestUtil.createSearchResult(it,term)})
        val predictions = (1..5).map { PresentationTestUtil.createPrediction(it,term,3) }

        `when`(interactor.searchLocations(term)).thenReturn(Single.just(Predictions(term,predictions)))

        viewModel.searchLocation(term)

        verify(searchResultsEmitter).post(predictionsExpected)
    }

}