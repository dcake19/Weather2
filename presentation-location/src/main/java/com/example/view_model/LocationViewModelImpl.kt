package com.example.view_model

import android.util.Log
import com.example.domain.Location
import com.example.domain.LocationInteractor
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LocationViewModelImpl(private val locationInteractor: LocationInteractor): LocationViewModel {

    override fun init() {
        locationInteractor.getLocation(52.2053,0.1218)
        .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Location>{
                override fun onSuccess(t: Location) {
                    Log.v("view_model_remote","${t.name}  ${t.region}, ${t.country}")
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

            })
        locationInteractor.getLocation(53.368491, -1.450158)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Location>{
                override fun onSuccess(t: Location) {
                    Log.v("view_model_remote","${t.name}  ${t.region}, ${t.country}")
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

            })

        locationInteractor.getLocation(52.952804, -1.157791)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Location>{
                override fun onSuccess(t: Location) {
                    Log.v("view_model_remote","${t.name}  ${t.region}, ${t.country}")
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

            })
    }

    override fun getStoredLocations(){
        locationInteractor.getStoredLocations()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Location>>{
                override fun onSuccess(t: List<Location>) {
                    t.forEach { Log.v("view_model_local","${it.name}  ${it.region}, ${it.country}") }
                }

                override fun onSubscribe(d: Disposable) {
                    d
                }

                override fun onError(e: Throwable) {
                    e
                }

            })
    }

}