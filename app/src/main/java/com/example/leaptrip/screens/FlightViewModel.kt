package com.example.leaptrip.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaptrip.network.FlightApiService
import com.example.leaptrip.network.FlightResponse
import com.example.leaptrip.network.FlightSearchRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlightViewModel : ViewModel() {
    private val _flights = MutableStateFlow<List<FlightResponse>>(emptyList())
    val flights: StateFlow<List<FlightResponse>> = _flights

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val api: FlightApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://172.20.10.2:8000/") // или свой адрес сервера
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightApiService::class.java)
    }

    fun searchFlights(fromCity: String, toCity: String, departureDate: String, returnDate: String) {
        viewModelScope.launch {
            try {
                val response = api.searchFlights(
                    FlightSearchRequest(
                        fromCity = fromCity,
                        toCity = toCity,
                        departureDate = departureDate,
                        returnDate = returnDate
                    )
                )
                if (response.isNotEmpty()) {
                    _flights.value = response
                    _error.value = null
                } else {
                    _flights.value = emptyList()
                    _error.value = "Рейсы не найдены"
                }
            } catch (e: Exception) {
                _flights.value = emptyList()
                _error.value = "Ошибка: ${e.localizedMessage}"
            }
        }
    }
}
