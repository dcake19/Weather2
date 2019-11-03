package com.example.weather2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.view_model.LocationViewModel
import com.example.view_model.R
import javax.inject.Inject

class FragmentLocation: Fragment() {

    @Inject lateinit var viewModel: LocationViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity!!.application as WeatherApplication).inject(this)
        return inflater.inflate(R.layout.location_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()

        view.findViewById<Button>(R.id.button).setOnClickListener{
            viewModel.getStoredLocations()
        }
    }

}