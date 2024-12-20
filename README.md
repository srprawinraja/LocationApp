LocationApp
LocationApp is an Android application that integrates with Mapbox and Google's Location Services to display a map and track the user's location. It uses Jetpack Compose for the user interface and includes essential location features to enhance the mapping experience.

Prerequisites
Before setting up and running the project, ensure that you have the following:

    Android Studio (or any compatible IDE for Android development)
    An Android device or emulator for testing
    Mapbox account for accessing the Mapbox API
    
Setup Guide
Part 1: Create and Configure Your Credentials
To use the Maps SDK, you need to create and configure your Mapbox credentials.

Step 1: Log in/Sign up for a Mapbox Account
If you haven’t done so already, sign up for a Mapbox account at Mapbox, then log in.
Step 2: Configure Your Public Token
Open your Android project in Android Studio.

Go to the app/res/values folder in your project.

Right-click on the folder and select New > Values Resource File.

Name the file mapbox_access_token.xml and click OK.

Add the following code snippet in the newly created file:

xml
Copy code
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools">
    <string name="mapbox_access_token" translatable="false" tools:ignore="UnusedResources">YOUR_MAPBOX_ACCESS_TOKEN</string>
</resources>
Replace YOUR_MAPBOX_ACCESS_TOKEN with your actual token from the Mapbox tokens page.

Step 3: Configure Permissions
To access the user's location, add the necessary permissions to your AndroidManifest.xml:

Open app/src/main/AndroidManifest.xml.

Add the following permissions:

xml
Copy code
<!-- Access to user's general location -->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<!-- Access to precise location (if needed) -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
Part 2: Add Mapbox SDK Dependency
Now that your credentials are configured, it’s time to add the Mapbox dependency to your project.

Step 1: Add Mapbox Maven Repository
Open your settings.gradle.kts file.

Add the following Maven repository:

kotlin
Copy code
dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
        }
    }
}
Step 2: Configure Gradle Files
Open your module-level build.gradle.kts file.

Ensure that your minSdk version is set to 21 or higher:

kotlin
Copy code
android {
    defaultConfig {
        minSdk = 21
    }
}
Add the Mapbox SDK dependency:

kotlin
Copy code
dependencies {
    implementation("com.mapbox.maps:android:11.9.0")
}
If you're using Jetpack Compose, add the Compose extension:

kotlin
Copy code
android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {
    implementation("com.mapbox.maps:android:11.9.0")
    implementation("com.mapbox.extension:maps-compose:11.9.0")
}
Step 3: Sync Gradle Files
After editing the Gradle files, sync the project with the Gradle files by selecting File > Sync Project with Gradle Files in Android Studio.

Now your project should be set up with Mapbox and ready to display the map and track the user's location.
