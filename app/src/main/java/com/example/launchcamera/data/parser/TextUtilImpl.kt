package com.example.launchcamera.data.parser

import com.example.launchcamera.domain.parser.TextUtils
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class TextUtilImpl @Inject constructor() : TextUtils {

    override fun getNextLine(
        lines: List<Text.Line>,
        currentLine: Text.Line
    ): String? {
        val currentIndex = lines.indexOf(currentLine)
        return if (currentIndex != -1 && currentIndex + 1 < lines.size) {
            lines[currentIndex + 1].text
        } else {
            null
        }
    }

    override fun normalizeText(text: String): String = text.uppercase().trim()

    override fun cleanNameText(text: String): String =
        text.trim().split("\\s+".toRegex()).joinToString(" ") { work ->
            work.replaceFirstChar { it.uppercase() }
        }

    override fun extractIdFromText(text: String, regex: String): String? {
        val matchResult = regex.toRegex().find(text)
        return matchResult?.groupValues?.get(1)?.replace(".", "")?.replace(" ", "")
    }

    override fun parseCityAndBirthDate(text: String): Pair<String?, String?> {
        val parts = text.split(",", "/", "-").map { it.trim() }
        return if (parts.size >= 2) {
            Pair(parts[0], parts[1])
        } else {
            Pair(null, null)
        }
    }
}