package com.example.presentation_location_view_model.locations

import com.example.domain.Location
import com.example.domain.LocationInteractor
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LocationsViewModelImpl(private val locationInteractor: LocationInteractor):
    LocationsViewModel {

    override fun init() {
        locationInteractor.getLocation(52.2053,0.1218)
        .observeOn(Schedulers.computation())
            .subscribe(object : SingleObserver<Location>{
                override fun onSuccess(t: Location) {
                    //Log.v("view_model_remote","${t.name}  ${t.region}, ${t.country}")
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

            })
        locationInteractor.getLocation(53.368491, -1.450158)
            .observeOn(Schedulers.computation())
            .subscribe(object : SingleObserver<Location>{
                override fun onSuccess(t: Location) {
                    //Log.v("view_model_remote","${t.name}  ${t.region}, ${t.country}")
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

            })

        locationInteractor.getLocation(52.952804, -1.157791)
            .observeOn(Schedulers.computation())
            .subscribe(object : SingleObserver<Location>{
                override fun onSuccess(t: Location) {
                    //Log.v("view_model_remote","${t.name}  ${t.region}, ${t.country}")
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
            .observeOn(Schedulers.computation())
            .subscribe(object : SingleObserver<List<Location>>{
                override fun onSuccess(t: List<Location>) {
                   // t.forEach { Log.v("view_model_local","${it.name}  ${it.region}, ${it.country}") }
                }

                override fun onSubscribe(d: Disposable) {
                    d
                }

                override fun onError(e: Throwable) {
                    e
                }

            })
    }

    override fun deleteLocation(placeId: String) {
        locationInteractor.deleteLocation(placeId)
    }
}