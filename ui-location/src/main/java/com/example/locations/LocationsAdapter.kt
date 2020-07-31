package com.example.locations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation_location_view_model.locations.LocationsView
import com.example.presentation_location_view_model.locations.LocationsViewModel
import com.example.view_model.R

class LocationsAdapter(private val context: Context,
                       private val viewModel: LocationsViewModel,
                       private val allSelected: (allChecked: Boolean,someSelected: Boolean) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    companion object {
        const val EDITABLE = 1
    }

    var items = mutableListOf<LocationsView>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var checkBoxVisibility = View.GONE

    fun move(fromPosition: Int,toPosition: Int){
        val movedItem = items.removeAt(fromPosition)
        items.add(toPosition,movedItem)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.locations_item, parent, false)
        (view as ViewGroup).layoutTransition.setDuration(300)
        return LocationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun toggleEditable(){
        checkBoxVisibility =
            if (checkBoxVisibility == View.GONE) {
                View.VISIBLE
            } else {
                View.GONE
            }
        notifyItemRangeChanged(0,items.size, EDITABLE)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LocationViewHolder -> holder.setData(items[position])
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payload: MutableList<Any>) {
        if (payload.isNotEmpty() && holder is LocationViewHolder) {
            // payload could be batched. If so, we only care about the final state.
            when (payload.last()) {
                1 -> holder.changeEditable()
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    fun allChecked(isChecked: Boolean){
        items.forEach { it.selected = isChecked }
        notifyDataSetChanged()
    }

    fun deleteSelected(){
        items = items.filterNot { it.selected }.toMutableList()
        notifyDataSetChanged()
    }

    inner class LocationViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        fun changeEditable(){
            itemView.findViewById<CheckBox>(R.id.check_box_selected).visibility = checkBoxVisibility
        }

        fun setData(locationsView: LocationsView){
            val checkBox = itemView.findViewById<CheckBox>(R.id.check_box_selected)
            itemView.findViewById<CheckBox>(R.id.check_box_selected).visibility = checkBoxVisibility
            checkBox.isChecked = locationsView.selected

            itemView.findViewById<CheckBox>(R.id.check_box_selected)
                .setOnCheckedChangeListener { buttonView, isChecked ->
                    locationsView.selected = isChecked
                    allSelected(!items.any{ !it.selected } , items.any { it.selected })
                }
            itemView.findViewById<TextView>(R.id.location_text_place_name).text = locationsView.name
            itemView.findViewById<TextView>(R.id.location_text_region_name).text =
                context.getString(R.string.region_country, locationsView.region,locationsView.country)
        }
    }

}