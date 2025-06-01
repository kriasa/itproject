package com.example.leaptrip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaptrip.network.HotelResponse
import com.example.leaptrip.network.HotelRequest
import com.example.leaptrip.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HotelViewModel : ViewModel() {

    private val api = RetrofitClient.hotelApiService // <- именно сюда надо смотреть

    private val _hotels = MutableStateFlow<List<HotelResponse>>(emptyList())
    val hotels: StateFlow<List<HotelResponse>> = _hotels

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setError(message: String) {
        _error.value = message
    }

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
            _hotels.value = emptyList()

            try {
                val request = HotelRequest(
                    city = city,
                    check_in = checkIn,
                    check_out = checkOut,
                    adults = adults,
                    stars = stars,
                    price_min = priceMin,
                    price_max = priceMax,
                    limit = limit
                )

                val response = api.searchHotels(request)

                if (response.isNotEmpty()) {
                    _hotels.value = response
                } else {
                    _error.value = "Отели не найдены. Попробуйте изменить параметры поиска"
                }
            } catch (e: Exception) {
                _error.value = "Ошибка при поиске: ${e.localizedMessage ?: "Неизвестная ошибка"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
