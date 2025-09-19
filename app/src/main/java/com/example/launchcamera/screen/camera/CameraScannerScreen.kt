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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.launchcamera.screen.camera.state.CameraPermissionState
import com.example.launchcamera.screen.camera.state.IconConfig
import com.example.launchcamera.screen.camera.state.ScanState
import com.example.launchcamera.screen.camera.viewModel.CameraScannerViewModel
import com.example.launchcamera.screen.components.ButtonSecondary
import com.example.launchcamera.screen.components.ProgressBarView
import com.example.launchcamera.screen.components.TextContent
import com.example.launchcamera.screen.components.TextTitle
import com.example.launchcamera.ui.theme.Purple40

internal const val CAMERA_SCANNER_ROUTE = "camera"
private const val DESCRIPTION_IMAGE_CAMERA_SCANNER = "Image of camera"

@Composable
fun CameraScannerScreen(
    viewModel: CameraScannerViewModel,
    onSuccessScanner: (String, String) -> Unit
) {

    val cameraScannerState = viewModel.cameraScannerState.collectAsState().value

    when {
        cameraScannerState.shouldRequestPermission -> CameraPermissionLauncher(viewModel = viewModel)
        cameraScannerState.cameraPermissionState == CameraPermissionState.GRANTED &&
                cameraScannerState.showCamera -> {
            CameraFullScreenView(viewModel)
        }

        else -> CameraScannerWelcomeScreen(
            viewModel,
            onSuccessScanner,
        )
    }
}

@Composable
private fun CameraScannerWelcomeScreen(
    viewModel: CameraScannerViewModel,
    onSuccessScanner: (String, String) -> Unit
) {
    val verticalArrangement: Arrangement.Vertical = Arrangement.Center
    val cameraScannerState = viewModel.cameraScannerState.collectAsState().value
    val scanState = cameraScannerState.scanState
    val messageErrorScan = cameraScannerState.messageErrorScan
    val title = viewModel.getTitle(scanState)
    val description = viewModel.getDescription(
        scanState,
        messageErrorScan
    )
    val iconConfig = viewModel.getIconConfig(scanState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40),
        verticalArrangement = verticalArrangement
    ) {
        if (viewModel.isProgress) {
            ProgressBarView()
        } else {
            ImageCamera(iconConfig)
            TitleCameraScanner(title)
            DescriptionCameraScanner(description)
            ButtonCameraScanner(
                viewModel,
                onSuccessScanner
            )
        }
    }
}

@Composable
private fun ImageCamera(
    iconConfig: IconConfig
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = iconConfig.icon,
            contentDescription = DESCRIPTION_IMAGE_CAMERA_SCANNER,
            tint = iconConfig.color,
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
    onSuccessScanner: (String, String) -> Unit
) {
    val cameraScannerState = viewModel.cameraScannerState.collectAsState().value
    val scanState = cameraScannerState.scanState

    ButtonSecondary(
        text = viewModel.getButtonText(scanState),
        onClick = {
            if (cameraScannerState.scanState == ScanState.FINISH) {
                onSuccessScanner(cameraScannerState.userData.name, cameraScannerState.userData.id)
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
