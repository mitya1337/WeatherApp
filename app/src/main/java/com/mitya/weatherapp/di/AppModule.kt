package com.mitya.weatherapp.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.mitya.weatherapp.network.OpenWeatherService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideOpenWeatherService(): OpenWeatherService {
        return OpenWeatherService.create()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }
}

