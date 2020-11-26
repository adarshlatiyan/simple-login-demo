package com.example.loginflow.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.loginflow.R
import com.example.loginflow.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        with(binding) {
            loginButton.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()

                val direction = LoginFragmentDirections.actionLoginFragmentToRechargeFragment()
                findNavController().navigate(direction)
            }
        }
    }
}