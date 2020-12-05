package com.example.presentation_weather

import com.example.domain.use_cases.weather.WeatherInteractor
import com.example.presentation_weather.WeatherPresentationTestUtil.createWeatherHourForecastList
import com.example.presentation_weather.WeatherPresentationTestUtil.createWeatherHourForecastViewList
import com.example.presentation_weather.WeatherPresentationTestUtil.createWeatherTodayHourlyForecast
import com.example.presentation_weather_2.WeatherHourForecastView
import com.example.presentation_weather_2.hourly.WeatherHourlyForecastViewModel
import com.example.presentation_weather_2.hourly.WeatherHourlyForecastViewModelImpl
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProviderTrampoline
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherHourViewModelTests {

    @Mock lateinit var interactor: WeatherInteractor
    @Mock lateinit var emitter: ViewModelEmitter<List<WeatherHourForecastView>>

    private lateinit var viewModel: WeatherHourlyForecastViewModel

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)

        viewModel = WeatherHourlyForecastViewModelImpl(interactor,
            RxSchedulerProviderTrampoline(), emitter)
    }

    @Test
    fun getWeatherHoursForecast(){
        val placeId = "place_id"

        val expectedForecastForView = createWeatherHourForecastViewList(placeId,"Place Name")

        Mockito.`when`(interactor.getHourlyForecast(placeId))
            .thenReturn(Single.just(createWeatherHourForecastList()))

        viewModel.getWeatherHours(placeId)

        Mockito.verify(emitter).post(expectedForecastForView)
    }

}