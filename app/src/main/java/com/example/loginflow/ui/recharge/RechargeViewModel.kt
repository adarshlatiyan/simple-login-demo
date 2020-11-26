package com.example.loginflow.ui.recharge

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.loginflow.repository.RechargeRepository
import com.example.loginflow.ui.login.LoginViewModel
import com.example.loginflow.util.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RechargeViewModel @ViewModelInject constructor(
    private val rechargeRepository: RechargeRepository,
    @Assisted savedStateHandle: SavedStateHandle
): ViewModel() {
    private var _dataState = MutableLiveData<DataState<String>>()
    val dataState get() = _dataState as LiveData<DataState<String>>

    fun setRechargeStateEvent(event: RechargeStateEvent) {
        viewModelScope.launch {
            when (event) {
                is RechargeStateEvent.RechargeEvent -> {
                    rechargeRepository.recharge(event.email, event.password).collect {
                        withContext(viewModelScope.coroutineContext) {
                            _dataState.value = it
                        }
                    }
                }
                else -> _dataState = MutableLiveData()
            }
        }
    }

    sealed class RechargeStateEvent {
        class RechargeEvent(val email: String, val password: String) : RechargeStateEvent()
        object None : RechargeStateEvent()
    }
}