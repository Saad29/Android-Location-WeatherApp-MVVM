package com.example.weatherapp1.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp1.models.data_classes.WeatherData
import com.example.weatherapp1.network.OpenWeatherApi
import com.example.weatherapp1.utils.kelvinToCelsius
import com.example.weatherapp1.utils.unixTimestampToDateTimeString
import com.example.weatherapp1.utils.unixTimestampToTimeString
import kotlinx.coroutines.launch

private const val APP_ID = "your_app_id"


/**
 * The [ViewModel] that is attached to the [WeatherActivity].
 */
class WeatherViewModel(lat: String , lon : String) : ViewModel() {

    // the mutabledata for storing the weather info from the response
    val weatherInfoLiveData = MutableLiveData<WeatherData>()
    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()
    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response


    /**
     * Call getCurrentWeather() on init so we can display status immediately.
     */

    init {
        getCurrentWeather(lat,lon)
       // Log.i("locationxxx WA", lat + " AND " + lon)
    }

    /**
     * Sets the value of the status LiveData to the Weather API status.
     */
    private fun getCurrentWeather(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                val weatherInfo = OpenWeatherApi.retrofitService.getWeather(lat,lon,
                    APP_ID
                )
                //on successfully retrieving the weather info
                val weatherData = WeatherData(
                    dateTime = weatherInfo.dt.unixTimestampToDateTimeString(),
                    temperature = weatherInfo.main.temp.kelvinToCelsius().toString(),
                    humidity = "${weatherInfo.main.humidity}%",
                    cityAndCountry = "${weatherInfo.name}, ${weatherInfo.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${weatherInfo.weather[0].icon}.png",
                    weatherConditionIconDescription = weatherInfo.weather[0].description,
                    sunrise = weatherInfo.sys.sunrise.unixTimestampToTimeString(),
                    sunset = weatherInfo.sys.sunset.unixTimestampToTimeString()
                )
                //after acquiring the data we post it to the view
                weatherInfoLiveData.postValue(weatherData) // PUSH data to LiveData object
            } catch (e: Exception) {
                //_response.value = "Failure: ${e.message}"

            }

        }

    }
}