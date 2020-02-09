package com.example.utils.schedulers

import io.reactivex.Scheduler

interface RxSchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
}