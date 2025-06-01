package com.example.leaptrip.network

data class HotelResponse(
    val name: String,
    val stars: Int,
    val price: Int,             // общая цена
    val price_per_night: Int,
    val location: Map<String, Double>,  // { "lat": ..., "lon": ... }
    val booking_url: String
)
