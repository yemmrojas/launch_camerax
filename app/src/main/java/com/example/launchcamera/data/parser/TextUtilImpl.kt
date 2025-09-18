package com.example.launchcamera.data.parser

import com.example.launchcamera.data.parser.IdentityCardExtractor.Companion.REGEX_KEY_BIRTH
import com.example.launchcamera.data.parser.IdentityCardExtractor.Companion.REGEX_KEY_CITY
import com.example.launchcamera.data.parser.IdentityCardExtractor.Companion.REGEX_KEY_LASTNAME_OR_NAME
import com.example.launchcamera.domain.parser.TextUtils
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class TextUtilImpl @Inject constructor() : TextUtils {

    override fun getNextLine(
        lines: List<String>,
        currentLine: Int
    ): String? {
        val keyValue = lines[currentLine]
        val valueRegex = when {
            regexConverter(REGEX_KEY_LASTNAME_OR_NAME).containsMatchIn(keyValue) -> {
                regexConverter(REGEX_LASTNAME_OR_NAME)
            }
            keyValue.contains(REGEX_KEY_CITY, ignoreCase = true) -> {
                regexConverter(REGEX_CITY)
            }
            keyValue.contains(REGEX_KEY_BIRTH, ignoreCase = true) -> {
                regexConverter(REGEX_DATE)
            }
            else -> return null
        }
        var foundValue = getValueRight(lines, currentLine, valueRegex)

        if (foundValue.isEmpty()) {
            foundValue = getValueLeft(lines, currentLine, valueRegex)
        }

        return foundValue
    }

    private fun regexConverter(text: String) = text.toRegex(RegexOption.IGNORE_CASE)

    private fun getValueRight(
        list: List<String>,
        currentLine: Int,
        regexPattern: Regex
    ): String {
        val startIndex = currentLine + 1
        val endIndex = min(list.size - 1, currentLine + 2)
        val value = list[currentLine]

        if (startIndex > endIndex) return ""

        for (i in startIndex..endIndex) {
            val potentialValue = list[i]
            if (regexPattern.containsMatchIn(potentialValue) && value != potentialValue) {
                return potentialValue
            }
        }
        return EMPTY
    }

    private fun getValueLeft(
        list: List<String>,
        currentLine: Int,
        regexPattern: Regex
    ): String {
        val startIndex = currentLine - 1
        val endIndex = max(0, currentLine - 2)
        val value = list[currentLine]

        if (startIndex < endIndex) return ""

        for (i in startIndex downTo endIndex) {
            val potentialValue = list[i]
            if (regexPattern.containsMatchIn(potentialValue) && value != potentialValue) {
                return potentialValue
            }
        }
        return EMPTY
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

    companion object {
        const val EMPTY = ""
        const val REGEX_CITY = "[()]"
        const val REGEX_LASTNAME_OR_NAME = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+(\\s+[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+)*$"
        const val REGEX_DATE = """(\d{1,2})\s+([A-Za-zÁÉÍÓÚÑáéíóúñ]+)\s+(\d{4})"""
    }
}