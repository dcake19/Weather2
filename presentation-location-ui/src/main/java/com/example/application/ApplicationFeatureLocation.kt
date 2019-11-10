package com.example.application

import androidx.fragment.app.Fragment

interface ApplicationFeatureLocation {

    fun injectLocation(fragment: Fragment)
}