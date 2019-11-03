package com.example.weather2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, FragmentLocation())
        ft.commit()
        //val tds = TestDarkSky()
        //tds.get()

      // val tl = TestLocation()
        //tl.get(this)
        //DaggerMyComponent.create().inject(this)
       // locationViewModel.initialize(this)
        Log.v("","")
    }
}
