package com.example.launchcamera.domain.repository

import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.text.Text

interface OcrDataRepository {
    suspend fun recognizeText(imageProxy: ImageProxy): Result<Text>
}