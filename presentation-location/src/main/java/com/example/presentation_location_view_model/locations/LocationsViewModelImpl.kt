package com.example.presentation_location_view_model.locations

import com.example.domain.Location
import com.example.domain.LocationInteractor
import com.example.utils.schedulers.RxSchedulerProvider
import com.example.utils.ViewModelEmitter
import io.reactivex.SingleObserver
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class LocationsViewModelImpl(private val locationInteractor: LocationInteractor,
                             private val scheduler: RxSchedulerProvider,
                             private val mapper: LocationsMapper,
                             private val locationsEmitter: ViewModelEmitter<List<LocationsView>>): LocationsViewModel {

    //private val locationsEmitter = ViewModelEmitter<List<LocationsView>>(true)

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
//        locationInteractor.getLocation(53.368491, -1.450158)
//            .observeOn(Schedulers.computation())
//            .subscribe(object : SingleObserver<Location>{
//                override fun onSuccess(t: Location) {
//                    //Log.v("view_model_remote","${t.name}  ${t.region}, ${t.country}")
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//
//            })
//
//        locationInteractor.getLocation(52.952804, -1.157791)
//            .observeOn(Schedulers.computation())
//            .subscribe(object : SingleObserver<Location>{
//                override fun onSuccess(t: Location) {
//                    //Log.v("view_model_remote","${t.name}  ${t.region}, ${t.country}")
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//
//            })
    }

    override fun getLocationsObservable(): Observable<List<LocationsView>> {
        return Observable.create{locationsEmitter.initEmitter(it)}
    }

    override fun getStoredLocations(){
        locationInteractor.getStoredLocations()
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.computation())
            .map {mapper.map(it)}
//            .map { locations -> locations.map { location -> LocationsView(location.placeId,
//                    location.position,location.name,location.region,location.country) }
//                .sortedBy { it.position }}
            .subscribe(object : SingleObserver<List<LocationsView>>{
                override fun onSuccess(t: List<LocationsView>) {
                   locationsEmitter.post(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

            })
    }

    override fun deleteLocation(placeId: String) {
        locationInteractor.deleteLocation(placeId)
    }
}