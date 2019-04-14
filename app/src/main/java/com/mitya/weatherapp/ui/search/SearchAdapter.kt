package com.mitya.weatherapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mitya.weatherapp.R
import com.mitya.weatherapp.domain.CityWeather
import kotlinx.android.synthetic.main.city_item.view.*

class SearchAdapter(private val searchViewModel: SearchViewModel) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private val cityList = ArrayList<CityWeather>()

    fun addCities(cities: List<CityWeather>) {
        cityList.clear()
        this.notifyDataSetChanged()
        cityList.addAll(cities)
        this.notifyItemRangeInserted(0, cityList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = cityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(cityList[position], searchViewModel)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cityWeather: CityWeather, searchViewModel: SearchViewModel) {
            with(itemView) {
                cityName.text = cityWeather.cityName
                cityCondition.text = cityWeather.weatherCondition[0].condition
                cityTemperature.text = cityWeather.weatherData.temperature.toString()
                cityWind.text = cityWeather.wind.speed.toString()
                saveCity.visibility = View.VISIBLE
                saveCity.setOnClickListener {
                    searchViewModel.saveCityId(cityWeather.cityId)
                    Toast.makeText(it.context, "City saved!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}