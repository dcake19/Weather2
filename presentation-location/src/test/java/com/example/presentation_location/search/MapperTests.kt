package com.example.presentation_location.search

import com.example.domain.autocomplete.Prediction
import com.example.domain.autocomplete.Predictions
import com.example.presentation_location.PresentationTestUtil
import com.example.presentation_location.PresentationTestUtil.terms
import com.example.presentation_location_view_model.search.SearchLocationMapper
import com.example.presentation_location_view_model.search.SearchResultView
import com.example.presentation_location_view_model.search.SearchResultsView
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MapperTests {

    @Test
    fun map(){
        val searchLocationMapper = SearchLocationMapper()

        val searchResultsViewExpected = SearchResultsView("term", listOf(
            SearchResultView("place_id_1","",""),
            SearchResultView("place_id_2", "term-name_2",""),
            SearchResultView("place_id_3","term-name_3","term-region_3"),
            SearchResultView("place_id_4","term-name_4","term-region_4, term-country_4"),
            SearchResultView("place_id_5","term-name_5","term-region_5, term-country_5")
        ))

        val searchResultViewActual = searchLocationMapper.map(Predictions("term", listOf(
            Prediction("place_id_1",terms.take(0).map { "term${it}1" }),
            Prediction("place_id_2",terms.take(1).map { "term${it}2" }),
            Prediction("place_id_3",terms.take(2).map { "term${it}3" }),
            Prediction("place_id_4",terms.take(3).map { "term${it}4" }),
            Prediction("place_id_5",terms.take(4).map { "term${it}5" }))))

        assertThat(searchResultViewActual, `is`(searchResultsViewExpected))
    }
}