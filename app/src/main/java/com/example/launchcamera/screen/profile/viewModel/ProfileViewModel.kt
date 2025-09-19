package com.example.launchcamera.screen.profile.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.usescases.GetUserByIdUseCase
import com.example.launchcamera.screen.profile.PROFILE_ID_ARGUMENT
import com.example.launchcamera.screen.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val userId = savedStateHandle.get<String>(PROFILE_ID_ARGUMENT)

    private val _messageError = MutableStateFlow("")
    val messageError = _messageError.asStateFlow()

    private val _stateProfile = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val stateProfile = _stateProfile.asStateFlow()

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData = _userData.asStateFlow()

    fun getUserById(id: String?) {
        _stateProfile.value = ScreenState.Loading
        viewModelScope.launch {
            val result = getUserByIdUseCase(id.orEmpty())
            if (result.isSuccess) {
                _userData.value = result.getOrNull()
                _stateProfile.value = ScreenState.Success
            } else {
                _stateProfile.value = ScreenState.Error
                _messageError.value = result.exceptionOrNull()?.message.orEmpty()
            }
        }
    }
}