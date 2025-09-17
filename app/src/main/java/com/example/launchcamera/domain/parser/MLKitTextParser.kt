package com.example.launchcamera.domain.parser

import com.example.launchcamera.domain.model.UserData
import com.google.mlkit.vision.text.Text

interface MLKitTextParser {
    fun parse(recognizedText: Text, data: UserData) : UserData
}