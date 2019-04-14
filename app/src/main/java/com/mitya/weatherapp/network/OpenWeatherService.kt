package com.mitya.weatherapp.network

import com.mitya.weatherapp.BuildConfig
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("data/2.5/find")
    fun searchByCity(@Query("q") query: String): Single<ApiResponse>

    @GET("data/2.5/group")
    fun getCityWeather(@Query("id") ids: CityListRequest): Single<ApiResponse>

    companion object Factory {
        fun create(): OpenWeatherService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val url = chain.request().url()
                        .newBuilder()
                        .addQueryParameter("APPID", BuildConfig.OPEN_WEATHER_API_KEY)
                        .build()
                    val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                    chain.proceed(request)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(OpenWeatherService::class.java)
        }
    }
}