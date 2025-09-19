package com.example.launchcamera.domain.usescases

import androidx.camera.core.ImageProxy
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.repository.OcrRepository
import javax.inject.Inject

class ExtractTextFromImageUseCase @Inject constructor(
    private val ocrRepository: OcrRepository
) {
    suspend operator fun invoke(image: ImageProxy, data: UserData) =
        ocrRepository.extractTextFromImage(image, data)
}