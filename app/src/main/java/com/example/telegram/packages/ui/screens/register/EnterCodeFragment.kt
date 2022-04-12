package com.example.telegram.ui.screens.register

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.telegram.packages.ui.screens.register.EnterCodeRepositoryImpl
import com.example.telegram.packages.ui.screens.register.EnterCodeViewModelFactory
import com.example.telegram.packages.viewmodel.EnterCodeViewModel
import com.example.telegram.utilits.replaceFragment
import com.example.tg.R
import com.example.tg.databinding.FragmentEnterCodeBinding


/* Фрагмент для ввода кода подтверждения при регистрации */

class EnterCodeFragment : Fragment(R.layout.fragment_enter_code) {

    private val phoneNumber get() = requireArguments().getString(EXRTA, "")
    private val id get() = requireArguments().getString("", "")
    private lateinit var binding: FragmentEnterCodeBinding
    private lateinit var viewModel: EnterCodeViewModel
    private lateinit var enterCodeRepositoryImpl: EnterCodeRepositoryImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnterCodeBinding.bind(view)
        enterCodeRepositoryImpl = EnterCodeRepositoryImpl()
        viewModel = ViewModelProvider(
            this,
            EnterCodeViewModelFactory(EnterCodeRepositoryImpl())
        )[EnterCodeViewModel::class.java]
        binding.registerEnterPhoneNumberBtnNext.setOnClickListener {
            viewModel.enterCode(binding.registerInputCode.text.toString(), phoneNumber, id)
        }
        viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                //open next fragment
                (requireActivity() as AppCompatActivity).replaceFragment()
            }
        }
    }

    companion object {
        const val EXRTA = "key"
        fun getInstance(phoneNumber: String, id: String): Fragment {
            return EnterCodeFragment().apply {
                arguments = Bundle().apply {
                    putString(EXRTA, phoneNumber)
                    putString("", id)
                }
            }
        }
    }
}
