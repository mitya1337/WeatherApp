package com.mitya.weatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitya.weatherapp.MainActivity
import com.mitya.weatherapp.R
import com.mitya.weatherapp.ui.search.SearchFragment
import com.mitya.weatherapp.di.Injectable
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel
    private val adapter by lazy { MainAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        setupRecyclerView()
        viewModel.loadCityList()
        viewModel.cityList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) (activity as MainActivity).replaceCurrentFragment(SearchFragment.newInstance())
            else adapter.addCities(it)
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { showProgress(it) })
    }

    private fun setupRecyclerView() {
        mainRecycler.adapter = adapter
        mainRecycler.layoutManager = LinearLayoutManager(context)
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            loadingProgressBar.visibility = View.VISIBLE
            mainRecycler.visibility = View.GONE
        } else {
            loadingProgressBar.visibility = View.GONE
            mainRecycler.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
