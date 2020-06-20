package com.example.presentation_location

import com.example.domain.Location
import com.example.domain.autocomplete.Prediction
import com.example.presentation_location_view_model.map.MapLocationView
import com.example.presentation_location_view_model.map.NewMapLocationView
import com.example.presentation_location_view_model.search.SearchResultView

object PresentationTestUtil {

    fun createLocation(i: Int=0,lat: Double=0.0,lng: Double=0.0) =
        Location("place_id_$i","name_$i","region_$i","country_$i",lat,lng)

    fun createMapLocationView(i: Int=0,lat: Double=0.0,lng: Double=0.0)
            = MapLocationView("place_id_$i",lat,lng)

    fun createNewMapLocationView(i: Int=0,lat: Double=0.0,lng: Double=0.0)
            = NewMapLocationView("place_id_$i",lat,lng,"name_$i","region_$i, country_$i")

    fun createSearchResult(i: Int=0,term: String = "") = SearchResultView("place_id_$i",
        "$term-name_$i", "$term-region_$i, $term-country_$i")

//    fun createSearchResult(i: Int=0,term: String = "",termsNumber: Int) = SearchResultView("place_id_$i",
//        if (termsNumber>0)"$term-name_$i" else "",
//        if (termsNumber>=3)"$term-region_$i, $term-country_$i" else if(termsNumber==2) "$term-region_$i" else "")

    fun createPrediction(i: Int=0,term: String = "",termsNumber: Int=1)
            = Prediction("place_id_$i",(0 until termsNumber).map { "$term${terms[it]}$i" })

    private val terms = listOf("-name_","-region_","-country_","x")

}