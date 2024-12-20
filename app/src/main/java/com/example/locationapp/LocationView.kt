package com.example.locationapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location


@SuppressLint("UnrememberedMutableState")
@Composable
fun DisplayView(context: Context){
    val mapViewportState = rememberMapViewportState()
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    remember { LocationServices.getFusedLocationProviderClient(context) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) {permission->
        if(permission[Manifest.permission.ACCESS_COARSE_LOCATION]==true
            && permission[Manifest.permission.ACCESS_FINE_LOCATION]==true){
            mapViewportState.transitionToFollowPuckState()
        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(context,"Location Permission is required for this app to work", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context,"enable location permission in setting", Toast.LENGTH_LONG).show()
            }
        }

    }
    Box (
        modifier = Modifier.fillMaxSize()
    ){

        MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState
        ) {
            MapEffect(Unit) { mapView ->
                mapView.location.updateSettings {
                    locationPuck = createDefault2DPuck(withBearing = true)
                    enabled = true
                    puckBearing = PuckBearing.COURSE
                    puckBearingEnabled = true
                }
                mapViewportState.transitionToFollowPuckState()
            }
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)  // Align to the bottom-end
                .padding(start = 10.dp, end = 50.dp, bottom = 90.dp, top = 10.dp)
                .background(Color.Cyan, shape = CircleShape)  // Blue background with circular shape
                .padding(5.dp),  // Inner padding for the icon
                onClick = {
                    if(!isLocationPermitted(context)){
                        launcher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    } else if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                        Toast.makeText(context,"please enable location", Toast.LENGTH_LONG).show()
                    } else if(!isInternetAvailable(connectivityManager)){
                        Toast.makeText(context,"enable mobile data", Toast.LENGTH_LONG).show()
                    } else {
                        mapViewportState.transitionToFollowPuckState()
                    }
            }
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                tint = Color.Black,  // Black icon color
                modifier = Modifier.size(30.dp)  // Icon size
            )
        }
    }
}

fun isLocationPermitted(context: Context):Boolean{
    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED&&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
}

fun isInternetAvailable(connectivityManager: ConnectivityManager): Boolean {
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}






