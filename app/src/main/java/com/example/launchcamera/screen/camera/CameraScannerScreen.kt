package com.example.launchcamera.screen.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.launchcamera.screen.camera.viewModel.CameraScannerViewModel
import com.example.launchcamera.screen.components.ButtonSecondary
import com.example.launchcamera.screen.components.TextContent
import com.example.launchcamera.screen.components.TextTitle
import com.example.launchcamera.ui.theme.Purple40
import com.example.launchcamera.ui.theme.Purple80

internal const val CAMERA_SCANNER_ROUTE = "camera"

private const val TITLE_CAMERA_SCANNER = "Scan of identity document"
private const val DESCRIPTION_CAMERA_SCANNER_FRONT_SIDE = "Prepare your ID for front side scanning"
private const val DESCRIPTION_CAMERA_SCANNER_BACK_SIDE = "Prepare your ID for back side scanning"
private const val DESCRIPTION_CAMERA_SCANNER_FINISH =
    "Everything is ready for you to finish your registration."
private const val BUTTON_CAMERA_SCANNER = "Start Scanning"
private const val DESCRIPTION_IMAGE_CAMERA_SCANNER = "Image of camera"
private const val CONTINUE_REGISTRY = "Continue registry"
private const val TITLE_ERROR_SCAN = "Error Scan"

@Composable
fun CameraScannerScreen(
    viewModel: CameraScannerViewModel,
    onSuccessScanner: (String) -> Unit
) {

    val shouldRequestPermission = viewModel.shouldRequestPermission.collectAsState()
    val cameraPermissionState = viewModel.cameraPermissionState.collectAsState()
    val showCamera = viewModel.showCamera.collectAsState()

    if (shouldRequestPermission.value) {
        CameraPermissionLauncher(viewModel = viewModel)
    }

    if (cameraPermissionState.value == CameraPermissionState.GRANTED && showCamera.value) {
        CameraFullScreenView(viewModel)
    } else {
        CameraScannerWelcomeScreen(viewModel, onSuccessScanner)
    }

}

@Composable
private fun CameraScannerWelcomeScreen(
    viewModel: CameraScannerViewModel,
    onSuccessScanner: (String) -> Unit
) {
    val verticalArrangement: Arrangement.Vertical = Arrangement.Center
    val scanState = viewModel.scanState.collectAsState()
    val messageErrorScan = viewModel.messageErrorScan.collectAsState()
    val title = if (scanState.value == ScanState.ERROR) TITLE_ERROR_SCAN else TITLE_CAMERA_SCANNER
    val description = when (scanState.value) {
        ScanState.INIT, ScanState.FRONT_SIDE -> DESCRIPTION_CAMERA_SCANNER_FRONT_SIDE
        ScanState.BACK_SIDE -> DESCRIPTION_CAMERA_SCANNER_BACK_SIDE
        ScanState.FINISH -> DESCRIPTION_CAMERA_SCANNER_FINISH
        else -> messageErrorScan.value.isEmpty()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40),
        verticalArrangement = verticalArrangement
    ) {
        ImageCamera(scanState.value)
        TitleCameraScanner(title)
        DescriptionCameraScanner(description.toString())
        ButtonCameraScanner(viewModel, onSuccessScanner)
    }
}

@Composable
private fun ImageCamera(scanState: ScanState) {

    val iconColor = if (scanState.name == ScanState.ERROR.name) Color.Red else Purple80
    val imageIcon = if (scanState.name == ScanState.ERROR.name) Icons.Default.Close else Icons.Default.Info
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageIcon,
            contentDescription = DESCRIPTION_IMAGE_CAMERA_SCANNER,
            tint = iconColor,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
        )
    }
}

@Composable
private fun TitleCameraScanner(title: String) {
    TextTitle(
        title = title,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(
                start = 32.dp,
                end = 32.dp
            )
            .fillMaxWidth()
    )
}

@Composable
private fun DescriptionCameraScanner(description: String) {
    TextContent(
        content = description,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(
                top = 16.dp,
                start = 32.dp,
                end = 32.dp,
                bottom = 32.dp
            )
            .fillMaxWidth()
    )
}

@Composable
private fun ButtonCameraScanner(
    viewModel: CameraScannerViewModel,
    onSuccessScanner: (String) -> Unit
) {
    val scanState = viewModel.scanState.collectAsState()
    val isScanSuccess = scanState.value == ScanState.FINISH
    ButtonSecondary(
        text = if (isScanSuccess) CONTINUE_REGISTRY else BUTTON_CAMERA_SCANNER,
        onClick = {
            if (scanState.value == ScanState.FINISH) {
                    onSuccessScanner(viewModel.userData.value.id)
                } else {
                    viewModel.onMainButtonPressed()
                }
            },
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    bottom = 32.dp
                )
            )
        }
