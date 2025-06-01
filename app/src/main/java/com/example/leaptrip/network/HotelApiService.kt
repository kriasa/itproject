package com.example.leaptrip.network

import retrofit2.http.Body
import retrofit2.http.POST
interface HotelApiService {
    @POST("hotels/search")
    suspend fun searchHotels(@Body request: HotelRequest): List<HotelResponse>
}
