package com.example.leaptrip.network

import com.google.gson.annotations.SerializedName

data class FlightResponse(
    @SerializedName("airline")
    val airline: String,

    @SerializedName("flight_number")
    val flight_number: String,

    @SerializedName("departure_at")
    val departure_at: String,

    @SerializedName("return_at")
    val return_at: String?,

    @SerializedName("price")
    val price: Int,

    @SerializedName("transfers")
    val transfers: String,

    @SerializedName("duration")
    val duration: String,

    @SerializedName("booking_url")
    val booking_url: String
)

