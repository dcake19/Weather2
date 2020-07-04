package com.example.view_model.app

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.application.ApplicationFeatureLocation

class LocationApplicationTest: Application(), ApplicationFeatureLocation {

    override fun injectLocation(fragment: Fragment) {

    }

}