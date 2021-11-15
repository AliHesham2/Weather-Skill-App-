package com.example.weather_app.view.util

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.example.weather_app.R
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SetTextI18n")
@RequiresApi(Build.VERSION_CODES.N)
@BindingAdapter("getTempUnit")
fun getTempUnit(txt:TextView,temp: Double?){
    val x = txt.resources.configuration.locales.toString()
    var value =  txt.resources.getString(R.string.TEMP_C)
    if (txt.resources.getString(R.string.TEMP_US) == x || txt.resources.getString(R.string.TEMP_LR) == x || txt.resources.getString(R.string.TEMP_MM) == x) {
        value = txt.resources.getString(R.string.TEMP_F)
    }
    txt.text = temp.toString() + value
}

@BindingAdapter("unixTime")
fun unixTime(txt:TextView,timex: Int?){
    val date = Date(timex!! * 1000L)
    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat(txt.resources.getString(R.string.PATTERN))
    sdf.timeZone = TimeZone.getDefault()
    txt.text =  sdf.format(date)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("humidity")
fun humidity(txt:TextView,x: Int?){
    txt.text = " $x  ${txt.resources.getString(R.string.PER_CENT)}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("tempMin")
fun tempMin(txt:TextView,x:Double?){
    txt.text= "$x ${txt.resources.getString(R.string.TEMP_MIN)}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("tempMax")
fun tempMax(txt:TextView,x:Double?){
    txt.text = "$x ${txt.resources.getString(R.string.TEMP_MAX)}"
}

@BindingAdapter("speed")
fun speed(txt:TextView,x:Double?) {
    txt.text = x.toString()
}

@BindingAdapter("image")
fun image(img:ImageView, icon: String?){
    when(icon){
        img.resources.getString(R.string.CODE_01d) ->   img.setImageResource(R.drawable.sunny)
        img.resources.getString(R.string.CODE_02d) ->   img.setImageResource(R.drawable.cloud)
        img.resources.getString(R.string.CODE_03d) ->   img.setImageResource(R.drawable.cloud)
        img.resources.getString(R.string.CODE_04d) ->   img.setImageResource(R.drawable.cloud)
        img.resources.getString(R.string.CODE_04n) ->   img.setImageResource(R.drawable.cloud)
        img.resources.getString(R.string.CODE_10d) ->   img.setImageResource(R.drawable.rain)
        img.resources.getString(R.string.CODE_11d) ->   img.setImageResource(R.drawable.storm)
        img.resources.getString(R.string.CODE_13d) ->   img.setImageResource(R.drawable.snowflake)
        img.resources.getString(R.string.CODE_01n) ->   img.setImageResource(R.drawable.cloud)
        img.resources.getString(R.string.CODE_02n) ->   img.setImageResource(R.drawable.cloud)
        img.resources.getString(R.string.CODE_03n) ->   img.setImageResource(R.drawable.cloud)
        img.resources.getString(R.string.CODE_10n) ->   img.setImageResource(R.drawable.cloud)
        img.resources.getString(R.string.CODE_11n) ->   img.setImageResource(R.drawable.rain)
        img.resources.getString(R.string.CODE_13n) ->   img.setImageResource(R.drawable.snowflake)
        else ->    img.setImageResource(R.drawable.cloud)
    }
}