package com.example.leaptrip.network

import com.example.leaptrip.SimpleCookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
// Добавьте этот класс в начало файла RetrofitClient.kt (перед object RetrofitClient)
class CookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Логирование кук
        val cookies = response.headers("Set-Cookie")
        if (cookies.isNotEmpty()) {
            println("Received cookies: $cookies")
        }

        // Также логируем отправляемые куки (если есть)
        val requestCookies = request.headers("Cookie")
        if (requestCookies.isNotEmpty()) {
            println("Sending cookies: $requestCookies")
        }

        return response
    }
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.233.18:8000"

    private val cookieJar = SimpleCookieJar()
    private val cookieManager = CookieManager().apply {
        setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    }

    private val httpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar) // Подключаем нашу CookieJar
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
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

    val userApiService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}
