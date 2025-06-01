package com.example.leaptrip.network

import com.google.gson.annotations.SerializedName

data class FlightSearchRequest(
        @SerializedName("fromCity")
        val fromCity: String,

        @SerializedName("toCity")
        val toCity: String,

        @SerializedName("departureDate")
        val departureDate: String,

        @SerializedName("returnDate")
        val returnDate: String? = null,

        @SerializedName("oneWay")
        val oneWay: Boolean = false,

        @SerializedName("direct")
        val direct: Boolean = false,

        @SerializedName("limit")
        val limit: Int = 10
)

