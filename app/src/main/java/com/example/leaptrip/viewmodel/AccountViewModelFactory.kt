package com.example.leaptrip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leaptrip.network.UserApiService

class AccountViewModelFactory(
    private val userApiService: UserApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(userApiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
