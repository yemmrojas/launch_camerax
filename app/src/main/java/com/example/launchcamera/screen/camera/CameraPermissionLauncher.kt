package com.example.launchcamera.screen.camera

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.launchcamera.screen.camera.viewModel.CameraScannerViewModel

@Composable
fun CameraPermissionLauncher(
    viewModel: CameraScannerViewModel,
) {
    val shouldRequestPermission = viewModel.shouldRequestPermission.collectAsState()

    val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            CAMERA,
            READ_MEDIA_IMAGES
        )
    } else {
        arrayOf(
            CAMERA,
            WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        if (permissions.values.all { it }) {
            viewModel.onCameraPermissionResult(true)
        } else {
            viewModel.onCameraPermissionResult(false)
        }
    }

    LaunchedEffect(shouldRequestPermission.value) {
        if (shouldRequestPermission.value) {
            permissionLauncher.launch(permissionsToRequest)
        }
    }
}