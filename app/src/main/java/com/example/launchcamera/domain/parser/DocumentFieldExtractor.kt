package com.example.launchcamera.domain.parser

import com.example.launchcamera.domain.parser.data.UserDataUpdate
import com.google.mlkit.vision.text.Text

interface DocumentFieldExtractor {
    fun extractAllFields(recognizedText: Text): List<UserDataUpdate>
}