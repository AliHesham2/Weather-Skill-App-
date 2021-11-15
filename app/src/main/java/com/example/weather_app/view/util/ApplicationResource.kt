package com.example.weather_app.view.util

import android.content.res.Resources

object ApplicationResource {
        private  var resources:Resources?=null
        fun setResource(resources: Resources) {
            this.resources = resources
    }
    fun getResource(): Resources? {
        return resources
    }

}