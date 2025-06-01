
package com.example.leaptrip.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): AuthResponse

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): AuthResponse

    @GET("user")
    suspend fun getUserInfo(): UserResponse
}