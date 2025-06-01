package com.example.leaptrip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaptrip.network.UserApiService
import com.example.leaptrip.network.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val userApiService: UserApiService
) : ViewModel() {

    private val _user = MutableStateFlow<UserResponse?>(null)
    val user: StateFlow<UserResponse?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadUser() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val userResponse = userApiService.getCurrentUser()
                _user.value = userResponse
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки данных: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // TODO: добавить логику выхода
    fun logout() {
        // Например, очистка токена и навигация к экрану входа
    }
}
