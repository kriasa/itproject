package com.example.leaptrip.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FlightApi {
    private const val BASE_URL = "http://192.168.233.18:8000/" // или ваш сервер

    val retrofitService: FlightApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightApiService::class.java)
    }
}
