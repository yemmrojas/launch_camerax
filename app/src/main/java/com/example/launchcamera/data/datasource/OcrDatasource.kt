package com.example.launchcamera.data.datasource

import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.text.Text

interface OcrDatasource {
    suspend fun recognizeText(imageProxy: ImageProxy): Result<Text>
}