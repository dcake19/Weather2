package com.example.utils.schedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class RxSchedulerProviderTrampoline: RxSchedulerProvider {
    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

}