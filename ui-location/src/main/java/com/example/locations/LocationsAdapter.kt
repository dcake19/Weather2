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

    var items = mutableListOf<LocationsView>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var editingEnabled = false

    fun move(fromPosition: Int,toPosition: Int){
        val movedItem = items.removeAt(fromPosition)
        items.add(toPosition,movedItem)
        notifyItemMoved(fromPosition, toPosition)
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

    fun enableEditing(viewHolder: RecyclerView.ViewHolder?){
        if (viewHolder!=null && viewHolder is LocationViewHolder){
            viewHolder.editingEnabled(true)
        }
    }

    fun disableEditing(viewHolder: RecyclerView.ViewHolder?){
        if (viewHolder!=null && viewHolder is LocationViewHolder){
            viewHolder.editingDisabled()
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

        fun setData(locationsView: LocationsView){
            if (editingEnabled) {
                val checkBox = itemView.findViewById<CheckBox>(R.id.check_box_selected)
                editingEnabled(false)
                checkBox.isChecked = locationsView.selected
            }
            itemView.findViewById<CheckBox>(R.id.check_box_selected)
                .setOnCheckedChangeListener { buttonView, isChecked ->
                    locationsView.selected = isChecked
                    allSelected(!items.any{ !it.selected } , items.any { it.selected })
                }
            itemView.findViewById<TextView>(R.id.location_text_place_name).text = locationsView.name
            itemView.findViewById<TextView>(R.id.location_text_region_name).text =
                context.getString(R.string.region_country, locationsView.region,locationsView.country)
        }

        fun editingEnabled(animate: Boolean){
            val checkBox = itemView.findViewById<CheckBox>(R.id.check_box_selected)
            checkBox.visibility = View.VISIBLE

            val arrowsLayout = itemView.findViewById<ConstraintLayout>(R.id.layout_arrows)
            arrowsLayout.visibility = View.VISIBLE

            val layout = itemView.findViewById<ConstraintLayout>(R.id.location_layout)
            val constraint = ConstraintSet()
            constraint.clone(layout)
            constraint.clear(R.id.location_text_place_name,ConstraintSet.START)
            constraint.connect(R.id.location_text_place_name,ConstraintSet.START,R.id.check_box_selected,ConstraintSet.END)
            constraint.clear(R.id.location_text_region_name,ConstraintSet.START)
            constraint.connect(R.id.location_text_region_name,ConstraintSet.START,R.id.check_box_selected,ConstraintSet.END)

            if (animate) {
                val checkBoxAnimator = ObjectAnimator.ofFloat(checkBox,"alpha",0f,1f)
                checkBoxAnimator.start()
                val arrowsAnimator = ObjectAnimator.ofFloat(arrowsLayout,"alpha",0f,1f)
                arrowsAnimator.start()
                val transition = ChangeBounds()
                transition.duration = 300
                TransitionManager.beginDelayedTransition(layout, transition)
            }else{
                checkBox.alpha = 1f
                arrowsLayout.alpha = 1f
            }
            constraint.applyTo(layout)
        }

        fun editingDisabled(){
            val checkBox = itemView.findViewById<CheckBox>(R.id.check_box_selected)
            val checkBoxAnimator = ObjectAnimator.ofFloat(checkBox,"alpha",1f,0f)
            checkBoxAnimator.addListener(object :Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    checkBox.visibility = View.INVISIBLE
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            checkBoxAnimator.start()
            val arrowsLayout = itemView.findViewById<ConstraintLayout>(R.id.layout_arrows)
            val arrowsAnimator = ObjectAnimator.ofFloat(arrowsLayout,"alpha",1f,0f)
            arrowsAnimator.start()
            arrowsAnimator.addListener(object :Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    arrowsLayout.visibility = View.INVISIBLE
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })

            val layout = itemView.findViewById<ConstraintLayout>(R.id.location_layout)
            val constraint = ConstraintSet()
            constraint.clone(layout)
            constraint.clear(R.id.location_text_place_name,ConstraintSet.START)
            constraint.connect(R.id.location_text_place_name,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START)
            constraint.clear(R.id.location_text_region_name,ConstraintSet.START)
            constraint.connect(R.id.location_text_region_name,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START)
            val transition = ChangeBounds()
            transition.duration = 300
            TransitionManager.beginDelayedTransition(layout, transition)

            constraint.applyTo(layout)
        }
    }

}