package com.example.view_model.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class LocationTestRunner : AndroidJUnitRunner(){
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, LocationApplicationTest::class.java.name, context)
    }
}