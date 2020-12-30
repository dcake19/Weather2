package com.example.presentation_location_view_model.locations

import com.example.domain.use_cases.location.Location
import com.example.domain.use_cases.location.LocationInteractor
import com.example.utils.schedulers.RxSchedulerProvider
import com.example.utils.ViewModelEmitter
import io.reactivex.SingleObserver
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


class LocationsViewModelImpl(private val locationInteractor: LocationInteractor,
                             private val scheduler: RxSchedulerProvider,
                             private val mapper: LocationsMapper,
                             private val locationsEmitter: ViewModelEmitter<List<LocationsView>>,
                             private val errorEmitter: ViewModelEmitter<String>): LocationsViewModel {

    override fun init() {
        val observer = object : SingleObserver<Location>{
            override fun onSuccess(t: Location) {}
            override fun onSubscribe(d: Disposable) {}
            override fun onError(e: Throwable) {}
        }

//        locationInteractor.getLocation(52.2053,0.1218)
//        .observeOn(Schedulers.computation())
//            .subscribe()
//        locationInteractor.getLocation(53.368491, -1.450158)
//            .observeOn(Schedulers.computation())
//            .subscribe(observer)
//
//        locationInteractor.getLocation(52.952804, -1.157791)
//            .observeOn(Schedulers.computation())
//            .subscribe(observer)
//
//        locationInteractor.getLocation(51.472101, -0.091660)
//            .observeOn(Schedulers.computation())
//            .subscribe(observer)
//
//        locationInteractor.getLocation(52.468115, -1.872855)
//            .observeOn(Schedulers.computation())
//            .subscribe(observer)
//
//
//        locationInteractor.getLocation(53.450867, -2.231002)
//            .observeOn(Schedulers.computation())
//            .subscribe(observer)
//
//        locationInteractor.getLocation(51.462025, -2.595660)
//            .observeOn(Schedulers.computation())
//            .subscribe(observer)
//
//        locationInteractor.getLocation(53.785724, -1.516300)
//            .observeOn(Schedulers.computation())
//            .subscribe(observer)

        //locationInteractor.getLocation(55.945000, -3.185710)
           // .observeOn(Schedulers.computation())
           // .subscribe(observer)
    }

    override fun getLocationsObservable(): Observable<List<LocationsView>> {
        return Observable.create{locationsEmitter.initEmitter(it)}
    }

    override fun getErrorObservable(): Observable<String> {
        return Observable.create { errorEmitter.initEmitter(it) }
    }


    override fun getStoredLocations(){


            locationInteractor.getStoredLocations()
                .subscribeOn(scheduler.computation())
                .observeOn(scheduler.computation())
                .map { mapper.map(it) }
                .subscribe(object : SingleObserver<List<LocationsView>> {
                    override fun onSuccess(t: List<LocationsView>) {
                        locationsEmitter.post(t)
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        errorEmitter.post(e.message)
                    }
                })

    }

    override fun updateLocationsOrder(locations: List<LocationsView>) {
        locationInteractor.updateLocationsOrder(locations.map { it.placeId })
    }

    override fun deleteLocations(locations: List<LocationsView>) {
        locationInteractor.deleteLocations(locations.filter { it.selected }.map { it.placeId })
    }
}