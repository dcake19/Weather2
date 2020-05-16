package com.example.presentation_location_view_model.map

import com.example.domain.LocationInteractor

import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProvider
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class MapLocationsViewModelImpl(private val locationInteractor: LocationInteractor,
                                private val scheduler: RxSchedulerProvider,
                                private val mapper: MapLocationMapper,
                                private val locationsEmitter: ViewModelEmitter<List<MapLocationView>>,
                                private val newlocationEmitter: ViewModelEmitter<NewMapLocationView>): MapLocationViewModel {

    override fun getLocationsObservable(): Observable<List<MapLocationView>> {
        return Observable.create{locationsEmitter.initEmitter(it)}
    }

    override fun getNewLocationObservable(): Observable<NewMapLocationView> {
        return Observable.create{newlocationEmitter.initEmitter(it)}
    }

    override fun getStoredLocations() {
        locationInteractor.getStoredLocations()
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.computation())
            .map {mapper.map(it)}
            .subscribe(object : SingleObserver<List<MapLocationView>> {
                override fun onSuccess(t: List<MapLocationView>) {
                    locationsEmitter.post(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun getNewLocationByPlaceId(placeId: String) {
        locationInteractor.getLocationByPlaceId(placeId)
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.computation())
            .map { mapper.mapNewLocation(it) }
            .subscribe(object :SingleObserver<NewMapLocationView>{
                override fun onSuccess(t: NewMapLocationView) {
                    newlocationEmitter.post(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

            })
    }

    override fun getNewLocationBySearchTerm(term: String) {
        locationInteractor.getLocationByName(term)
            .subscribeOn(scheduler.computation())
            .observeOn(scheduler.computation())
            .map { mapper.mapNewLocation(it) }
            .subscribe(object :SingleObserver<NewMapLocationView>{
                override fun onSuccess(t: NewMapLocationView) {
                    newlocationEmitter.post(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }
            })
    }
}