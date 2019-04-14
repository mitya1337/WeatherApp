package com.mitya.weatherapp.domain

import com.google.gson.annotations.SerializedName

data class CityWeather(
    @SerializedName("coord") val location: Location
    , @SerializedName("weather") val weatherCondition: List<WeatherCondition>
    , @SerializedName("main") val weatherData: WeatherData, val wind: Wind
    , @SerializedName("id") val cityId: Int, @SerializedName("name") val cityName: String
)

data class Location(val lon: Double, val lat: Double)

data class WeatherCondition(
    val id: Long, @SerializedName("main") val condition: String,
    val description: String,
    val icon: String
)

data class WeatherData(
    @SerializedName("temp") val temperature: Double, val pressure: Double
    , val humidity: Int, @SerializedName("temp_min") val temperatureMin: Double
    , @SerializedName("temp_max") val temperatureMax: Double
)

data class Wind(val speed: Double, @SerializedName("deg") val direction: Double)