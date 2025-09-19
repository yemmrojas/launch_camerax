package com.example.launchcamera.data.datasource

import androidx.camera.core.ImageProxy
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.parser.MLKitTextParser
import com.example.launchcamera.domain.repository.OcrDataRepository
import com.example.launchcamera.domain.repository.OcrRepository
import javax.inject.Inject

class OcrRepositoryImpl @Inject constructor(
    private val ocrDatasource: OcrDataRepository,
    private val mlKitTextParser: MLKitTextParser
): OcrRepository {
    override suspend fun extractTextFromImage(image: ImageProxy, data: UserData): Result<UserData> =
       ocrDatasource.recognizeText(image).map { mlKitTextParser.parse(it, data) } }