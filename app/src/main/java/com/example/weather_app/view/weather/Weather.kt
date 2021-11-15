package com.example.weather_app.view.weather

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.weather_app.R
import com.example.weather_app.databinding.WeatherBinding
import com.example.weather_app.view.util.CheckPermission
import com.example.weather_app.view.util.PopUpMsg
import com.google.android.gms.location.*

class Weather : AppCompatActivity() {
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: WeatherBinding
    private  var latLongData:Location?=null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialization
        binding = DataBindingUtil.setContentView(this, R.layout.weather)
        val application = requireNotNull(this).application
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val viewModelFactory = WeatherViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(WeatherViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //observer
        viewModel.error.observe(this,{
            if(it != null){
                PopUpMsg.alertMsg(findViewById(android.R.id.content),it)
            }
        })
        viewModel.loading.observe(this,{
            if(it == true){
                PopUpMsg.showDialogue(this)
            }else{
                PopUpMsg.hideDialogue()
            }
        })

        // refresh
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.refresh ->{
                    checkLocationPermission()
                    true
                }
                else -> false
            }
        }

        checkLocationPermission()
    }

    // ask for permission
    private fun checkLocationPermission(){
        val permission = CheckPermission.checkPermissions(this.applicationContext)
        if(permission){
            showViews()
            requestLocationData(mFusedLocationClient)
        }else{
            hideViews()
            binding.noData.text = this.resources.getString(R.string.ENABLE_Location)
        }
    }

    // Request Lat & Long by User Location
    @SuppressLint("MissingPermission")
    fun requestLocationData(mFusedLocationClient: FusedLocationProviderClient) {
        Log.i("Check","requestLocationData")
        val mLocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()!!)
    }

    // CallBack to get  Lat & Long Data
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            latLongData = locationResult.lastLocation
            if(latLongData != null){
                viewModel.getLatLong(latLongData!!)
            }
        }
    }

    private fun hideViews(){
        binding.cardView2.visibility = GONE
        binding.cardView.visibility = GONE
        binding.cardView4.visibility = GONE
        binding.cardView5.visibility = GONE
        binding.cardView6.visibility = GONE
        binding.noData.visibility = VISIBLE
    }
    private fun showViews(){
        binding.cardView2.visibility = VISIBLE
        binding.cardView.visibility = VISIBLE
        binding.cardView4.visibility = VISIBLE
        binding.cardView5.visibility = VISIBLE
        binding.cardView6.visibility = VISIBLE
        binding.noData.visibility = GONE
    }
}
