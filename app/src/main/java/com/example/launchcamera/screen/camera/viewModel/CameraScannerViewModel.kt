package com.example.launchcamera.screen.camera.viewModel

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.usescases.ExtractTextFromImageUseCase
import com.example.launchcamera.screen.camera.provider.CameraScannerContentProvider
import com.example.launchcamera.screen.camera.provider.UserDataValidator
import com.example.launchcamera.screen.camera.state.CameraPermissionState
import com.example.launchcamera.screen.camera.state.CameraScannerState
import com.example.launchcamera.screen.camera.state.IconConfig
import com.example.launchcamera.screen.camera.state.ScanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraScannerViewModel @Inject constructor(
    private val extractTextFromImageUseCase: ExtractTextFromImageUseCase,
    private val userDataValidator: UserDataValidator,
    private val cameraScannerContentProvider: CameraScannerContentProvider,
) : ViewModel() {

    private val _cameraScannerState = MutableStateFlow(CameraScannerState())
    val cameraScannerState = _cameraScannerState.asStateFlow()

    fun onCameraPermissionResult(isGranted: Boolean) {
        viewModelScope.launch {
            _cameraScannerState.value = _cameraScannerState.value.copy(
                cameraPermissionState = if (isGranted) {
                    CameraPermissionState.GRANTED
                } else {
                    CameraPermissionState.DENIED
                },
                shouldRequestPermission = false
            )
        }
    }

    fun onMainButtonPressed() {
        if (_cameraScannerState.value.cameraPermissionState != CameraPermissionState.GRANTED) {
            requestCameraPermission()
            return
        }

        when (_cameraScannerState.value.scanState) {
            ScanState.INIT, ScanState.BACK_SIDE, ScanState.FRONT_SIDE -> {
                _cameraScannerState.value = _cameraScannerState.value.copy(showCamera = true)
            }

            ScanState.FINISH -> {
                _cameraScannerState.value = _cameraScannerState.value.copy(showCamera = false)
            }

            else -> _cameraScannerState.value = _cameraScannerState.value.copy(showCamera = true)
        }
    }

    fun processImage(imageProxy: ImageProxy) {
        _cameraScannerState.value = _cameraScannerState.value.copy(showCamera = false)
        val currentUserData = _cameraScannerState.value.userData
        viewModelScope.launch {
            val result = extractTextFromImageUseCase(imageProxy, currentUserData)
            handleScanResult(result)
        }
    }

    fun getTitle(scanState: ScanState): String =
        cameraScannerContentProvider.getTitle(scanState)

    fun getDescription(scanState: ScanState, errorMessage: String): String =
        cameraScannerContentProvider.getDescription(scanState, errorMessage)

    fun getButtonText(scanState: ScanState): String =
        cameraScannerContentProvider.getButtonText(scanState)

    fun getIconConfig(scanState: ScanState): IconConfig =
        cameraScannerContentProvider.getIconConfig(scanState)

    private fun requestCameraPermission() {
        _cameraScannerState.value = _cameraScannerState.value.copy(shouldRequestPermission = true)
    }

    private fun handleScanResult(result: Result<UserData>) {
        if (result.isSuccess) {
            result.getOrNull()?.let { newUserData ->
                updateUserData(newUserData)
                updateScanStateBasedOnUserData(newUserData)
            }
        } else {
            handleScanError(result.exceptionOrNull()?.message.orEmpty())
        }
    }

    private fun updateUserData(userData: UserData) {
        _cameraScannerState.value = _cameraScannerState.value.copy(userData = userData)
    }

    private fun updateScanStateBasedOnUserData(userData: UserData) {
        val newScanState = if (userDataValidator.isUserDataComplete(userData)) {
            ScanState.FINISH
        } else {
            ScanState.BACK_SIDE
        }
        updateScanState(newScanState)
    }

    private fun updateScanState(scanState: ScanState) {
        _cameraScannerState.value = _cameraScannerState.value.copy(scanState = scanState)
    }

    private fun handleScanError(errorMessage: String) {
        _cameraScannerState.value = _cameraScannerState.value.copy(
            scanState = ScanState.ERROR,
            messageErrorScan = errorMessage
        )
    }
}