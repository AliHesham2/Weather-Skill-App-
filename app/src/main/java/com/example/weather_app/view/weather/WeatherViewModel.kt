package com.example.weather_app.view.weather

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.example.weather_app.R
import com.example.weather_app.model.Weather
import com.example.weather_app.model.WeatherData
import com.example.weather_app.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class WeatherViewModel(private val app: Application) : AndroidViewModel(app) {


    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _locationData = MutableLiveData<WeatherResponse>()
    val locationData: LiveData<WeatherResponse>
        get() = _locationData

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData>
        get() = _weatherData


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


     fun getLatLong(mLastLocation: Location) {
         startLoading()
         viewModelScope.launch(Dispatchers.IO) {
             try {
                 getData(mLastLocation)
             }catch (t: Exception){
                 Log.i("Check",t.message!!)
                 if (t is IOException) {
                     whenFail(app.resources.getString(R.string.NO_INTERNET))
                 } else {
                     whenFail(app.resources.getString(R.string.WRONG))
                 }
             }
         }
     }

    private suspend fun getData(mLastLocation: Location) {
        val response = Weather.retrofitService.getWeatherData(mLastLocation.latitude, mLastLocation.longitude, app.resources.getString(R.string.METRIC_UNIT), app.resources.getString(R.string.APP_ID))
        if(response.isSuccessful){
            whenSuccess(response.body())
        }else{
            whenFail(app.resources.getString(R.string.WRONG))
        }
    }

    private suspend fun whenSuccess(data: WeatherResponse?) {
        withContext(Dispatchers.Main){
            _loading.value = false
            _locationData.value = data
            _locationData.value!!.weather.map {
                _weatherData.value = it
            }
        }
    }
    private suspend fun whenFail(msg:String){
        withContext(Dispatchers.Main){
            _loading.value = false
            _error.value = msg
        }
    }
    private  fun startLoading(){
            _loading.value = true
    }

}


