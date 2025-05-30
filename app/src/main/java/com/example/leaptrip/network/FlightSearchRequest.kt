package com.example.leaptrip.network

data class FlightSearchRequest(
        val fromCity: String,
        val toCity: String,
        val departureDate: String,
        val returnDate: String? = null,
        val oneWay: Boolean = false,
        val direct: Boolean = false,
        val limit: Int = 10
)
