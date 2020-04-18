package com.example.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.application.ApplicationFeatureLocation
import com.example.presentation_location_view_model.search.SearchLocationViewModel
import com.example.view_model.R
import javax.inject.Inject

class FragmentSearch: Fragment() {

    @Inject lateinit var viewModel: SearchLocationViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity!!.application as ApplicationFeatureLocation).injectLocation(this)
        return inflater.inflate(R.layout.search_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel.searchLocation("lon")

        view.findViewById<ImageButton>(R.id.button_back).setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }


        view.findViewById<ImageButton>(R.id.button_map).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_search_to_map)
        }

        view.findViewById<SearchView>(R.id.search_location).isSelected = true
    }

}