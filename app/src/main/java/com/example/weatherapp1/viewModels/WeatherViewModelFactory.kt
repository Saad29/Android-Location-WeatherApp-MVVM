package com.example.weatherapp1.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeatherViewModelFactory (private val lat : String, private val lon : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(
                lat,
                lon
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}