package com.example.weather_app.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.R
import com.example.weather_app.view.util.ApplicationResource
import com.example.weather_app.view.weather.Weather

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ApplicationResource.setResource(this.resources)
         Handler(Looper.getMainLooper()).postDelayed({
          startActivity(Intent(this@MainActivity, Weather::class.java))
          finish()
          }, 3000)
    }
}