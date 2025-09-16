package com.example.launchcamera.screen.camera.provider

import com.example.launchcamera.screen.camera.state.IconConfig
import com.example.launchcamera.screen.camera.state.ScanState

interface CameraScannerContentProvider {
    fun getTitle(scanState: ScanState): String
    fun getDescription(scanState: ScanState, errorMessage: String): String
    fun getButtonText(scanState: ScanState): String
    fun getIconConfig(scanState: ScanState): IconConfig
}