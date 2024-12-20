plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.locationapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.locationapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Mapbox dependencies
    implementation("com.mapbox.maps:android:11.9.0")  // Mapbox SDK for Android
    implementation("com.mapbox.extension:maps-compose:11.9.0")  // Mapbox Compose extension for Compose UI

    // AndroidX and Jetpack dependencies
    implementation(libs.androidx.core.ktx)  // Core KTX extensions for AndroidX
    implementation(libs.androidx.lifecycle.runtime.ktx)  // Lifecycle components for KTX
    implementation(libs.androidx.activity.compose)  // Activity Compose support
    implementation(platform(libs.androidx.compose.bom))  // BOM for Compose dependencies
    implementation(libs.androidx.ui)  // Compose UI library
    implementation(libs.androidx.ui.graphics)  // Compose UI graphics
    implementation(libs.androidx.ui.tooling.preview)  // Compose tooling support for previews
    implementation(libs.androidx.material3)  // Material3 for Compose UI

    // Google Play Services dependencies
    implementation(libs.play.services.maps)  // Google Play services Maps SDK
    implementation(libs.play.services.location)  // Google Play services Location SDK

    // Testing dependencies
    testImplementation(libs.junit)  // JUnit for unit testing
    androidTestImplementation(libs.androidx.junit)  // AndroidX JUnit for instrumented tests
    androidTestImplementation(libs.androidx.espresso.core)  // Espresso for UI testing
    androidTestImplementation(platform(libs.androidx.compose.bom))  // BOM for testing Compose
    androidTestImplementation(libs.androidx.ui.test.junit4)  // Compose UI testing

    // Debug dependencies for Compose tooling
    debugImplementation(libs.androidx.ui.tooling)  // Compose tooling support
    debugImplementation(libs.androidx.ui.test.manifest)  // Manifest for testing in debug builds
}
