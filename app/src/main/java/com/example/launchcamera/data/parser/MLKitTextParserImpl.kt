package com.example.launchcamera.data.parser

import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.parser.DocumentFieldExtractor
import com.example.launchcamera.domain.parser.MLKitTextParser
import com.example.launchcamera.domain.parser.UserDataUpdater
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class MLKitTextParserImpl @Inject constructor(
    private val userDataUpdater: UserDataUpdater,
    private val documentFieldExtractor: DocumentFieldExtractor
): MLKitTextParser {
    override fun parse(recognizedText: Text, data: UserData): UserData {
        val extractor = documentFieldExtractor.extractAllFields(recognizedText)
        val userData = userDataUpdater.applyUpdates(data, extractor)
        println("user data value: $userData")
        return userData
    }
}