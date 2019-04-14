package com.mitya.weatherapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mitya.weatherapp.repository.LocalCityRepository
import com.mitya.weatherapp.repository.RemoteCityRepository
import com.mitya.weatherapp.domain.CityWeather
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val remoteCityRepository: RemoteCityRepository,
    private val localCityRepository: LocalCityRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val mutableLoading = MutableLiveData<Boolean>()
    private val mutableCityList = MutableLiveData<List<CityWeather>>()
    val loading: LiveData<Boolean> = mutableLoading
    val cityList: LiveData<List<CityWeather>> = mutableCityList

    fun loadCityList() {
        if (localCityRepository.loadCityIdList().isEmpty()) {
            mutableCityList.value = listOf()
        } else {
            compositeDisposable.add(
                remoteCityRepository.getCities(localCityRepository.loadCityIdList())
                    .doOnSubscribe { mutableLoading.value = true }
                    .subscribe({
                        mutableCityList.value = it
                        mutableLoading.value = false
                    }, {
                        mutableCityList.value = null
                        mutableLoading.value = false
                        it.printStackTrace()
                    })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }
}
