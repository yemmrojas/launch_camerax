package com.example.launchcamera.screen.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(): ViewModel() {

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