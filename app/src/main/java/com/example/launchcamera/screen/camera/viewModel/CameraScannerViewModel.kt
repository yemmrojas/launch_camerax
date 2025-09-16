package com.example.launchcamera.screen.camera.viewModel

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.usescases.ExtractTextFromImageUseCase
import com.example.launchcamera.screen.camera.CameraPermissionState
import com.example.launchcamera.screen.camera.ScanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraScannerViewModel @Inject constructor(
    private val extractTextFromImageUseCase: ExtractTextFromImageUseCase
) : ViewModel() {

    private val _cameraPermissionState = MutableStateFlow(CameraPermissionState.UNINITIALIZED)
    val cameraPermissionState = _cameraPermissionState.asStateFlow()

    private val _shouldRequestPermission = MutableStateFlow(false)
    val shouldRequestPermission = _shouldRequestPermission.asStateFlow()

    private val _userData = MutableStateFlow(UserData())
    val userData = _userData.asStateFlow()

    private val _scanState = MutableStateFlow(ScanState.INIT)
    val scanState = _scanState.asStateFlow()

    private val _messageErrorScan = MutableStateFlow("")
    val messageErrorScan = _messageErrorScan.asStateFlow()

    private val _showCamera = MutableStateFlow(true)
    val showCamera = _showCamera.asStateFlow()


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

    fun onMainButtonPressed() {
        if (_cameraPermissionState.value != CameraPermissionState.GRANTED) {
            requestCameraPermission()
            return
        }

        when (scanState.value) {
            ScanState.INIT, ScanState.BACK_SIDE, ScanState.FRONT_SIDE -> {
                _showCamera.value = true
            }
            ScanState.FINISH -> {
                _showCamera.value = false
            }
            else -> _scanState.value = ScanState.INIT
        }
    }

    private fun requestCameraPermission() {
        _shouldRequestPermission.value = true
    }

    fun processImage(imageProxy: ImageProxy) {
        _showCamera.value = false
        viewModelScope.launch {
            val result = extractTextFromImageUseCase(imageProxy, _userData.value)
            if (result.isSuccess) {
                result.map { newData ->
                    _userData.value = newData
                }
                if (_userData.value.id.isNotEmpty()
                    && _userData.value.name.isNotEmpty()
                    && _userData.value.lastName.isNotEmpty()
                    && _userData.value.city.isNotEmpty()
                    && _userData.value.birthDate.isNotEmpty()) {
                    _scanState.value = ScanState.FINISH
                } else {
                    _scanState.value = ScanState.BACK_SIDE
                }
            } else {
                _scanState.value = ScanState.ERROR
                _messageErrorScan.value = result.exceptionOrNull()?.message.orEmpty()
            }
        }
    }
}