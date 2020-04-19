package com.example.presentation_location_view_model.search

import com.example.domain.autocomplete.Predictions

class SearchLocationMapper {

    fun map(predictions: Predictions): SearchResultsView{
        return SearchResultsView(predictions.searchTerm,predictions
            .locations.map { SearchResultView(it.placeId,getPlaceName(it.terms),getRegionName(it.terms)) })
    }

    private fun getPlaceName(terms: List<String>): String{
        return if (terms.isNotEmpty()) terms[0] else ""
    }

    private fun getRegionName(terms: List<String>): String{
        return if (terms.size<2) "" else if (terms.size==2) terms[1] else "${terms[1]}, ${terms[2]}"
    }
}