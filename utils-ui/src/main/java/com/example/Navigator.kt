package com.example

import android.view.View
import androidx.navigation.Navigation

interface Navigator {
    fun popBackStack(view: View){
        Navigation.findNavController(view).popBackStack()
    }
}