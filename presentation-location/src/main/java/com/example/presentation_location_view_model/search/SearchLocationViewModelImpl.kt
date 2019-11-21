package com.example.presentation_location_view_model.search

import com.example.domain.Location
import com.example.domain.autocomplete.PredictionInteractor
import com.example.domain.autocomplete.Predictions
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchLocationViewModelImpl(private val predictionInteractor: PredictionInteractor): SearchLocationViewModel {

    override fun searchLocation(term: String) {
        predictionInteractor.searchLocations(term)
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe(object : SingleObserver<Predictions> {
                override fun onSuccess(t: Predictions) {
                    t
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

            })
    }
}