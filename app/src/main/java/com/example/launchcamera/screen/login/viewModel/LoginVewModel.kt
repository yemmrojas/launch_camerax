package com.example.launchcamera.screen.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launchcamera.domain.usescases.GetUserByIdUseCase
import com.example.launchcamera.screen.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _stateLogin = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val stateLogin = _stateLogin.asStateFlow()

    private val _id = MutableStateFlow("")
    val id = _id.asStateFlow()

    private val _idError = MutableStateFlow<String?>(null)
    val idError = _idError.asStateFlow()

    private val _saveSession = MutableStateFlow(true)
    val saveSession = _saveSession.asStateFlow()

    fun onIdChanged(newId: String) {
        _id.update { newId }
        if (newId.isNotBlank()) {
            _idError.value = null
        }
    }

    fun onSaveSessionChanged(save: Boolean) {
        _saveSession.value = save
    }

    fun validateId(): Boolean {
        return if (id.value.isNotBlank()) {
            _idError.value = null
            true
        } else {
            _idError.value = "ID cannot be empty"
            false
        }
    }

    fun getUserById(id: String) {
        _stateLogin.value = ScreenState.Loading
        viewModelScope.launch {
            val result = getUserByIdUseCase(id)
            if (result.isSuccess && result.getOrNull() != null) {
                _stateLogin.value = ScreenState.Success
            } else {
                _stateLogin.value = ScreenState.Error
            }
        }
    }

    fun resetState() {
        _stateLogin.value = ScreenState.Idle
    }
}