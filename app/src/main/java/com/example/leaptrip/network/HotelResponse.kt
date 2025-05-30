package com.example.leaptrip.network

data class HotelResponse(
    val name: String,
    val stars: Int,
    val price: Int,          // Общая цена за период
    val price_per_night: Int,
    val location: Location,
    val booking_url: String
)

data class Location(
    val lat: Double,
    val lon: Double
)