package com.example.leaptrip.network

data class LoginRequest(
    val username_or_email: String,
    val password: String
)