package com.mitya.weatherapp

import android.app.Activity
import android.app.Application
import com.mitya.weatherapp.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

class App : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { it.printStackTrace() }
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}