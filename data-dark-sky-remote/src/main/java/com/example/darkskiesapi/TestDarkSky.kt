package com.example.darkskiesapi

import com.example.darkskiesapi.forecast.WeatherForecast
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TestDarkSky {

    val darkSkyApi: DarkSkyApiImpl

    init {
        darkSkyApi = DarkSkyApiImpl(RetrofitClient().getClient().create(RetrofitDarkSkyApi::class.java))
    }

    fun get(){

        val single = object : SingleObserver<WeatherForecast>{
            override fun onSuccess(t: WeatherForecast) {
                t
            }

            override fun onSubscribe(d: Disposable) {
                d
            }

            override fun onError(e: Throwable) {
                e
            }

        }

        darkSkyApi.getForecast(52.2053,0.1218)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(single)
    }

}