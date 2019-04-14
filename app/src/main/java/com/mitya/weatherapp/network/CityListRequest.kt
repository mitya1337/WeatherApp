package com.mitya.weatherapp.network

class CityListRequest(private val cityList: List<Int>) {
    override fun toString(): String {
        if (cityList.isEmpty()) return ""
        return cityList.joinToString(",")
    }
}