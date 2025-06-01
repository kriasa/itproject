package com.example.leaptrip.network

import retrofit2.http.GET

interface UserApiService {
    @GET("/user")
    suspend fun getCurrentUser(): UserResponse
}
