package com.example.launchcamera.screen.camera

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.Executor

@Composable
fun CameraFullScreenView(
) {
    val context = LocalContext.current
    val imageCapture = remember { ImageCapture.Builder().build() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    takePhotoInternal(
                        context = context,
                        onPhotoCaptured = { imageProxy ->
                            Log.d("CameraFullScreenView", "Photo captured: $imageProxy")
                            imageProxy.close()
                        },
                        onCaptureError = { exception ->
                            Log.e("CameraFullScreenView", "Photo capture failed: ${exception.message}", exception)
                        },
                        imageCapture = imageCapture
                    )
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "Capture photo")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        CameraPreview(
            imageCapture = imageCapture,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
fun CameraPreview(
    imageCapture: ImageCapture,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }

            // Inicia el proceso de vinculación
            bindCameraUseCases(
                cameraProviderFuture = cameraProviderFuture,
                previewView = previewView,
                lifecycleOwner = lifecycleOwner,
                context = context,
                imageCapture = imageCapture // También le pasamos ImageCapture aquí
            )

            previewView
        }
    )
}

private fun bindCameraUseCases(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    previewView: PreviewView,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    context: Context,
    imageCapture: ImageCapture
) {
    val executor: Executor = ContextCompat.getMainExecutor(context)

    cameraProviderFuture.addListener({
        try {
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
            Log.d("CameraBinding", "Camera bound successfully.")

        } catch (e: Exception) {
            Log.e("CameraBinding", "Failed to bind camera use cases", e)
        }
    }, executor)
}

private fun takePhotoInternal(
    context: Context,
    onPhotoCaptured: (ImageProxy) -> Unit,
    onCaptureError: (ImageCaptureException) -> Unit,
    imageCapture: ImageCapture
) {
    val executor = ContextCompat.getMainExecutor(context)
    imageCapture.takePicture(
        executor,
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                Log.d("TakePhoto", "Photo capture succeeded")
                onPhotoCaptured(image)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("TakePhoto", "Photo capture failed: ${exception.message}", exception)
                onCaptureError(exception)
            }
        }
    )
}