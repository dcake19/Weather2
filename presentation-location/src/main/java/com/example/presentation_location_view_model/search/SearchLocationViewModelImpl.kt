package com.example.presentation_location_view_model.search

import com.example.domain.autocomplete.PredictionInteractor
import com.example.domain.autocomplete.Predictions
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProvider
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchLocationViewModelImpl(private val predictionInteractor: PredictionInteractor,
                                  private val scheduler: RxSchedulerProvider,
                                  private val mapper: SearchLocationMapper,
                                  private val searchResultsEmitter: ViewModelEmitter<SearchResultsView>): SearchLocationViewModel {

    //search terms to be displayed
    private val searchTerms = mutableListOf<String>()

    // search results for each search term
    private val searchResults = HashMap<String,List<SearchResultView>>()

    override fun getSearchResultsObservable(): Observable<SearchResultsView> {
        return Observable.create{searchResultsEmitter.initEmitter(it)}
    }

    override fun searchLocation(term: String) {
        val searchResult = searchResults[term]

        if (searchResult!=null){
            searchResultsEmitter.post(SearchResultsView(term,searchResult))
        }else {
            val validSearchTerms = mutableListOf<String>()
            for (i in 1..term.length) {
                validSearchTerms.add(term.substring(0, i))
            }
            searchTerms.removeAll { !validSearchTerms.contains(it) }

            if (term.isNotBlank()) {
                predictionInteractor.searchLocations(term)
                    .subscribeOn(scheduler.computation())
                    .observeOn(scheduler.computation())
                    .map { mapper.map(it) }
                    .doOnSuccess { searchResults[it.term] = it.searchResults }
                    .subscribe(object : SingleObserver<SearchResultsView> {
                        override fun onSuccess(t: SearchResultsView) {
                            if (searchTerms.contains(term)) {
                                searchTerms.removeAll { it.length <= term.length }
                                searchResultsEmitter.post(t)
                            }
                        }

                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onError(e: Throwable) {

                        }

                    })
            }
        }
    }
}