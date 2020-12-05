package com.example.presentation_weather

import com.example.domain.use_cases.weather.WeatherInteractor
import com.example.presentation_weather.WeatherPresentationTestUtil.createWeatherDailyForecastList
import com.example.presentation_weather.WeatherPresentationTestUtil.createWeatherDayForecastViewList
import com.example.presentation_weather_2.WeatherDayForecastView
import com.example.presentation_weather_2.daily.WeatherDailyForecastViewModel
import com.example.presentation_weather_2.daily.WeatherDailyForecastViewModelImpl
import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProviderTrampoline
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherDayViewModelTests {

    @Mock
    lateinit var interactor: WeatherInteractor
    @Mock
    lateinit var emitter: ViewModelEmitter<List<WeatherDayForecastView>>

    private lateinit var viewModel: WeatherDailyForecastViewModel

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)

        viewModel = WeatherDailyForecastViewModelImpl(interactor,
            RxSchedulerProviderTrampoline(), emitter)
    }

    @Test
    fun getWeatherDaysForecast(){
        val placeId = "place_id"

        val expectedForecastForView = createWeatherDayForecastViewList(placeId)

        Mockito.`when`(interactor.getDailyForecast(placeId))
            .thenReturn(Single.just(createWeatherDailyForecastList()))

        viewModel.getWeatherDays(placeId)

        Mockito.verify(emitter).post(expectedForecastForView)
    }
}