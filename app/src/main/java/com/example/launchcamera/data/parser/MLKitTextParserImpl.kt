package com.example.launchcamera.data.parser

import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.parser.MLKitTextParser
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class MLKitTextParserImpl @Inject constructor(): MLKitTextParser {
    override fun parse(recognizedText: Text, data: UserData): UserData {
        val newUserData = data.copy()
        recognizedText.textBlocks.forEach { block ->
            block.lines.forEach { line ->
                val linesText = line.text.uppercase()

                if (linesText.contains(CONTAIN_NAME.uppercase())) {
                    val nextLine  = getNextLine(block.lines, line)
                    if (nextLine != null) newUserData.copy(name = linesText)
                }
                if (linesText.contains(CONTAIN_LAST_NAME.uppercase())) {
                    val nextLine  = getNextLine(block.lines, line)
                    if (nextLine != null) newUserData.copy(lastName = linesText)
                }
                if (linesText.contains(CONTAIN_CITY.uppercase())) {
                    val nextLine  = getNextLine(block.lines, line)
                    if (nextLine != null) {
                        val result = nextLine.split(",")
                        newUserData.copy(birthDate = result[0])
                        newUserData.copy(city = result[1])
                    }
                }
                if (linesText.contains(REGEX_ID_NUMBER)) {
                    val nextLine  = getNextLine(block.lines, line)
                    if (nextLine != null) newUserData.copy(id = linesText)
                }
            }
        }
        return newUserData
    }

    private fun getNextLine(lines: List<Text.Line>, currentLine: Text.Line): String? {
        val currentIndex = lines.indexOf(currentLine)
        return if (currentIndex != -1 && currentIndex + 1 < lines.size) {
            lines[currentIndex + 1].text
        } else {
            null
        }
    }

    companion object {
        private const val REGEX_ID_NUMBER = """(?:NUMERO|C\.C\.|T\.?I\.?|NIUT)\s*([\d\.\s]+)"""
        private const val CONTAIN_NAME = "nombres"
        private const val CONTAIN_LAST_NAME = "apellidos"
        private const val CONTAIN_CITY = "fecha y lugar de expedixión"
    }
}
