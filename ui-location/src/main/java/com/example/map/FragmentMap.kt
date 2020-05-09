package com.example.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.application.ApplicationFeatureLocation
import com.example.presentation_location_view_model.map.MapLocationView
import com.example.presentation_location_view_model.map.MapLocationViewModel
import com.example.presentation_location_view_model.search.SearchLocationViewModel
import com.example.view_model.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FragmentMap: Fragment(){

    @Inject lateinit var mapLocationViewModel: MapLocationViewModel
    @Inject lateinit var searchLocationViewModel: SearchLocationViewModel

    private lateinit var map: GoogleMap
   // private val markers = mutableListOf<MarkerOptions>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity!!.application as ApplicationFeatureLocation).injectLocation(this)
        return inflater.inflate(R.layout.map_fragment,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync{onMapReady(it)}
    }

    override fun onResume() {
        super.onResume()
        mapLocationViewModel.getLocationsObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{onLocationsReady(it)}

    }

    private fun onMapReady(googleMap: GoogleMap){
        map = googleMap
        mapLocationViewModel.getStoredLocations()
        googleMap.mapType =  GoogleMap.MAP_TYPE_NORMAL
    }

    private fun onLocationsReady(locations: List<MapLocationView>){
        val latLngBoundsBuilder = LatLngBounds.Builder()

        locations.forEach {
            val latLng = LatLng(it.latitude,it.longitude)
            val mo = MarkerOptions().position(latLng)
            mo.title(it.placeId)
          //  markers.add(mo)
            latLngBoundsBuilder.include(latLng)
            map.addMarker(mo)
        }

        if (locations.isNotEmpty()){
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(),120))
        }

        map.setOnMarkerClickListener(object: GoogleMap.OnMarkerClickListener  {
            override fun onMarkerClick(p0: Marker?): Boolean {
                return true
            }
        })

    }

}