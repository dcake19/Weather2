package com.example.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.ApplicationFeatureLocation
import com.example.presentation_location_view_model.map.MapLocationView
import com.example.presentation_location_view_model.map.MapLocationViewModel
import com.example.presentation_location_view_model.map.NewMapLocationView
import com.example.presentation_location_view_model.search.SearchLocationViewModel
import com.example.presentation_location_view_model.search.SearchResultsView
import com.example.view_model.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FragmentMap: Fragment(){

    @Inject lateinit var mapLocationViewModel: MapLocationViewModel
    @Inject lateinit var searchLocationViewModel: SearchLocationViewModel

    private lateinit var mapSearchAdapter: MapSearchAdapter

    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireActivity().application as ApplicationFeatureLocation).injectLocation(this)
        return inflater.inflate(R.layout.map_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.button_back).setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }

        if (!::mapSearchAdapter.isInitialized)
            mapSearchAdapter = MapSearchAdapter(context,mapLocationViewModel)

        view.findViewById<SearchView>(R.id.search_location).apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val term = query ?: ""
                    if (term.isNotBlank())
                        mapLocationViewModel.getNewLocationBySearchTerm(term)
                    else
                        mapSearchAdapter.clear()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val term = newText ?: ""
                    if (term.isNotBlank())
                        searchLocationViewModel.searchLocation(term)
                    else
                        mapSearchAdapter.clear()
                    return false
                }
            })
        }

        createRecyclerView(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync{onMapReady(it)}
    }

    override fun onResume() {
        super.onResume()
        searchLocationViewModel.getSearchResultsObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onSearchResultsReady(it) }
        searchLocationViewModel.getLocationAddedObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onLocationAdded() }
        mapLocationViewModel.getNewLocationObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onNewLocationReady(it) }
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

    private fun createRecyclerView(view: View){
        view.findViewById<RecyclerView>(R.id.list_map_search_items).let {
            it.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            if (it.adapter==null) it.adapter = mapSearchAdapter
        }
    }

    private fun onSearchResultsReady(searchResultsView: SearchResultsView){
        view?.findViewById<CardView>(R.id.card_map_search_items)?.visibility =
            if (searchResultsView.searchResults.isNotEmpty()) View.VISIBLE else View.GONE
        mapSearchAdapter.searchResultsView = searchResultsView
    }

    private fun onNewLocationReady(newMapLocationView: NewMapLocationView){
        view?.findViewById<CardView>(R.id.card_map_search_items)?.visibility = View.GONE
        val target = CameraPosition.builder()
            .target(LatLng(newMapLocationView.latitude,newMapLocationView.longitude))
            .zoom(10f).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(target))

        view?.findViewById<CardView>(R.id.card_search_location)?.visibility = View.VISIBLE
        view?.findViewById<TextView>(R.id.text_place_name_search)?.text = newMapLocationView.placeName
        view?.findViewById<TextView>(R.id.text_region_name_search)?.text = newMapLocationView.placeRegion
        view?.findViewById<ImageButton>(R.id.button_add_location)?.setOnClickListener {
            view?.findViewById<CardView>(R.id.card_search_location)?.visibility = View.INVISIBLE
            searchLocationViewModel.addLocation(newMapLocationView.placeId)
        }
    }

    private fun onLocationAdded(){
        view?.findViewById<CardView>(R.id.card_search_location)?.visibility = View.INVISIBLE
        view?.findViewById<TextView>(R.id.text_place_name_search)?.text = ""
        view?.findViewById<TextView>(R.id.text_region_name_search)?.text = ""
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync{onMapReady(it)}
    }

}