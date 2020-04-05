package com.example.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.util.rangeTo
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
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
    private lateinit var locationsList: RecyclerView
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
            val layout = view.findViewById<ConstraintLayout>(R.id.location_layout)
            edit = !edit
            //locationsAdapter.allowEditing = edit
            if (edit) {
                editingEnabled(layout)
               // locationsAdapter.enabledEditing = true
                view.findViewById<FloatingActionButton>(R.id.fab_edit)
                    .setImageResource(R.drawable.ic_done)
            }else {
                editingDisabled(layout)
              //  locationsAdapter.enabledEditing = false
                view.findViewById<FloatingActionButton>(R.id.fab_edit)
                    .setImageResource(R.drawable.ic_edit)
            }
        }
    }

    private fun createRecyclerView(view: View){
        locationsList = view.findViewById<RecyclerView>(R.id.list_locations)
       // locationsList.findview
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        locationsList.layoutManager = linearLayoutManager
        if (locationsList.adapter == null)
            locationsList.adapter = locationsAdapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder): Boolean {
               // val fromPosition = viewHolder.adapterPosition
               // val targetPosition = viewHolder.adapterPosition

                locationsAdapter.move(viewHolder.adapterPosition,target.adapterPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        })

        itemTouchHelper.attachToRecyclerView(locationsList)

    }

    override fun onResume() {
        super.onResume()
        //viewModel.init()
        viewModel.getLocationsObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { locationsAdapter.items = it.toMutableList() }
        viewModel.getStoredLocations()
    }

    private fun editingEnabled(layout: ConstraintLayout){
        for (i in 0 until (locationsAdapter.itemCount))
            locationsAdapter.enableEditing(
                locationsList.findViewHolderForAdapterPosition(i))
    }

    private fun editingDisabled(layout: ConstraintLayout){
        for (i in 0 until (locationsAdapter.itemCount))
            locationsAdapter.disableEditing(
                locationsList.findViewHolderForAdapterPosition(i))
    }
}