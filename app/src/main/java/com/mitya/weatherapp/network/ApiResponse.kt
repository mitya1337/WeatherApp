package com.mitya.weatherapp.network

import com.google.gson.annotations.SerializedName
import com.mitya.weatherapp.domain.CityWeather

data class ApiResponse(@SerializedName("list") val result: List<CityWeather>)
