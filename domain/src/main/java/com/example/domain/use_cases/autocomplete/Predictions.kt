package com.example.domain.use_cases.autocomplete

data class Predictions(val searchTerm: String,val locations: List<Prediction>)

data class Prediction(val placeId: String,val terms: List<String>)