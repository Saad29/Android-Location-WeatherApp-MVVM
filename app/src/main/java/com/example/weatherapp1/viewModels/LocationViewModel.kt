package com.example.weatherapp1.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.weatherapp1.models.LocationLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationData =
        LocationLiveData(application)

    fun getLocationData() = locationData

    fun onGetWeather()
    {
     //Log.i("location data : ", locationData)
    }
}