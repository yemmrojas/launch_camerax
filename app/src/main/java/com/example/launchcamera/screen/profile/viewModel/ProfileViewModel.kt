package com.example.launchcamera.screen.profile.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.usescases.GetUserByIdUseCase
import com.example.launchcamera.screen.profile.PROFILE_ID_ARGUMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    val userId = savedStateHandle.get<String>(PROFILE_ID_ARGUMENT)
    var isLoading = false

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData = _userData.asStateFlow()

    fun getUserById(id: String) {
        isLoading = true
        viewModelScope.launch {
            val result = getUserByIdUseCase(id)
            if (result.isSuccess) {
                result.map {
                    _userData.value = it
                }
                isLoading = false
            } else {
                isLoading = false
            }
        }
    }
}