package com.example.presentation_weather

import com.example.domain.use_cases.weather.*
import com.example.presentation_weather_2.*
import com.example.presentation_weather_2.constants.NORTH
import com.example.presentation_weather_2.constants.NORTH_EAST

object WeatherPresentationTestUtil {
    private val timestamp = 1606730400

    fun createWeatherHourForecastList() = listOf(
        WeatherHourForecast(timestamp,804,16.93f, 15.79f,0f,
            4.27f,44,90,"overcast clouds"),
        WeatherHourForecast(timestamp+3600,500,18.94f,18.11f,
            0.37f,4.24f,46,93,"light rain"),
        WeatherHourForecast(timestamp+3600*2,804,19.36f,18.67f,
            0f,4.41f,40,95,"overcast clouds"),
        WeatherHourForecast(timestamp+3600*3,500,21.84f,
            21.08f,0.67f,4.77f,40,96,"light rain")
    )

    fun createWeatherDailyForecastList() = listOf(
        WeatherDayForecast(timestamp+3600*24,501,
            20.77f,17.93f,5.02f,
            timestamp+3600*(24+2),timestamp+3600*(24+6),4.77f,
            40, 93,
            1016,84,"moderate rain"),
        WeatherDayForecast(timestamp+3600*24*2,502,
            21.53f,16.91f,0f,
            timestamp+3600*(24*2+2),timestamp+3600*(24*2+6),
            3.17f,30, 100,
            1011,77,"heavy intensity rain")
    )

    fun createWeatherToday() = WeatherToday(timestamp,500,16.93f,
        15.91f,0.26f,timestamp-3600*2,timestamp+3600*6,
        4.1f, 20,90,1016,93,"light rain",
        createWeatherTodayHourlyForecast(), createWeatherTodayDailyForecast())

    fun createWeatherTodayHourlyForecast() = listOf(
        WeatherTodayHourlyForecast(timestamp,804,16.93f,0f),
        WeatherTodayHourlyForecast(timestamp+3600,500,18.94f,0.37f),
        WeatherTodayHourlyForecast(timestamp+3600*2,804,19.36f,0f),
        WeatherTodayHourlyForecast(timestamp+3600*3,500,21.84f,0.67f)
    )

    fun createWeatherTodayDailyForecast() = listOf(
        WeatherTodayDailyForecast(timestamp+3600*24,501,
            20.77f,17.93f,5.02f),
        WeatherTodayDailyForecast(timestamp+3600*24*2,502,
            21.53f,16.91f,0f)
    )

 //   fun createWeatherTodayView(placeId: String,placeName: String,dateTime: String) =
  //      WeatherTodayView(placeId,placeName,dateTime)

  //  fun createWeatherTodayHourlyForecastViewList(times: List<String>) = listOf(WeatherTodayHourlyForecastView(times[0],804,16.93f,0f))

    // timestamp = 1606730400
    // 2020-11-30 10:00:00 GMT
    fun createWeatherTodayView(placeId: String,placeName: String): WeatherTodayView{
        return WeatherTodayView(placeId,placeName,"Mon 30 November 10:00",500,
            "17\u00B0C","16\u00B0C","0 mm","8:00","16:00",
            "4.1 m/s",NORTH,"90%","1016 hPa", "93%",
            "light rain", createWeatherTodayHourly(), createWeatherTodayDaily())
    }

    fun createWeatherTodayHourly(): List<WeatherTodayHourlyForecastView>{
        return listOf(
            WeatherTodayHourlyForecastView("10:00",804,"17\u00B0C","0 mm"),
            WeatherTodayHourlyForecastView("11:00",500,"19\u00B0C","0 mm"),
            WeatherTodayHourlyForecastView("12:00",804,"19\u00B0C","0 mm"),
            WeatherTodayHourlyForecastView("13:00",500,"22\u00B0C","1 mm"))
    }

    fun createWeatherTodayDaily(): List<WeatherTodayDailyForecastView>{
        return listOf(
            WeatherTodayDailyForecastView("Tue",501,"21\u00B0C","18\u00B0C","5 mm"),
            WeatherTodayDailyForecastView("Wed",5012,"22\u00B0C","17\u00B0C","0 mm")
        )
    }

    fun createWeatherHourForecastViewList(placeId: String,placeName: String): List<WeatherHourForecastView>{
        return listOf(
            WeatherHourForecastView(placeId,"10:00",804,
            "17\u00B0C","16\u00B0C","0 mm","4.3 m/s",
                NORTH_EAST,"90%","overcast clouds"),
            WeatherHourForecastView(placeId,"11:00",500,
                "19\u00B0C","18\u00B0C","0 mm","4.2 m/s",
                NORTH_EAST,"93%","light rain"),
            WeatherHourForecastView(placeId,"12:00",804,
                "19\u00B0C","19\u00B0C","0 mm","4.4 m/s",
                NORTH_EAST,"95%","overcast clouds"),
            WeatherHourForecastView(placeId,"13:00",500,
                "22\u00B0C","21\u00B0C","1 mm","4.8 m/s",
                NORTH_EAST,"96%","light rain"))
    }

    fun createWeatherDayForecastViewList(placeId: String,placeName: String): List<WeatherDayForecastView>{
        return listOf(
            WeatherDayForecastView(placeId,"Tue 1 December",501,
                "21\u00B0C","18\u00B0C","5 mm",
                "8:00","16:00","4.8 m/s", NORTH_EAST,"93%",
                "1016 hPa","84%","moderate rain"),
            WeatherDayForecastView(placeId,"Wed 2 December",502,
                "22\u00B0C","17\u00B0C","0 mm",
                "8:00","16:00","3.2 m/s", NORTH_EAST,"100%",
                "1011 hPa","77%","heavy intensity rain")
        )
    }

}