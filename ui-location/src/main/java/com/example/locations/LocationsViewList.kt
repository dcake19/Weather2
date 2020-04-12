package com.example.locations

import com.example.presentation_location_view_model.locations.LocationsView

class LocationsViewList(val locations: MutableList<LocationsView>): MutableList<LocationsView> by locations {

    fun getChanged(fromPosition: Int,toPosition: Int): List<LocationsView>{
        locations[fromPosition].position = toPosition

        var changed: List<LocationsView>? = null
        if (fromPosition<toPosition){
            for (i in fromPosition+1 until toPosition+1){
                locations[i].position++
            }
            changed = locations.filter { it.position in fromPosition..toPosition }
        }else if (toPosition<fromPosition){
            for (i in fromPosition-1 downTo toPosition){
                locations[i].position--
            }
            changed = locations.filter { it.position in toPosition..fromPosition+1 }
        }

        locations.sortBy { it.position }

        return changed?: emptyList()
    }

    fun move(fromPosition: Int,toPosition: Int){

    }

}