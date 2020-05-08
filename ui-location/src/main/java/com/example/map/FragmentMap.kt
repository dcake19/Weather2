package com.example.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.view_model.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.Marker

class FragmentMap: Fragment(), OnMapReadyCallback{ //}, GoogleMap.OnMarkerClickListener{

    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.map_fragment,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap(view)
    }

    private fun setupMap(view: View){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
       // val mapFragment = activity!!.supportFragmentManager.findFragmentById(R.id.map) //as SupportMapFragment
        val x = 0

       mapFragment.getMapAsync(this)
      //  val googleMapView = view.findViewById<MapView>(R.id.view_map)//childFragmentManager.findFragmentById(R.id.map) as MapFragment
     //   googleMapView.getMapAsync { mapReady(it) }
    }

    private fun mapReady(googleMap: GoogleMap){
        map = googleMap
        googleMap.mapType =  GoogleMap.MAP_TYPE_NORMAL

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap!=null) {
            map = googleMap
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }
    // override fun onMapReady(p0: GoogleMap?) {

   // }

   // override fun onMarkerClick(p0: Marker?): Boolean {
//       return true
  //  }


}