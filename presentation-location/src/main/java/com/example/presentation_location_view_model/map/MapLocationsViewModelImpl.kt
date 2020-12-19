package com.example.presentation_location_view_model.map

import com.example.domain.use_cases.location.LocationInteractor

import com.example.utils.ViewModelEmitter
import com.example.utils.schedulers.RxSchedulerProvider
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class MapLocationsViewModelImpl(private val locationInteractor: LocationInteractor,
                                private val scheduler: RxSchedulerProvider,
                                private val mapper: MapLocationMapper,
                                private val locationsEmitter: ViewModelEmitter<List<MapLocationView>>,
                                private val newlocationEmitter: ViewModelEmitter<NewMapLocationView>,
                                private val errorEmitter: ViewModelEmitter<String>): MapLocationViewModel {

    private val newLocationByPlaceIdCached = HashMap<String,NewMapLocationView>()
    private val newLocationBySearchTermIdCached = HashMap<String,NewMapLocationView>()
    private var pendingSearch = ""

    override fun getLocationsObservable(): Observable<List<MapLocationView>> {
        return Observable.create{locationsEmitter.initEmitter(it)}
    }

    override fun getNewLocationObservable(): Observable<NewMapLocationView> {
        return Observable.create{newlocationEmitter.initEmitter(it)}
    }

    override fun getErrorObservable(): Observable<String> {
        return Observable.create { errorEmitter.initEmitter(it) }
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
                    errorEmitter.post(e.message)
                }
            })
    }

    override fun getNewLocationByPlaceId(placeId: String) {
        if (pendingSearch!=placeId) {
            val location = newLocationByPlaceIdCached[placeId]
            if (location == null) {
                locationInteractor.getLocationByPlaceId(placeId)
                    .subscribeOn(scheduler.computation())
                    .observeOn(scheduler.computation())
                    .map { mapper.mapNewLocation(it) }
                    .subscribe(object : SingleObserver<NewMapLocationView> {
                        override fun onSuccess(t: NewMapLocationView) {
                            newLocationByPlaceIdCached[placeId] = t
                            if (pendingSearch == placeId) {
                                pendingSearch = ""
                                newlocationEmitter.post(t)
                            }
                        }

                        override fun onSubscribe(d: Disposable) {
                            pendingSearch = placeId
                        }

                        override fun onError(e: Throwable) {
                            pendingSearch = ""
                            errorEmitter.post(e.message)
                        }

                    })
            } else {
                newlocationEmitter.post(location)
            }
        }
    }

    override fun getNewLocationBySearchTerm(term: String) {
        if (pendingSearch!=term) {
            val location = newLocationBySearchTermIdCached[term]
            if (location == null) {
                locationInteractor.getLocationByName(term)
                    .subscribeOn(scheduler.computation())
                    .observeOn(scheduler.computation())
                    .map { mapper.mapNewLocation(it) }
                    .subscribe(object : SingleObserver<NewMapLocationView> {
                        override fun onSuccess(t: NewMapLocationView) {
                            newLocationBySearchTermIdCached[term] = t
                            if (pendingSearch == term) {
                                newlocationEmitter.post(t)
                                pendingSearch = ""
                            }
                        }

                        override fun onSubscribe(d: Disposable) {
                            pendingSearch = term
                        }

                        override fun onError(e: Throwable) {
                            pendingSearch = ""
                            errorEmitter.post(e.message)
                        }
                    })
            } else {
                newlocationEmitter.post(location)
            }
        }
    }
}