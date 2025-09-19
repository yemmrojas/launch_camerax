package com.example.launchcamera.screen.register.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.usescases.GetUserByIdUseCase
import com.example.launchcamera.domain.usescases.UpdateUserUseCase
import com.example.launchcamera.screen.register.USER_ID_ARGUMENT
import com.example.launchcamera.screen.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    val userId = savedStateHandle.get<String>(USER_ID_ARGUMENT)

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData = _userData.asStateFlow()

    var resultUpdate = false

    private val _registryState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val registryState = _registryState.asStateFlow()

    private val _messageError = MutableStateFlow<String?>(null)
    val messageError = _messageError.asStateFlow()

    private val _email = MutableStateFlow(EMPTY_STRING)
    val email = _email.asStateFlow()

    private val _errorEmail = MutableStateFlow<String?>(null)
    val errorEmail = _errorEmail.asStateFlow()

    private val _confirmEmail = MutableStateFlow(EMPTY_STRING)
    val confirmEmail = _confirmEmail.asStateFlow()

    private val _errorConfirmEmail = MutableStateFlow<String?>(null)
    val errorConfirmEmail = _errorConfirmEmail.asStateFlow()

    private val _phone = MutableStateFlow(EMPTY_STRING)
    val phone = _phone.asStateFlow()

    private val _errorPhone = MutableStateFlow<String?>(null)
    val errorPhone = _errorPhone.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _email.update { newEmail }
        if (newEmail.isNotBlank()) {
            _errorEmail.value = null
        }
    }

    fun onConfirmEmailChanged(newConfirmEmail: String) {
        _confirmEmail.update { newConfirmEmail }
        if (newConfirmEmail.isNotBlank()) {
            _errorConfirmEmail.value = null
        }
    }

    fun onPhoneChanged(newPhone: String) {
        _phone.update { newPhone }
        if (newPhone.isNotBlank()) {
            _errorPhone.value = null
        }
    }

    fun validateFields(): Boolean {
        val emailPattern = Pattern.compile(REGEX_EMAIL)
        val emailMatcher = emailPattern.matcher(email.value)

        var isValid = true

        if (email.value.isBlank()) {
            _errorEmail.value = MESSAGE_ERROR_EMAIL_EMPTY
            isValid = false
        } else if (!emailMatcher.matches()) {
            _errorEmail.value = MESSAGE_ERROR_EMAIL
            isValid = false
        }

        if (confirmEmail.value.isBlank()) {
            _errorConfirmEmail.value = MESSAGE_CONFIRM_EMAIL_EMPTY
            isValid = false
        } else if (email.value != confirmEmail.value) {
            _errorConfirmEmail.value = MESSAGE_ERROR_CONFIRM_EMAIL
            isValid = false
        }

        if (phone.value.isBlank()) {
            _errorPhone.value = MESSAGE_ERROR_PHONE_EMPTY
            isValid = false
        } else if (phone.value.length < PHONE_NUMBER_LENGTH) {
            _errorPhone.value = MESSAGE_ERROR_PHONE
            isValid = false
        }

        return isValid
    }

    fun getUserById(id: String?) {
        _registryState.value = ScreenState.Loading
        viewModelScope.launch {
            val result = getUserByIdUseCase(id.orEmpty())
            if (result.isSuccess) {
                _userData.value = result.getOrNull()
                _registryState.value = ScreenState.Success
            } else {
                _registryState.value = ScreenState.Error
                _messageError.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun updateUser(
        email: String?,
        contact: String?
    ) {
        _registryState.value = ScreenState.Loading
        _userData.value = _userData.value?.copy(
            email = email,
            contact = contact
        )
        viewModelScope.launch {
            _userData.value?.let { userData ->
                val result = updateUserUseCase(userData)
                if (result.isSuccess) {
                    result.map { resultUpdate = it }
                    _registryState.value = ScreenState.Success
                } else {
                    _registryState.value = ScreenState.Error
                    _messageError.value = result.exceptionOrNull()?.message
                }
            }
        }
    }

    companion object {
        const val REGEX_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
        const val MESSAGE_ERROR_EMAIL = "The email is not valid"
        const val MESSAGE_ERROR_PHONE = "The phone number is not valid"
        const val MESSAGE_ERROR_CONFIRM_EMAIL = "The emails do not match"
        const val MESSAGE_ERROR_PHONE_EMPTY = "The phone number is not valid"
        const val MESSAGE_CONFIRM_EMAIL_EMPTY = "The confirm email cannot be empty"
        const val MESSAGE_ERROR_EMAIL_EMPTY = "The email cannot be empty"
        const val PHONE_NUMBER_LENGTH = 10
        const val EMPTY_STRING = ""
    }
}