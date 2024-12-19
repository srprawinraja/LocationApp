package com.example.locationapp

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.locationapp.ui.theme.LocationAppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocationAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    DisplayView(LocalContext.current)
                }
            }
        }
    }


    /*
    @Composable
    fun DisplayView() {

    }

    @Composable
     fun HandlePermission(context: Context) {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
        ) {permission->
            if(permission[Manifest.permission.ACCESS_COARSE_LOCATION]==true
                && permission[Manifest.permission.ACCESS_FINE_LOCATION]==true){

            } else {
                if(ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)){
                    Toast.makeText(context,"Location Permission is required for this app to work", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context,"enable location permission in setting", Toast.LENGTH_LONG).show()
                }
            }

        }
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @Composable
    fun Main(context: Context, locationUtils: LocationUtils, locationViewModel: LocationViewModel) {
        DisplayLocation(context = context, locationUtils = locationUtils, locationViewModel = locationViewModel)
    }
}
@Composable
fun DisplayLocation(context: Context, locationUtils: LocationUtils, locationViewModel: LocationViewModel){
    var location = locationViewModel.location.value
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) {permission->
        if(permission[Manifest.permission.ACCESS_COARSE_LOCATION]==true
            && permission[Manifest.permission.ACCESS_FINE_LOCATION]==true){

        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(context,"Location Permission is required for this app to work", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context,"enable location permission in setting", Toast.LENGTH_LONG).show()
            }
        }

    }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(location!=null){
            location.let {
                Text(text = "longitute is ${it.longitude} and latitude is ${it.latitude} \n \"${locationUtils.getAddress(location)}")
            }
        }
        Text(text = "")
        Button(onClick = {
            if(locationUtils.isLocationPermitted()){
                locationUtils.setTheCurrentLocation(locationViewModel)
            } else {
                launcher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }) {
            Text(text = "Button")
        }
    }*/
}

