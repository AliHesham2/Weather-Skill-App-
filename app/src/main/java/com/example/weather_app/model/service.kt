package com.example.weather_app.model


import com.example.weather_app.R
import com.example.weather_app.view.util.ApplicationResource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(ApplicationResource.getResource()!!.getString(R.string.BASE_URL))
    .build()


interface WeatherService {
    @GET("2.5/weather")
    suspend fun getWeatherData(@Query("lat") lat: Double,
                               @Query("lon") lon: Double,
                               @Query("units") units: String?,
                               @Query("appid") appId: String?
    ): Response<WeatherResponse>
}

object Weather {
    val retrofitService : WeatherService by lazy { retrofit.create(WeatherService::class.java) }
}