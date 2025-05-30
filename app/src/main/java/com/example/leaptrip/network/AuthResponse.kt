package com.example.leaptrip.network

data class AuthResponse(
    val message: String,
    val token: String? = null
)