package com.example.loginflow.data.coupon

import com.google.gson.annotations.SerializedName

data class CouponResponse(
    @SerializedName("token")
    val coupon: String?
)
