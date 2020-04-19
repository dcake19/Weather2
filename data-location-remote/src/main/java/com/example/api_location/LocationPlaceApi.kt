package com.example.api_location

import com.example.api_location.key.GEOCODING_API_KEY
import com.example.data_repository_location.auto_complete.AutoCompleteDataNetwork
import com.example.data_repository_location.auto_complete.LocationPredictionData
import io.reactivex.Single

class LocationPlaceApi(private val retrofitLocationApi: RetrofitLocationApi): AutoCompleteDataNetwork {

    override fun getAutocompleteLocation(searchTerm: String): Single<List<LocationPredictionData>> {
        return retrofitLocationApi.getAutocompleteLocation(searchTerm,"(cities)",GEOCODING_API_KEY)
            .map { lar -> lar.predictions
              //  .filter { p -> p.types.contains("locality") }
                .map { p -> LocationPredictionData(p.placeId,p.terms.take(3).map { t -> t.value }) } }
    }

}