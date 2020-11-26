package com.example.loginflow.ui.recharge

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.loginflow.R
import com.example.loginflow.databinding.FragmentRechargeBinding
import com.example.loginflow.util.DataState
import com.example.loginflow.util.validate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RechargeFragment : Fragment(R.layout.fragment_recharge) {
    companion object {
        private const val TAG = "RechargeFragment"
    }

    private lateinit var binding: FragmentRechargeBinding
    private val viewModel: RechargeViewModel by viewModels()

    private val args : RechargeFragmentArgs by navArgs()

    private val email by lazy { args.email }
    private val password by lazy { args.pass }
    private lateinit var mobileNumber: String

    private val progressDialog by lazy {
        AlertDialog.Builder(requireContext()).create().apply {
            setView(layoutInflater.inflate(R.layout.progress_layout, null))
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRechargeBinding.bind(view)

        subscribeToViewModel()
        initView()
    }

    private fun initView() {
        with(binding) {
            generateButton.setOnClickListener {
                if (getRechargeAmount() != -1) {
                    if (etMobile.validate()) {
                        mobileNumber = etMobile.text.toString()
                        viewModel.setRechargeStateEvent(RechargeViewModel.RechargeStateEvent.RechargeEvent(email, password))
                    }
                } else {
                    Snackbar.make(
                        this@RechargeFragment.requireView(),
                        "Please select an amount",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getRechargeAmount(): Int {
        with(binding) {
            return when(radioGroup.checkedRadioButtonId) {
                R.id.rb10 -> 10
                R.id.rb25 -> 25
                R.id.rb40 -> 40
                else -> -1
            }
        }
    }

    private fun subscribeToViewModel() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    hideProgressBar()
                    rechargeSuccess(mobileNumber, it.data)
                }
                is DataState.Loading -> {
                    showProgressBar()
                }
                is DataState.Failure -> {
                    hideProgressBar()
                    rechargeFailed(it.exception)
                }
            }
        }
    }

    private fun rechargeFailed(e: Exception) {
        Snackbar.make(
            requireView(),
            "Some error occurred, please try again...",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun rechargeSuccess(mobileNumber: String, coupon: String) {
        val action = RechargeFragmentDirections.actionRechargeFragmentToCouponFragment(mobileNumber, coupon)
        findNavController().navigate(action)
    }


    private fun hideProgressBar() {
        progressDialog.hide()
    }

    private fun showProgressBar() {
        progressDialog.show()
    }

    override fun onStop() {
        super.onStop()
        viewModel.setRechargeStateEvent(RechargeViewModel.RechargeStateEvent.None)
    }
}