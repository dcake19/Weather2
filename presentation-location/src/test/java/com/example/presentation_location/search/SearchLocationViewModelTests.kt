package com.example.presentation_location.search

import com.example.domain.autocomplete.PredictionInteractor
import com.example.domain.autocomplete.Predictions
import com.example.presentation_location.PresentationTestUtil
import com.example.presentation_location_view_model.search.SearchLocationMapper
import com.example.presentation_location_view_model.search.SearchLocationViewModelImpl
import com.example.presentation_location_view_model.search.SearchResultsView
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProviderTrampoline
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter
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
        val predictionsExpected = SearchResultsView(term,(1..5).map { PresentationTestUtil.createSearchResult(it, term) })
        val predictions = (1..5).map { PresentationTestUtil.createPrediction(it, term, 3) }

        `when`(interactor.searchLocations(term)).thenReturn(Single.just(Predictions(term,predictions)))

        viewModel.searchLocation(term)

        verify(searchResultsEmitter).post(predictionsExpected)
    }

    @Test
    fun searchRepeat(){
        val term1 = "l"
        val predictionsExpected1 = SearchResultsView(term1,(1..5).map { PresentationTestUtil.createSearchResult(it, term1) })
        val predictions1 = (1..5).map { PresentationTestUtil.createPrediction(it, term1, 3) }

        `when`(interactor.searchLocations(term1)).thenReturn(Single.just(Predictions(term1,predictions1)))

        val term2 = "lo"
        val predictionsExpected2 = SearchResultsView(term2,(1..5).map { PresentationTestUtil.createSearchResult(it, term2) })
        val predictions2 = (1..5).map { PresentationTestUtil.createPrediction(it, term2, 3) }

        `when`(interactor.searchLocations(term2)).thenReturn(Single.just(Predictions(term2,predictions2)))

        viewModel.searchLocation(term1)
        viewModel.searchLocation(term2)
        viewModel.searchLocation(term1)

        verify(searchResultsEmitter, times(2)).post(predictionsExpected1)
        verify(searchResultsEmitter).post(predictionsExpected2)
        verify(interactor, times(1)).searchLocations(term1)
    }

    @Test
    fun searchTermsReturnOrderChanged(){
        val term1 = "l"
        val predictions1 = Predictions(term1,(1..5).map {
            PresentationTestUtil.createPrediction(it, term1, 3)
        })
        val predictionsExpected1 = SearchResultsView(term1,(1..5).map {
            PresentationTestUtil.createSearchResult(it, term1)
        })
        lateinit var emitter1: SingleEmitter<Predictions>
        val single1 = Single.create<Predictions> { emitter1 = it }

        `when`(interactor.searchLocations(term1)).thenReturn(single1)

        val term2 = "lo"
        val predictions2 = Predictions(term2,(1..5).map { PresentationTestUtil.createPrediction(it, term2, 3) })
        val predictionsExpected2 = SearchResultsView(term2,(1..5).map { PresentationTestUtil.createSearchResult(it, term2) })
        lateinit var emitter2: SingleEmitter<Predictions>
        val single2 = Single.create<Predictions> { emitter2 = it }

        `when`(interactor.searchLocations(term2)).thenReturn(single2)

        viewModel.searchLocation(term1)
        viewModel.searchLocation(term2)
        emitter2.onSuccess(predictions2)
        emitter1.onSuccess(predictions1)

        verify(searchResultsEmitter).post(predictionsExpected2)
        verify(searchResultsEmitter, times(1)).post(any()?:predictionsExpected1)
    }

    @Test
    fun searchNewTermBeforeFirstTermReturned(){
        val term1 = "l"
        val predictions1 = Predictions(term1,(1..5).map {
            PresentationTestUtil.createPrediction(it, term1, 3)
        })
        val predictionsExpected1 = SearchResultsView(term1,(1..5).map {
            PresentationTestUtil.createSearchResult(it, term1)
        })
        lateinit var emitter1: SingleEmitter<Predictions>
        val single1 = Single.create<Predictions> { emitter1 = it }

        `when`(interactor.searchLocations(term1)).thenReturn(single1)

        val term2 = "a"
        val predictions2 = Predictions(term2,(1..5).map {
            PresentationTestUtil.createPrediction(it, term2, 3)
        })
        val predictionsExpected2 = SearchResultsView(term2,(1..5).map {
            PresentationTestUtil.createSearchResult(it, term2)
        })
        lateinit var emitter2: SingleEmitter<Predictions>
        val single2 = Single.create<Predictions> { emitter2 = it }

        `when`(interactor.searchLocations(term2)).thenReturn(single2)

        viewModel.searchLocation(term1)
        viewModel.searchLocation(term2)
        emitter1.onSuccess(predictions1)
        emitter2.onSuccess(predictions2)

        verify(searchResultsEmitter).post(predictionsExpected2)
        verify(searchResultsEmitter, times(1)).post(any()?:predictionsExpected1)
    }

    

}