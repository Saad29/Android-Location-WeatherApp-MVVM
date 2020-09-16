package com.example.weatherapp1.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp1.utils.LocUtils
import com.example.weatherapp1.R
import com.example.weatherapp1.viewModels.LocationViewModel
import com.example.weatherapp1.databinding.ActivityLocationBinding
import kotlinx.android.synthetic.main.activity_location.*


class LocationActivity : AppCompatActivity() {

    private lateinit var viewModel: LocationViewModel
    private var isGPSEnabled = false
    private lateinit var binding: ActivityLocationBinding
    private var lati = ""
    private var longi = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_location
        )
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        binding.locationViewModel = viewModel
        //click handler for getting weather
        binding.getWeatherButton.setOnClickListener{onGetWeather()}
        LocUtils(this)
            .turnGPSOn(object : LocUtils.OnGpsListener {

            override fun gpsStatus(isGPSEnable: Boolean) {
                this@LocationActivity.isGPSEnabled = isGPSEnable
            }
        })
    }



    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }

    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> latLong.text = getString(R.string.enable_gps)

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> latLong.text = getString(R.string.permission_request)

            else -> ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST
            )
        }
    }

    private fun startLocationUpdate() {
        viewModel.getLocationData().observe(this, Observer {
            latLong.text =  getString(R.string.latLong, it.longitude, it.latitude)
            lati = it.latitude.toString()
            longi = it.longitude.toString()
        })
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }


    //function for handling click
    private fun onGetWeather()
    {
        val lat = lati
        val lon = longi
        val intent = Intent(this, WeatherActivity::class.java).apply {
            putExtra("lat", lat)
            putExtra("lon",lon)
        }
        startActivity(intent)

    }





}

const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101