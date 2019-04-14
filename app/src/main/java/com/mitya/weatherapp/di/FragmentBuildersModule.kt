package com.mitya.weatherapp.di

import com.mitya.weatherapp.ui.search.SearchFragment
import com.mitya.weatherapp.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment
}