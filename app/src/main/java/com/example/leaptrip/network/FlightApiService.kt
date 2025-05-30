package com.example.leaptrip.network
import retrofit2.http.Body
import retrofit2.http.POST
interface FlightApiService {
    @POST("flights/search")  // путь должен соответствовать твоему API
    suspend fun searchFlights(@Body request: FlightSearchRequest): FlightSearchResponse
}