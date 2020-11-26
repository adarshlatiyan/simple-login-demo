package com.example.loginflow.repository

import com.example.loginflow.data.login.LoginApi
import com.example.loginflow.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val loginApi: LoginApi
) {
    fun login(email: String, password: String): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        val response = loginApi.login(email, password)
        if (response.isSuccessful) emit(DataState.Success(Unit))
        else {
            val errorBody = response.errorBody()?.string()
            val error = try {
                JSONObject(errorBody).getString("error")
            } catch (e: Exception) {
                null
            }
            if (error != null) emit(DataState.Failure(Exception(error)))
            else emit(DataState.Failure(Exception("Unknown error")))
        }
    }
}