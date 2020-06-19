package com.example.data_repository_location

import com.example.data_repository_location.auto_complete.AutoCompleteDataNetwork
import com.example.data_repository_location.auto_complete.AutoCompleteRepositoryImpl
import com.example.domain.autocomplete.Predictions
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*

class AutoCompleteRepositoryTests {

    private lateinit var repository: AutoCompleteRepositoryImpl

    @Mock lateinit var network: AutoCompleteDataNetwork

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repository = AutoCompleteRepositoryImpl(network)
    }

    @Test
    fun searchLocations(){
        val term = "term"

        val networkPredictions = (1..5).map { RepositoryTestUtil.createPredictionData(it,3) }
        val predictionsExpected = Predictions(term,(1..5).map { RepositoryTestUtil.createPrediction(it,3) })

        `when`(network.getAutocompleteLocation(term)).thenReturn(Single.just(networkPredictions))

        val predictionsActual = repository.searchLocations(term).test().values()[0]

        assertThat(predictionsActual, `is`(predictionsExpected))
    }

}