package com.example.launchcamera.screen.camera.state

import com.example.launchcamera.domain.model.UserData

data class CameraScannerState(
    val cameraPermissionState: CameraPermissionState = CameraPermissionState.UNINITIALIZED,
    val shouldRequestPermission: Boolean = false,
    val userData: UserData = UserData(),
    val scanState: ScanState = ScanState.INIT,
    val messageErrorScan: String = "",
    val showCamera: Boolean = true
)