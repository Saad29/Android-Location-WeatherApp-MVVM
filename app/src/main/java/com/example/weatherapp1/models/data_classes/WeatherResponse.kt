package com.example.weatherapp1.models.data_classes

import com.squareup.moshi.Json

data class WeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherX>,
    val wind: Wind
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Sys(
    val country: String,
    //@Json(name = "id") val SysId: Int,
    val sunrise: Int,
    val sunset: Int
   // val type: Int
)

data class WeatherX(
    val description: String,
    val icon: String,
    @Json(name = "id") val WeatherId: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val speed: Double
)

