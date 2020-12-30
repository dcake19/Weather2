package com.example.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation_location_view_model.search.SearchLocationViewModel
import com.example.presentation_location_view_model.search.SearchResultView
import com.example.presentation_location_view_model.search.SearchResultsView
import com.example.utils_ui.getColor
import com.example.utils_ui.highlight
import com.example.view_model.R


class SearchAdapter(private val context: Context?,
                    private val viewModel: SearchLocationViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var searchResultsView: SearchResultsView? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun clear(){
        searchResultsView = null
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder((LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item, parent, false)))
    }

    override fun getItemCount(): Int {
        return searchResultsView?.searchResults?.size?:0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val searchResultView = searchResultsView?.searchResults?.get(position)
        if (searchResultView!=null)
        when(holder){
            is SearchViewHolder -> holder.setData(searchResultView)
        }
    }

    inner class SearchViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun setData(searchResultView: SearchResultView){
            itemView.findViewById<TextView>(R.id.location_text_place_name).text =
                highlight(searchResultView.placeName,searchResultsView?.term?:"",
                    getColor(context,R.color.dark_text_1),getColor(context,R.color.highlight_text))
            itemView.findViewById<TextView>(R.id.location_text_region_name).text = searchResultView.placeRegion
            itemView.findViewById<ConstraintLayout>(R.id.layout_search_item)
                .setOnClickListener {
                    viewModel.addLocation(searchResultView.placeId)
                }
        }
    }

}