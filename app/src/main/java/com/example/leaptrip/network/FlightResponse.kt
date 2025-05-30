package com.example.leaptrip.network

data class FlightResponse(
    val airline: String,
    val flight_number: String,
    val departure_at: String,
    val return_at: String?,
    val price: Int,
    val transfers: String,
    val duration: String,
    val booking_url: String
)

typealias FlightSearchResponse = List<FlightResponse>
