package com.example.launchcamera.screen.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

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
}
