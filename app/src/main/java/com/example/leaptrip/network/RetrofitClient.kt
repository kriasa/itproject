package com.example.leaptrip.network
//Бумбумшакалака
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // Убедитесь, что IP и порт правильные (без / в конце)
    private const val BASE_URL = "http://192.168.43.115:8000"

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC // Или Level.BODY для детальных логов
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val flightApiService: FlightApiService by lazy {
        retrofit.create(FlightApiService::class.java)
    }

    val hotelApiService: HotelApiService by lazy {
        retrofit.create(HotelApiService::class.java)
    }
}

