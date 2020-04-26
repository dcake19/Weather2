package com.example.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.ApplicationFeatureLocation
import com.example.presentation_location_view_model.search.SearchLocationViewModel
import com.example.presentation_location_view_model.search.SearchResultsView
import com.example.view_model.R
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FragmentSearch: Fragment() {

    @Inject lateinit var viewModel: SearchLocationViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity!!.application as ApplicationFeatureLocation).injectLocation(this)
        return inflater.inflate(R.layout.search_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!::searchAdapter.isInitialized)
            searchAdapter = SearchAdapter(context,viewModel)

        view.findViewById<ImageButton>(R.id.button_back).setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }

        view.findViewById<ImageButton>(R.id.button_map).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_search_to_map)
        }

        view.findViewById<SearchView>(R.id.search_location).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val term = query?:""
                if (term.isNotBlank())
                    viewModel.searchLocation(term)
                else
                    searchAdapter.clear()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val term = newText?:""
                if (term.isNotBlank())
                    viewModel.searchLocation(term)
                else
                    searchAdapter.clear()
                return false
            }
        })

        createRecyclerView(view)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSearchResultsObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { display(it) }
        viewModel.getLocationAddedObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { Navigation.findNavController(view!!).popBackStack() }
    }

    private fun display(searchResultsView: SearchResultsView){
        view?.findViewById<TextView>(R.id.text_search_instructions)?.visibility = View.GONE
        searchAdapter.searchResultsView = searchResultsView
    }

    private fun createRecyclerView(view: View){
        view.findViewById<RecyclerView>(R.id.list_search_results).let {
            it.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            if (it.adapter==null) it.adapter = searchAdapter
        }

    }
}