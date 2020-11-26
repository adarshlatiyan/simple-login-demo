package com.example.loginflow.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginflow.R
import com.example.loginflow.databinding.FragmentLoginBinding
import com.example.loginflow.util.DataState
import com.example.loginflow.util.validate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val progressDialog by lazy {
        AlertDialog.Builder(requireContext()).create().apply {
            setView(layoutInflater.inflate(R.layout.progress_layout, null))
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        subscribeToViewModel()
        initView()
    }

    private fun initView() {
        with(binding) {
            loginButton.setOnClickListener {
                val isUsernameValid = etUsername.validate()
                val isPasswordValid = etPassword.validate()

                if (isUsernameValid && isPasswordValid) {
                    username = etUsername.text.toString()
                    password = etPassword.text.toString()

                    viewModel.setAuthStateEvent(
                        LoginViewModel.AuthStateEvent.LoginEvent(username, password)
                    )
                }
            }
        }
    }

    private fun subscribeToViewModel() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    hideProgressBar()
                    loginSuccess()
                }
                is DataState.Loading -> {
                    showProgressBar()
                }
                is DataState.Failure -> {
                    hideProgressBar()
                    loginFailed(it.exception)
                }
            }
        }
    }

    private fun loginFailed(e: Exception) {
        Snackbar.make(this.requireView(), e.message!!, Snackbar.LENGTH_SHORT).show()
    }

    private fun loginSuccess() {
        val action = LoginFragmentDirections.actionLoginFragmentToRechargeFragment(username, password)
        findNavController().navigate(action)
    }

    private fun hideProgressBar() {
        progressDialog.hide()
    }

    private fun showProgressBar() {
        progressDialog.show()
    }
}