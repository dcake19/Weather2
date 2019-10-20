package com.example.api_location

import com.example.api_location.autocomplete.LocationAutocompleteResponse
import com.example.api_location.locations.LocationResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TestLocation {

    val locationApi: LocationApiImpl

    init {
        locationApi = LocationApiImpl(RetrofitClient().getClient().create(RetrofitLocationApi::class.java))
    }

    fun get(){

        val single1 = object : SingleObserver<LocationResponse>{
            override fun onSuccess(t: LocationResponse) {
                t
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                e
            }

        }

        val single2 = object : SingleObserver<LocationResponse>{
            override fun onSuccess(t: LocationResponse) {
                t
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                e
            }

        }

        locationApi.getLocations("manchester")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(single1)

        locationApi.getLocations(52.2053,0.1218)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(single2)

        locationApi.getLocationsByPlaceId("ChIJ2_UmUkxNekgRqmv-BDgUvtk")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(single2)


        val single = object : SingleObserver<LocationAutocompleteResponse>{
            override fun onSuccess(t: LocationAutocompleteResponse) {
                t
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {
                e
            }

        }

        locationApi.getAutocompleteLocation("man")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(single)
    }

}