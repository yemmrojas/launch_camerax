package com.example.launchcamera.domain.repository

import androidx.camera.core.ImageProxy
import com.example.launchcamera.domain.model.UserData

interface OcrRepository {
    suspend fun extractTextFromImage(image: ImageProxy, data: UserData): Result<UserData>
}