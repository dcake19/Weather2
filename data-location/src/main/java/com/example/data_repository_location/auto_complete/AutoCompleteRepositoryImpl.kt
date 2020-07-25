package com.example.data_repository_location.auto_complete

import com.example.domain.use_cases.autocomplete.AutoCompleteRepository
import com.example.domain.use_cases.autocomplete.Prediction
import com.example.domain.use_cases.autocomplete.Predictions
import io.reactivex.Single

class AutoCompleteRepositoryImpl(private val autoCompleteDataNetwork: AutoCompleteDataNetwork):
    AutoCompleteRepository {

    override fun searchLocations(term: String): Single<Predictions> {
        return autoCompleteDataNetwork.getAutocompleteLocation(term)
            .map { predictions -> Predictions(term,predictions.map { Prediction(it.placeId,it.terms) }) }
    }
}