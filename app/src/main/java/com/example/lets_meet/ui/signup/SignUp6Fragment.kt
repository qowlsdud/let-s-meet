package com.example.lets_meet.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentSignUp5Binding
import com.example.lets_meet.databinding.FragmentSignUp6Binding
class SignUp6Fragment  : SignUpFragment<FragmentSignUp6Binding>(R.layout.fragment_sign_up6) {
    val viewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        binding.next.setOnClickListener ((activity as SignUpActivity).gotoMain)
    }
    override val currentPage: Int = 6
}