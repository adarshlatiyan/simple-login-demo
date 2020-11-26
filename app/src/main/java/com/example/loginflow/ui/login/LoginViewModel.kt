package com.example.loginflow.ui.login

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.loginflow.repository.AuthRepository
import com.example.loginflow.util.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dataState = MutableLiveData<DataState<Unit>>()
    val dataState get() = _dataState as LiveData<DataState<Unit>>

    fun setAuthStateEvent(event: AuthStateEvent) {
        viewModelScope.launch {
            when (event) {
                is AuthStateEvent.LoginEvent -> {
                    authRepository.login(event.email, event.password).collect {
                        withContext(viewModelScope.coroutineContext) {
                            _dataState.value = it
                        }
                    }
                }
                else -> return@launch
            }
        }
    }

    sealed class AuthStateEvent {
        class LoginEvent(val email: String, val password: String) : AuthStateEvent()
        object None : AuthStateEvent()
    }
}