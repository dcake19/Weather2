package com.example.utils

import io.reactivex.ObservableEmitter

class ViewModelEmitter<T>(var cacheValue: Boolean = false) {

    private var observableEmitter: ObservableEmitter<T>? = null
    private var value: T? = null

    fun onNext(): Boolean{
        val currentValue = value
        if (currentValue != null && observableEmitter!=null){
            observableEmitter?.onNext(currentValue)
            if (!cacheValue) value = null
            return true
        }
        return false
    }

    fun initEmitter(emitter: ObservableEmitter<T>){
        observableEmitter = emitter
    }

    fun post(t: T?){
        if (t!=null) {
            value = t
            onNext()
        }
    }

    fun stopEmitting(){
        observableEmitter = null
    }

    fun clear(){
        value = null
    }
}