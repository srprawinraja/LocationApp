package com.example.locationapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils(val context: Context) {
    private val _fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    @SuppressLint("MissingPermission")
    fun setTheCurrentLocation(viewModel: LocationViewModel){
        val locationCallBack  = object : LocationCallback() {
            override fun onLocationResult(location: LocationResult) {
                super.onLocationResult(location)
                location.lastLocation?.let {
                    val locationData=LocationData(it.latitude, it.longitude)
                    Log.i("location of the user in model", locationData.toString())

                    viewModel.updateLocation(locationData)
                }
            }
        }
        val locationRequest=LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper())
    }
    fun isLocationPermitted():Boolean{
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
    }

   /* fun getAddress(latLng: LocationData):String{
        val geoCoder=Geocoder(context, Locale.getDefault())
        val coordinates = LatLng(latLng.latitude, latLng.longitude)
        val addresses =geoCoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1)
        return if(addresses.isNullOrEmpty()){
            "no address found"
        } else{
            addresses[0].getAddressLine(0)
        }
    }*/

}