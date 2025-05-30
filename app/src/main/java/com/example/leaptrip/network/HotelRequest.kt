package com.example.leaptrip.network

data class HotelRequest(
    val city: String,
    val check_in: String,
    val check_out: String,
    val adults: Int = 2,
    val stars: List<Int>? = null,
    val price_min: Int? = null,
    val price_max: Int? = null,
    val limit: Int = 10
)