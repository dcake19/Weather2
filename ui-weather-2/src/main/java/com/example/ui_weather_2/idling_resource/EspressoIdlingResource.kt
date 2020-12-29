package com.example.ui_weather_2.idling_resource

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    @JvmStatic var countingIdlingResource: CountingIdlingResource? = null

    fun createIdlingResource(){
        countingIdlingResource = CountingIdlingResource(RESOURCE)
    }

    fun increment(){
        countingIdlingResource?.increment()
    }

    fun decrement(){
        if (countingIdlingResource?.isIdleNow==false){
            countingIdlingResource?.decrement()
        }
    }
}