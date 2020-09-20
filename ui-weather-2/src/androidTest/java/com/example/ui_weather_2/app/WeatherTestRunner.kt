package com.example.ui_weather_2.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class WeatherTestRunner  : AndroidJUnitRunner(){
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, WeatherApplicationTest::class.java.name, context)
    }
}