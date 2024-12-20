package com.example.locationapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view of the app
        setContent {
            LocationAppTheme { // Apply the custom theme to the app
                Surface(
                    modifier = Modifier.fillMaxSize(), // Ensure the surface fills the entire screen
                    color = MaterialTheme.colorScheme.background // Set the background color based on theme
                ) {
                    // Display the view using the current context
                    DisplayView(LocalContext.current)
                }
            }
        }
    }
}
