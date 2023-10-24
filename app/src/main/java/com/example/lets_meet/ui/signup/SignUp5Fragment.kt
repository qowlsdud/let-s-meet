package com.example.lets_meet.ui.signup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.lets_meet.R
import com.example.lets_meet.databinding.FragmentSignUp5Binding

class SignUp5Fragment : SignUpFragment<FragmentSignUp5Binding>(R.layout.fragment_sign_up5) {
    val viewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        binding.next.setOnClickListener ((activity as SignUpActivity).gotoMain)
    }
    override val currentPage: Int = 5
}