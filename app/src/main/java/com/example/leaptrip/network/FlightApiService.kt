package com.example.leaptrip.network
import retrofit2.http.Body
import retrofit2.http.POST

interface FlightApiService {
    @POST("flights/search")
    suspend fun searchFlights(@Body request: FlightSearchRequest): List<FlightResponse>
}
