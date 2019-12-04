package com.example.locations

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation_location_view_model.locations.LocationsPresentation
import com.example.presentation_location_view_model.locations.LocationsViewModel
import com.example.view_model.R

class LocationsAdapter(private val context: Context,
                       private val viewModel: LocationsViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var items = mutableListOf<LocationsPresentation>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LocationViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.locations_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LocationViewHolder -> holder.setData(items[position])
        }
    }

    inner class LocationViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun setData(locationsPresentation: LocationsPresentation){
            itemView.findViewById<TextView>(R.id.location_text_place_name).text = locationsPresentation.name
            itemView.findViewById<TextView>(R.id.location_text_region_name).text =
                context.getString(R.string.region_country, locationsPresentation.region,locationsPresentation.country)

        }
    }

}