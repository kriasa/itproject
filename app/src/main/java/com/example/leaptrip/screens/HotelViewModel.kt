package com.example.leaptrip.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaptrip.network.HotelApiService
import com.example.leaptrip.network.HotelRequest
import com.example.leaptrip.network.HotelResponse
import com.example.leaptrip.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HotelViewModel : ViewModel() {
    // Явно указываем тип для MutableStateFlow
    private val _hotels: MutableStateFlow<List<HotelResponse>> = MutableStateFlow(emptyList())
    val hotels: StateFlow<List<HotelResponse>> = _hotels

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error

    // Явно указываем тип для api
    private val api: HotelApiService = RetrofitClient.hotelApiService

    fun searchHotels(
        city: String,
        checkIn: String,
        checkOut: String,
        adults: Int = 2,
        stars: List<Int>? = null,
        priceMin: Int? = null,
        priceMax: Int? = null,
        limit: Int = 10
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val response = api.searchHotels(
                    HotelRequest(
                        city = city,
                        check_in = checkIn,
                        check_out = checkOut,
                        adults = adults,
                        stars = stars,
                        price_min = priceMin,
                        price_max = priceMax,
                        limit = limit
                    )
                )

                _hotels.value = if (response.isNotEmpty()) {
                    response
                } else {
                    emptyList<HotelResponse>().also {
                        _error.value = "Отели не найдены. Попробуйте изменить параметры поиска"
                    }
                }
            } catch (e: Exception) {
                _hotels.value = emptyList()
                _error.value = "Ошибка сети: ${e.localizedMessage ?: "неизвестная ошибка"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
