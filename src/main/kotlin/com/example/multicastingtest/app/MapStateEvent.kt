package com.example.multicastingtest.app

typealias Zoom = Double
data class Point(val lat: Double, val lon: Double)
data class MapStateEvent(val centerPoint: Point, val zoom: Zoom) : Event