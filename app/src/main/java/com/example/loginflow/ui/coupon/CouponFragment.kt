package com.example.loginflow.ui.coupon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.loginflow.R
import com.example.loginflow.databinding.FragmentCouponBinding

class CouponFragment : Fragment(R.layout.fragment_coupon) {
    private val args: CouponFragmentArgs by navArgs()
    private lateinit var binding: FragmentCouponBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCouponBinding.bind(view)

        with(binding) {
            tvMobile.text = "Mobile : ${args.mobile}"
            tvCoupon.text = "Coupon : ${args.coupon}"
        }
    }


}