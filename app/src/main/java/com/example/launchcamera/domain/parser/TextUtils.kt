package com.example.launchcamera.domain.parser

import com.google.mlkit.vision.text.Text

interface TextUtils {
    fun getNextLine(lines: List<Text.Line>, currentLine: Text.Line): String?
    fun normalizeText(text: String): String
    fun cleanNameText(text: String): String
    fun extractIdFromText(text: String, regex: String): String?
    fun parseCityAndBirthDate(text: String): Pair<String?, String?>
}