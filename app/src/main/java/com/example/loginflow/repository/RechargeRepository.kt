package com.example.loginflow.repository

import com.example.loginflow.data.coupon.CouponCodeApi
import com.example.loginflow.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RechargeRepository @Inject constructor(
    private val couponCodeApi: CouponCodeApi
) {
    fun recharge(email: String, password: String): Flow<DataState<String>> = flow {
        emit(DataState.Loading)
        val response = couponCodeApi.login(email, password)
        if (response.isSuccessful) emit(DataState.Success(response.body()?.coupon!!))
        else {
            val errorBody = response.errorBody()?.string()
            val error = try {
                org.json.JSONObject(errorBody).getString("error")
            } catch (e: Exception) {
                null
            }
            if (error != null) emit(DataState.Failure(Exception(error)))
            else emit(DataState.Failure(Exception("Unknown error")))
        }
    }
}