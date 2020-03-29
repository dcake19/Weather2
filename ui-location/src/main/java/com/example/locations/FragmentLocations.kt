package com.example.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.ApplicationFeatureLocation
import com.example.presentation_location_view_model.locations.LocationsViewModel
import com.example.view_model.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FragmentLocations: Fragment() {

    @Inject lateinit var viewModel: LocationsViewModel
    private lateinit var locationsAdapter: LocationsAdapter
    private var edit = false

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity!!.application as ApplicationFeatureLocation).injectLocation(this)
        return inflater.inflate(R.layout.locations_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::locationsAdapter.isInitialized){
            locationsAdapter = LocationsAdapter(activity!!,viewModel)
        }
        createRecyclerView(view)

        view.findViewById<ImageButton>(R.id.button_back).setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }

        view.findViewById<ImageButton>(R.id.button_add).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_locations_to_search)
        }

        view.findViewById<ImageButton>(R.id.button_settings).setOnClickListener{

        }

        view.findViewById<FloatingActionButton>(R.id.fab_edit).setOnClickListener{
            edit = !edit
            if (edit)
                view.findViewById<FloatingActionButton>(R.id.fab_edit).setImageResource(R.drawable.ic_done)
            else
                view.findViewById<FloatingActionButton>(R.id.fab_edit).setImageResource(R.drawable.ic_edit)
        }
    }

    private fun createRecyclerView(view: View){
        val locationsList = view.findViewById<RecyclerView>(R.id.list_locations)
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        locationsList.layoutManager = linearLayoutManager
        if (locationsList.adapter == null)
            locationsList.adapter = locationsAdapter
    }

    override fun onResume() {
        super.onResume()
       // viewModel.init()
        viewModel.getLocationsObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { locationsAdapter.items = it.toMutableList() }
        viewModel.getStoredLocations()
    }
}