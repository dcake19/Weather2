package com.example.ui_weather_2.today

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.presentation_weather_2.main.WeatherMainForecastViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class LocationSetter: GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    companion object{
        const val LOCATION_REQUEST_CODE = 101
    }

    private var googleApiClient: GoogleApiClient? = null
    private var locationRequest: LocationRequest? = null

    private lateinit var activity: Activity
    lateinit var viewModel: WeatherMainForecastViewModel

    fun getLocation(activity: Activity,viewModel: WeatherMainForecastViewModel){
        this.activity = activity
        this.viewModel = viewModel
        googleApiClient = GoogleApiClient.Builder(activity)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        googleApiClient?.connect()
    }

    override fun onConnected(p0: Bundle?) {
        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = 1000
      //  locationRequest?.numUpdates = 1
        if(ActivityCompat.checkSelfPermission(activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)  == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,
                locationRequest,
                { onLocationChanged(it) }
            )
        }else{
            ActivityCompat.requestPermissions(
                activity,
                listOf(android.Manifest.permission.ACCESS_FINE_LOCATION).toTypedArray(),
                LOCATION_REQUEST_CODE)
        }
    }

    fun setLocation(){
        if (::activity.isInitialized && ::viewModel.isInitialized) {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient,
                    locationRequest, { onLocationChanged(it) }
                )
            }
        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private fun onLocationChanged(location: Location?) {
        googleApiClient?.disconnect()
        Log.v("new_location","lat: ${location?.latitude} , long: ${location?.longitude}")
        location?.let { viewModel.getForecast(it.latitude, it.longitude) }
    }

}