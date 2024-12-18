package com.example.locationapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel() {
    private var _location= mutableStateOf<LocationData?>(null)
    val location=_location
    fun updateLocation(newLocation:LocationData){
        _location.value=newLocation
    }
}