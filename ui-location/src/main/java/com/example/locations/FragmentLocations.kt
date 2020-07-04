package com.example.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
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
    private lateinit var checkBoxSelectAll: CheckBox
    private lateinit var buttonDelete: ImageButton
    private var edit = false

    // used for moving items in list
    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            locationsAdapter.move(viewHolder.adapterPosition,target.adapterPosition)
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }
    })

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (requireActivity().application as ApplicationFeatureLocation).injectLocation(this)
        return inflater.inflate(R.layout.locations_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::locationsAdapter.isInitialized){
            locationsAdapter = LocationsAdapter(requireActivity(),viewModel,::selectedUpdated)
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

        buttonDelete = view.findViewById<ImageButton>(R.id.button_delete)
        buttonDelete.setOnClickListener{
            viewModel.deleteLocations(locationsAdapter.items)
            locationsAdapter.deleteSelected()
        }

        checkBoxSelectAll = view.findViewById<CheckBox>(R.id.check_box_select_all)

        checkBoxSelectAll.setOnCheckedChangeListener { buttonView, isChecked ->
                if (buttonView.isPressed) {
                    locationsAdapter.allChecked(isChecked)
                    selectedUpdated(isChecked, isChecked)
                }
            }

//        view.findViewById<FloatingActionButton>(R.id.fab_edit).setOnClickListener{
//            edit = !edit
//            if (edit) {
//                editingEnabled(view)
//            }else {
//                editingDisabled(view)
//            }
//        }
    }

    private fun createRecyclerView(view: View){
        locationsList = view.findViewById<RecyclerView>(R.id.list_locations)
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        locationsList.layoutManager = linearLayoutManager
        if (locationsList.adapter == null)
            locationsList.adapter = locationsAdapter

    }

    override fun onResume() {
        super.onResume()
        //viewModel.init()
        viewModel.getLocationsObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { locationsAdapter.items = it.toMutableList() }
        viewModel.getStoredLocations()
    }

    private fun editingEnabled(view: View){
        view.findViewById<FloatingActionButton>(R.id.fab_edit).setImageResource(R.drawable.ic_done)
        locationsAdapter.editingEnabled = true
        view.findViewById<ConstraintLayout>(R.id.top_bar_locations).visibility = View.INVISIBLE
        view.findViewById<ConstraintLayout>(R.id.top_bar_locations_editing).visibility = View.VISIBLE
        itemTouchHelper.attachToRecyclerView(locationsList)
        for (i in 0 until (locationsAdapter.itemCount))
            locationsAdapter.enableEditing(locationsList.findViewHolderForAdapterPosition(i))

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                editingDisabled(view)
            }
        })
    }

    private fun editingDisabled(view: View){
        view.findViewById<FloatingActionButton>(R.id.fab_edit).setImageResource(R.drawable.ic_edit)
        locationsAdapter.editingEnabled = false
        view.findViewById<ConstraintLayout>(R.id.top_bar_locations).visibility = View.VISIBLE
        view.findViewById<ConstraintLayout>(R.id.top_bar_locations_editing).visibility = View.INVISIBLE

        viewModel.updateLocationsOrder(locationsAdapter.items)
        itemTouchHelper.attachToRecyclerView(null)
        for (i in 0 until (locationsAdapter.itemCount))
            locationsAdapter.disableEditing(locationsList.findViewHolderForAdapterPosition(i))

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity!!.onBackPressed()
            }
        })
    }

    private fun selectedUpdated(allChecked: Boolean,someChecked: Boolean){
        checkBoxSelectAll.isChecked = allChecked
        buttonDelete.isEnabled = someChecked
    }


}