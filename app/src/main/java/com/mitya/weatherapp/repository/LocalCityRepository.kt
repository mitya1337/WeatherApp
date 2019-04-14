package com.mitya.weatherapp.repository

import android.content.SharedPreferences
import javax.inject.Inject

class LocalCityRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun saveCity(cityId: Int) {
        val set = HashSet<String>(sharedPreferences.getStringSet(CITY_SET_KEY, HashSet<String>()))
        set.add(cityId.toString())
        sharedPreferences.edit().putStringSet(CITY_SET_KEY, set).apply()
    }

    fun loadCityIdList(): List<Int> {
        val set = sharedPreferences.getStringSet(CITY_SET_KEY, HashSet<String>())
        val list = ArrayList<Int>()
        set.forEach { list.add(it.toInt()) }
        return list
    }

    companion object {
        private const val CITY_SET_KEY = "city_set"
    }
}
