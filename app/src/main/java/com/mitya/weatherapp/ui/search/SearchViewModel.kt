package com.mitya.weatherapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mitya.weatherapp.domain.CityWeather
import com.mitya.weatherapp.repository.LocalCityRepository
import com.mitya.weatherapp.repository.RemoteCityRepository
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val remoteCityRepository: RemoteCityRepository,
    private val localCityRepository: LocalCityRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var query: String = ""
    private val mutableLoading = MutableLiveData<Boolean>()
    private val mutableCityList = MutableLiveData<List<CityWeather>>()
    val loading: LiveData<Boolean> = mutableLoading
    val cityList: LiveData<List<CityWeather>> = mutableCityList

    fun searchCities(cityName: String) {
        val input = cityName.toLowerCase(Locale.getDefault()).trim()
        if (input == query) {
            return
        }
        query = input
        compositeDisposable.add(remoteCityRepository.searchCities(input)
            .doOnSubscribe { mutableLoading.value = true }
            .subscribe({
                mutableLoading.value = false
                mutableCityList.value = it
            }, {
                mutableCityList.value = null
                mutableLoading.value = false
                it.printStackTrace()
            })
        )
    }

    fun saveCityId(cityId: Int) = localCityRepository.saveCity(cityId)

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }
}
