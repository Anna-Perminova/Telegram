package com.example.telegram.ui.screens.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.telegram.packages.ui.screens.register.EnterCodeRepository
import com.example.telegram.packages.ui.screens.register.EnterCodeRepositoryImpl
import com.example.telegram.packages.ui.screens.register.EnterCodeViewModelFactory
import com.example.telegram.packages.viewmodel.EnterCodeViewModel
import com.example.telegram.utilits.*
import com.example.tg.MainActivity
import com.example.tg.R
import com.example.tg.databinding.FragmentEnterCodeBinding


/* Фрагмент для ввода кода подтверждения при регистрации */

class EnterCodeFragment(val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {
    private lateinit var binding: FragmentEnterCodeBinding+
    private  lateinit var viewModel: EnterCodeViewModel
    private lateinit var enterCodeRepositoryImpl: EnterCodeRepositoryImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnterCodeBinding.bind(view)
        enterCodeRepositoryImpl= EnterCodeRepositoryImpl()
        viewModel=ViewModelProvider(this,EnterCodeViewModelFactory(EnterCodeRepositoryImpl()))[EnterCodeViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.title = phoneNumber
        binding.registerInputCode.addTextChangedListener(AppTextWatcher {
            val string = binding.registerInputCode.text.toString()
            if (string.length == 6) {
                binding.registerEnterPhoneNumberBtnNext.setOnClickListener{
                    viewModel.enterCode(binding.registerInputCode.toString(),phoneNumber, id) }


            }
        })
    }
}
