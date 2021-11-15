package com.example.weather_app.view.util

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.example.weather_app.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

object CheckPermission {

    private var permission:Boolean = false

    fun checkPermissions(applicationContext: Context): Boolean {
        if(!isLocationEnabled(applicationContext)){
            permission = false
            PopUpMsg.toastMsg(applicationContext, applicationContext.resources.getString(R.string.ENABLE_Location)
            )
        }else {
            Dexter.withContext(applicationContext)
                .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report!!.areAllPermissionsGranted()) {
                            permission = true
                        }
                        if (report.isAnyPermissionPermanentlyDenied) {
                            permission = false
                            PopUpMsg.toastMsg(
                                applicationContext,
                                applicationContext.resources.getString(R.string.DENIED)
                            )
                        }
                    }
                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        permission = false
                        PopUpMsg.toastMsg(
                            applicationContext,
                            applicationContext.resources.getString(R.string.DENIED)
                        )
                    }
                }).onSameThread()
                .check()
        }
        return permission
    }
    private fun isLocationEnabled(context: Context): Boolean {
        Log.i("Check","isLocationEnabled")
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

}