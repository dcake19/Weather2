package com.example.presentation_location_view_model.search

data class SearchResultsView(val term: String,val searchResults: List<SearchResultView>)

data class SearchResultView(val placeId: String,val placeName: String,val placeRegion: String)