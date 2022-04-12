package com.example.telegram.packages.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.telegram.packages.viewmodel.EnterCodeViewModel

class EnterCodeViewModelFactory(private val enterCodeRepository:EnterCodeRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EnterCodeViewModel::class.java)){
            return EnterCodeViewModel(enterCodeRepository) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}