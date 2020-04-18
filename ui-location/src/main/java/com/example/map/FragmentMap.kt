package com.example.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.view_model.R

class FragmentMap: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.map_fragment,container,false)
    }

}