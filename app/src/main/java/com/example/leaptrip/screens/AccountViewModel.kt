package com.example.leaptrip.screens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val _userEmail = MutableStateFlow("user@example.com")
    val userEmail: StateFlow<String> = _userEmail

    private val _userName = MutableStateFlow("Иван Иванов")
    val userName: StateFlow<String> = _userName

    fun logout() {
        viewModelScope.launch {
            // Реальная логика выхода
            _userEmail.value = ""
            _userName.value = ""
        }
    }
}