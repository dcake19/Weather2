package com.example.view_model

import android.content.Context
import com.example.domain.Location
import io.reactivex.Single

interface LocationViewModel {
    fun init()

    fun getStoredLocations()
}