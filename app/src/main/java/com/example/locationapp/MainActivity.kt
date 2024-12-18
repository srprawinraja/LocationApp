package com.example.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocationAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val context= LocalContext.current
                    val locationUtils=LocationUtils()
                    val locationViewModel : LocationViewModel by viewModels()
                    Main(context, locationUtils, locationViewModel)
                }
            }
        }
    }

    @Composable
    fun Main(context: Context, locationUtils: LocationUtils, locationViewModel: LocationViewModel) {
        DisplayLocation(context = context, locationUtils = locationUtils, locationViewModel = locationViewModel)
    }
}
@Composable
fun DisplayLocation(context: Context, locationUtils: LocationUtils, locationViewModel: LocationViewModel){
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
        Button(onClick = {
            if(locationUtils.isLocationPermitted(context)){
                // got location permission
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
    }
}


