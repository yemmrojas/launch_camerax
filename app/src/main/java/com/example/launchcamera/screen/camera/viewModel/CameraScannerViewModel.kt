package com.example.launchcamera.screen.camera.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launchcamera.screen.camera.CameraPermissionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraScannerViewModel @Inject constructor(): ViewModel() {

    private val _cameraPermissionState = MutableStateFlow(CameraPermissionState.UNINITIALIZED)
    val cameraPermissionState = _cameraPermissionState.asStateFlow()

    private val _shouldRequestPermission = MutableStateFlow(false)
    val shouldRequestPermission = _shouldRequestPermission.asStateFlow()

    fun onCameraPermissionResult(isGranted: Boolean) {
        viewModelScope.launch {
            if (isGranted) {
                _cameraPermissionState.value = CameraPermissionState.GRANTED
            } else {
                _cameraPermissionState.value = CameraPermissionState.DENIED
            }
            _shouldRequestPermission.value = false
        }
    }

    fun requestCameraPermission() {
        _shouldRequestPermission.value = true
    }
}