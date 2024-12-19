package com.example.locationapp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel() {
    private val _location = mutableStateOf(LocationData(37.7749 ,-122.4194))
    val location=_location
    fun updateLocation(newLocation:LocationData){
        Log.i("location of the user", newLocation.toString())
        _location.value=newLocation
    }

}