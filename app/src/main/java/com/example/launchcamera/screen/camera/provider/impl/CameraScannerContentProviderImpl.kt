package com.example.launchcamera.screen.camera.provider.impl

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import com.example.launchcamera.screen.camera.provider.CameraScannerContentProvider
import com.example.launchcamera.screen.camera.state.IconConfig
import com.example.launchcamera.screen.camera.state.ScanState
import com.example.launchcamera.ui.theme.Purple80
import javax.inject.Inject

class CameraScannerContentProviderImpl @Inject constructor() : CameraScannerContentProvider {
    override fun getTitle(scanState: ScanState): String =
        if (scanState == ScanState.ERROR) {
            TITLE_ERROR_SCAN
        } else {
            TITLE_CAMERA_SCANNER
        }

    override fun getDescription(
        scanState: ScanState,
        errorMessage: String
    ): String = when (scanState) {
        ScanState.INIT, ScanState.FRONT_SIDE -> DESCRIPTION_CAMERA_SCANNER_FRONT_SIDE
        ScanState.BACK_SIDE -> DESCRIPTION_CAMERA_SCANNER_BACK_SIDE
        ScanState.FINISH -> DESCRIPTION_CAMERA_SCANNER_FINISH
        ScanState.ERROR -> errorMessage.ifEmpty { "Unknown error occurred" }
    }

    override fun getButtonText(scanState: ScanState): String  =
        if (scanState == ScanState.FINISH) {
            CONTINUE_REGISTRY
        } else {
            BUTTON_CAMERA_SCANNER
        }

    override fun getIconConfig(scanState: ScanState): IconConfig =
        when (scanState) {
            ScanState.ERROR -> IconConfig(
                icon = Icons.Default.Close,
                color = Color.Red,
                contentDescription = DESCRIPTION_IMAGE_CAMERA_SCANNER
            )
            ScanState.FINISH -> IconConfig(
                icon = Icons.Default.Check,
                color = Purple80,
                contentDescription = DESCRIPTION_IMAGE_CAMERA_SCANNER
            )
            else -> IconConfig(
                icon = Icons.Default.Info,
                color = Purple80,
                contentDescription = DESCRIPTION_IMAGE_CAMERA_SCANNER
            )
        }

    companion object {

        private const val TITLE_CAMERA_SCANNER = "Scan of identity document"
        private const val DESCRIPTION_CAMERA_SCANNER_FRONT_SIDE =
            "Prepare your ID for front side scanning"
        private const val DESCRIPTION_CAMERA_SCANNER_BACK_SIDE =
            "Prepare your ID for back side scanning"
        private const val DESCRIPTION_CAMERA_SCANNER_FINISH =
            "Everything is ready for you to finish your registration."
        private const val BUTTON_CAMERA_SCANNER = "Start Scanning"
        private const val DESCRIPTION_IMAGE_CAMERA_SCANNER = "Image of camera"
        private const val CONTINUE_REGISTRY = "Continue registry"
        private const val TITLE_ERROR_SCAN = "Error Scan"
    }
}