package com.example.launchcamera.repository

import androidx.camera.core.ImageProxy
import com.example.launchcamera.data.datasource.OcrDatasource
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.parser.MLKitTextParser
import javax.inject.Inject

class OcrRepositoryImpl @Inject constructor(
    private val ocrDatasource: OcrDatasource,
    private val mlKitTextParser: MLKitTextParser
): OcrRepository {
    override suspend fun extractTextFromImage(image: ImageProxy, data: UserData): Result<UserData> =
       ocrDatasource.recognizeText(image).map { mlKitTextParser.parse(it, data) } }