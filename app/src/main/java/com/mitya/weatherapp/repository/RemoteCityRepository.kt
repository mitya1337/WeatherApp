package com.mitya.weatherapp.repository

import com.mitya.weatherapp.network.CityListRequest
import com.mitya.weatherapp.network.OpenWeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RemoteCityRepository @Inject constructor(private val openWeatherService: OpenWeatherService) {

    fun searchCities(query: String) =
        openWeatherService.searchByCity(query)
            .subscribeOn(Schedulers.io())
            .map { it.result }
            .observeOn(AndroidSchedulers.mainThread())

    fun getCities(ids: List<Int>) =
        openWeatherService.getCityWeather(CityListRequest(ids))
            .subscribeOn(Schedulers.io())
            .map { it.result }
            .observeOn(AndroidSchedulers.mainThread())
}