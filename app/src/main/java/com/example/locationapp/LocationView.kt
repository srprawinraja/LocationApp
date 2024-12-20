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
fun DisplayView(context: Context) {
    // Initialize map viewport state for controlling the map view
    val mapViewportState = rememberMapViewportState()

    // Get location and connectivity managers
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Remember the fused location provider client (used for getting device location)
    remember { LocationServices.getFusedLocationProviderClient(context) }

    // Launcher for requesting permissions
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permission ->
        // Check if required location permissions are granted
        if (permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            && permission[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            // If permissions granted, start following the user's location
            mapViewportState.transitionToFollowPuckState()
        } else {
            // Handle case where location permission is denied
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(context, "Location Permission is required for this app to work", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Enable location permission in settings", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Box to contain the map and the location button
    Box(modifier = Modifier.fillMaxSize()) {

        // Mapbox Map with defined viewport state
        MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState
        ) {
            MapEffect(Unit) { mapView ->
                // Update location settings for the map view
                mapView.location.updateSettings {
                    locationPuck = createDefault2DPuck(withBearing = true)  // Use puck with bearing enabled
                    enabled = true
                    puckBearing = PuckBearing.COURSE  // Set puck to follow course direction
                    puckBearingEnabled = true
                }
                // Transition to follow the puck (user's location) on the map
                mapViewportState.transitionToFollowPuckState()
            }
        }

        // IconButton for the user to request location permissions or interact with the map
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)  // Align button to the bottom-right of the screen
                .padding(start = 10.dp, end = 50.dp, bottom = 90.dp, top = 10.dp)  // Add padding around the button
                .background(Color.Cyan, shape = CircleShape)  // Set a cyan background with circular shape for the button
                .padding(5.dp),  // Inner padding for the icon itself
            onClick = {
                // If location permission is not granted, request permissions
                if (!isLocationPermitted(context)) {
                    launcher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                } else if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    // If location provider is not enabled, show a message
                    Toast.makeText(context, "Please enable location", Toast.LENGTH_LONG).show()
                } else if (!isInternetAvailable(connectivityManager)) {
                    // If there's no internet connection, prompt user to enable mobile data
                    Toast.makeText(context, "Enable mobile data", Toast.LENGTH_LONG).show()
                } else {
                    // If everything is okay, start following the puck (location)
                    mapViewportState.transitionToFollowPuckState()
                }
            }
        ) {
            // Icon displayed inside the button (location icon)
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",  // Description for accessibility
                tint = Color.Black,  // Set icon color to black
                modifier = Modifier.size(30.dp)  // Set icon size
            )
        }
    }
}

// Helper function to check if location permissions are granted
fun isLocationPermitted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

// Helper function to check if internet is available
fun isInternetAvailable(connectivityManager: ConnectivityManager): Boolean {
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)  // Check if internet capability is available
}
