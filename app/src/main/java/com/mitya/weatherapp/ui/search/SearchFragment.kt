package com.mitya.weatherapp.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitya.weatherapp.R
import com.mitya.weatherapp.di.Injectable
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchViewModel
    private val adapter by lazy { SearchAdapter(viewModel) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        initSearchInputListener()
        setupRecyclerView()
        viewModel.cityList.observe(viewLifecycleOwner, Observer { adapter.addCities(it) })
        viewModel.loading.observe(viewLifecycleOwner, Observer { showProgress(it) })
    }

    private fun setupRecyclerView() {
        searchRecycler.adapter = adapter
        searchRecycler.layoutManager = LinearLayoutManager(context)
    }

    private fun initSearchInputListener() {
        searchInput.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(v: View) {
        dismissKeyboard(v.windowToken)
        viewModel.searchCities(searchInput.text.toString())
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            loadingProgressBar.visibility = View.VISIBLE
            searchRecycler.visibility = View.GONE
        } else {
            loadingProgressBar.visibility = View.GONE
            searchRecycler.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
