package com.example.presentation_weather

import com.example.domain.use_cases.location.LocationInteractor
import com.example.domain.use_cases.weather.WeatherInteractor
import com.example.presentation_weather.WeatherPresentationTestUtil.createLocationsList
import com.example.presentation_weather.WeatherPresentationTestUtil.createLocationsViewList
import com.example.presentation_weather.WeatherPresentationTestUtil.createWeatherToday
import com.example.presentation_weather.WeatherPresentationTestUtil.createWeatherTodayView
import com.example.presentation_weather_2.LocationView
import com.example.presentation_weather_2.WeatherTodayView
import com.example.presentation_weather_2.main.WeatherMainForecastViewModel
import com.example.presentation_weather_2.main.WeatherMainForecastViewModelImpl
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProviderTrampoline
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherMainForecastViewModelTests {

    @Mock lateinit var weatherInteractor: WeatherInteractor
    @Mock lateinit var locationInteractor: LocationInteractor
    @Mock lateinit var locationsEmitter: ViewModelEmitter<List<LocationView>>
    @Mock lateinit var forecastEmitter: ViewModelEmitter<WeatherTodayView>
    @Mock lateinit var errorEmitter: ViewModelEmitter<String>
    @Mock lateinit var pendingEmitter: ViewModelEmitter<Unit>

    private lateinit var viewModel: WeatherMainForecastViewModel

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        viewModel = WeatherMainForecastViewModelImpl(weatherInteractor,locationInteractor,
            RxSchedulerProviderTrampoline(),locationsEmitter, forecastEmitter,
                    errorEmitter,pendingEmitter)
    }

    @Test
    fun getLocations(){
        val expectedLocationForView = createLocationsViewList()

        Mockito.`when`(locationInteractor.getStoredLocations())
            .thenReturn(Single.just(createLocationsList()))

        viewModel.start()

        Mockito.verify(locationsEmitter).post(expectedLocationForView)
    }

    @Test
    fun getForecast(){
        val place =  createLocationsViewList()[0]
        val placeId = place.placeId

        val expectedForecastView = createWeatherTodayView(placeId)

        createWeatherToday()

        Mockito.`when`(locationInteractor.getStoredLocations())
            .thenReturn(Single.just(createLocationsList()))

        Mockito.`when`(weatherInteractor.getForecast(placeId))
            .thenReturn(Single.just(createWeatherToday()))

        viewModel.start()

        viewModel.getWeather(placeId,false)

        Mockito.verify(forecastEmitter).post(expectedForecastView)
    }

}