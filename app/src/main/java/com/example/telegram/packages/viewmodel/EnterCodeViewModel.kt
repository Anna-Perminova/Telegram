package com.example.telegram.packages.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.telegram.packages.ui.screens.register.EnterCodeRepository

class EnterCodeViewModel(private val repository: EnterCodeRepository):ViewModel() {
fun enterCode(code: String, phoneNumber: String, id: String):LiveData<String>{
    return repository.enterCode(code, phoneNumber, id)
}
}