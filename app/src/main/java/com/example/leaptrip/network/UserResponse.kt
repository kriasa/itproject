package com.example.leaptrip.network

data class UserResponse(
    val id: Int,
    val username: String,
    val email: String,
    val is_active: Boolean
)
