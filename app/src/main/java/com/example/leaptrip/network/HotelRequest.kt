package com.example.leaptrip.network

data class HotelRequest(
    val city: String,
    val check_in: String,    // формат "YYYY-MM-DD"
    val check_out: String,   // формат "YYYY-MM-DD"
    val adults: Int = 2,
    val stars: List<Int>? = null,  // например [3,4,5]
    val price_min: Int? = null,
    val price_max: Int? = null,
    val limit: Int = 10
)