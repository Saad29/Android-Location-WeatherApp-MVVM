package com.example.weatherapp1.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp1.R
import com.example.weatherapp1.viewModels.WeatherViewModel
import com.example.weatherapp1.viewModels.WeatherViewModelFactory
import com.example.weatherapp1.databinding.ActivityWeatherBinding
import com.example.weatherapp1.models.data_classes.WeatherData
import kotlinx.android.synthetic.main.additional_weather_info_layout.*
import kotlinx.android.synthetic.main.basic_weather_info_layout.*
import kotlinx.android.synthetic.main.sunrise_sunset_layout.*


/**
 * This activity shows the weather from the OpenWeatherMap web services transaction.
 */
class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var viewModelFactory: WeatherViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getting extras from intent sent from previous activity,the lat and the long of the location
        val bundle = intent.extras
        val lati = bundle?.getString("lat");
        val longi = bundle?.getString("lon");


        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_weather
        )
        //we are using viewmodel factory to push the location data to the ViewModel at runtime, can also be done using Dependency Injection
        viewModelFactory =
            WeatherViewModelFactory(
                lati.toString(),
                longi.toString()
            )
        viewModel = ViewModelProvider(this,viewModelFactory).get(WeatherViewModel::class.java)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Activity
        binding.lifecycleOwner = this

        // Giving the binding access to the WeatherViewModel
        binding.weatherViewModel = viewModel

        //Observer for getting updates on weather and display it, uses lambda expression
        viewModel.weatherInfoLiveData.observe(this, Observer { weatherData ->
            setWeatherInfo(weatherData)
        })

    }

    private fun setWeatherInfo(weatherData: WeatherData) {
        tv_date_time?.text = weatherData.dateTime
        tv_temperature?.text = weatherData.temperature
        tv_city_country?.text = weatherData.cityAndCountry
        Glide.with(this).load(weatherData.weatherConditionIconUrl).into(iv_weather_condition)
        tv_humidity_value?.text = weatherData.humidity
        tv_weather_condition?.text = weatherData.weatherConditionIconDescription
        tv_sunrise_time?.text = weatherData.sunrise
        tv_sunset_time?.text = weatherData.sunset
    }

}