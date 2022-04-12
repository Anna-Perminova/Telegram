package com.example.telegram.packages.viewmodel

import androidx.lifecycle.ViewModel
import com.example.telegram.packages.ui.screens.register.EnterCodeRepository

class EnterCodeViewModel(private val repository: EnterCodeRepository) : ViewModel() {
    val isLoggedIn = repository.isLoggedIn()
    fun enterCode(code: String, phoneNumber: String, id: String) {
        return repository.enterCode(code, phoneNumber, id)
    }
}