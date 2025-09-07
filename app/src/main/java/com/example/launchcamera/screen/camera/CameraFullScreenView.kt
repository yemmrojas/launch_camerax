package com.example.launchcamera.screen.camera

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraFullScreenView(
) {
    val context = LocalContext.current
    val cameraController = remember { LifecycleCameraController(context).apply {
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    } }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.currentStateFlow.collect { state ->
            Log.d("CameraLifecycle", "El estado del ciclo de vida es: $state")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    takePhotoInternal(
                        controller = cameraController,
                        context = context,
                        onPhotoCaptured = {},
                        onCaptureError = {}
                    )
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "Capture photo")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        CameraPreview(
            cameraController = cameraController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
fun CameraPreview(
    cameraController: LifecycleCameraController,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        factory = { context ->
            PreviewView(context).apply {
                controller = cameraController
            }
        },
        modifier = modifier
    )

}

private fun takePhotoInternal(
    controller: LifecycleCameraController,
    context: Context,
    onPhotoCaptured: (ImageProxy) -> Unit,
    onCaptureError: (ImageCaptureException) -> Unit
) {
}