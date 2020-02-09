package com.example.utils.schedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class RxSchedulerProviderImpl: RxSchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }
}