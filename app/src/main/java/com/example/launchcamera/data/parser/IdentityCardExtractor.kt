package com.example.launchcamera.data.parser

import android.util.Log
import com.example.launchcamera.domain.parser.DocumentFieldExtractor
import com.example.launchcamera.domain.parser.ExtractedDataValidator
import com.example.launchcamera.domain.parser.TextUtils
import com.example.launchcamera.domain.parser.data.UserDataUpdate
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class IdentityCardExtractor @Inject constructor(
    private val textUtils: TextUtils,
    private val validator: ExtractedDataValidator
) : DocumentFieldExtractor {

    private val regexNameOrLastName = Regex(REGEX_KEY_LASTNAME_OR_NAME, RegexOption.IGNORE_CASE)

    override fun extractAllFields(recognizedText: Text): List<UserDataUpdate> {
        val updates = mutableListOf<UserDataUpdate>()
        val extractList = extractAllTextLines(recognizedText)
        extractList.forEachIndexed { index, text ->
            val normalizerText = textUtils.normalizeText(text)
            val nextLine = textUtils.getNextLine(extractList, index)
            extractName(normalizerText, nextLine)?.let { updates.add(it) }
            extractLastName(normalizerText, nextLine)?.let { updates.add(it) }
            extractId(normalizerText)?.let { updates.add(it) }
            extractBirthDate(normalizerText, nextLine)?.let { updates.add(it) }
            extractPlaceBirth(normalizerText, nextLine)?.let { updates.add(it) }
        }
        println("coincidencia lista: $updates")
        return updates
    }

    fun extractAllTextLines(recognizedText: Text): List<String> {
        val lines = mutableListOf<String>()
        recognizedText.textBlocks.forEach { block ->
            block.lines.forEach { line ->
                lines.add(line.text.trim())
            }
        }
        Log.d("IdentityCardExtractor", "Extracted lines: $lines")
        return lines
    }

    private fun extractName(lineText: String, nextLine: String?): UserDataUpdate? {
        return if (regexNameOrLastName.containsMatchIn(lineText)) {
            nextLine?.let { next ->
                val cleanName = textUtils.cleanNameText(next)
                if (validator.isValidName(cleanName)) {
                    UserDataUpdate.Name(cleanName)
                } else UserDataUpdate.None
            }
        } else null
    }

    private fun extractLastName(lineText: String, nextLine: String?): UserDataUpdate? {
        return if (regexNameOrLastName.containsMatchIn(lineText)) {
            nextLine?.let { next ->
                val cleanLastName = textUtils.cleanNameText(next)
                if (validator.isValidName(cleanLastName)) {
                    UserDataUpdate.LastName(cleanLastName)
                } else UserDataUpdate.None
            }
        } else null
    }

    private fun extractId(lineText: String): UserDataUpdate? {
        val idNumber = textUtils.extractIdFromText(lineText, REGEX_ID_NUMBER)
        return idNumber?.let { id ->
            if (validator.isValidId(id)) {
                UserDataUpdate.Id(id)
            } else null
        }
    }

    private fun extractBirthDate(lineText: String, nextLine: String?): UserDataUpdate? {
        return if (lineText.contains(REGEX_KEY_BIRTH, ignoreCase = true)) {
            nextLine?.let { next ->
                if (validator.isValidBirthDate(next)) {
                    UserDataUpdate.BirthDate(next)
                } else UserDataUpdate.None
            }
        } else null
    }

    private fun extractPlaceBirth(lineText: String, nextLine: String?): UserDataUpdate? {
        return if (lineText.contains(REGEX_KEY_CITY, ignoreCase = true)) {
            nextLine?.let { next ->
                if (validator.isValidBirthDate(next)) {
                    UserDataUpdate.City(next)
                } else null
            }
        } else null
    }

    companion object {
        const val REGEX_ID_NUMBER = """([\d.]+)"""
        const val REGEX_KEY_LASTNAME_OR_NAME = "Nombres|Nom|Nomb|nombes|mombes|Apellidos|Apel|Apell|Ape11idos|ApeIIidos|apelidos"
        const val REGEX_KEY_CITY = "Lugar de n"
        const val REGEX_KEY_BIRTH = "Fecha de n"
    }
}