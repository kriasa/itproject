package com.example.leaptrip.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaptrip.network.RetrofitClient
import com.example.leaptrip.network.FlightResponse
import com.example.leaptrip.network.FlightSearchRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlightViewModel : ViewModel() {
    private val api = RetrofitClient.flightApiService


    private val _flights = MutableStateFlow<List<FlightResponse>>(emptyList())
    val flights: StateFlow<List<FlightResponse>> = _flights

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setError(message: String) {
        _error.value = message
    }

    fun searchFlights(
        fromCity: String,
        toCity: String,
        departureDate: String,
        returnDate: String?
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _flights.value = emptyList()

            try {
                val request = FlightSearchRequest(
                    fromCity = fromCity,
                    toCity = toCity,
                    departureDate = departureDate,
                    returnDate = returnDate
                )

                val response = api.searchFlights(request)

                if (response.isNotEmpty()) {
                    _flights.value = response
                } else {
                    _error.value = "Рейсы не найдены. Попробуйте изменить параметры поиска"
                }
            } catch (e: Exception) {
                Log.e("FlightViewModel", "Ошибка при поиске", e)
                _error.value = "Ошибка при поиске: ${e.localizedMessage ?: "Неизвестная ошибка"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
