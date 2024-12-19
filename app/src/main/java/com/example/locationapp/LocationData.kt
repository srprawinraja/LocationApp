package com.example.locationapp

import com.mapbox.geojson.Point

data class LocationData(
    val latitude:Double,
    val longitude:Double
)
fun LocationData.toPoint(): Point {
    return Point.fromLngLat(longitude, latitude)
}
