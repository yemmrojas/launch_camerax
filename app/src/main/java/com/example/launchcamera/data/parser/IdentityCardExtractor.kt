package com.example.launchcamera.data.parser

import com.example.launchcamera.domain.parser.DocumentFieldExtractor
import com.example.launchcamera.domain.parser.ExtractedDataValidator
import com.example.launchcamera.domain.parser.TextUtils
import com.example.launchcamera.domain.parser.data.UserDataUpdate
import com.google.mlkit.vision.text.Text
import javax.inject.Inject

class IdentityCardExtractor @Inject constructor(
    private val textUtils: TextUtils,
    private val validator: ExtractedDataValidator
): DocumentFieldExtractor {

    override fun extractAllFields(recognizedText: Text): List<UserDataUpdate> {
        val updates = mutableListOf<UserDataUpdate>()
        recognizedText.textBlocks.forEach { block ->
            block.lines.forEach { line ->
                val normalizerText = textUtils.normalizeText(line.text)
                val nextLine = textUtils.getNextLine(block.lines, line)
                extractName(normalizerText, nextLine)?.let { updates.add(it) }
                extractLastName(normalizerText, nextLine)?.let { updates.add(it) }
                extractId(normalizerText)?.let { updates.add(it) }
                extractCityAndBirthDate(normalizerText, nextLine)?.let { updates.addAll(it) }
            }
        }
        return updates
    }

    private fun extractName(lineText: String, nextLine: String?): UserDataUpdate? {
        return if (lineText.contains(CONTAIN_NAME, ignoreCase = true)) {
            nextLine?.let { next ->
                val cleanName = textUtils.cleanNameText(next)
                if (validator.isValidName(cleanName)) {
                    UserDataUpdate.Name(cleanName)
                } else null
            }
        } else null
    }

    private fun extractLastName(lineText: String, nextLine: String?): UserDataUpdate? {
        return if (lineText.contains(CONTAIN_LAST_NAME, ignoreCase = true)) {
            nextLine?.let { next ->
                val cleanLastName = textUtils.cleanNameText(next)
                if (validator.isValidName(cleanLastName)) {
                    UserDataUpdate.LastName(cleanLastName)
                } else null
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

    private fun extractCityAndBirthDate(lineText: String, nextLine: String?): List<UserDataUpdate>? {
        return if (lineText.contains(CONTAIN_CITY_AND_BIRTH, ignoreCase = true)) {
            nextLine?.let { next ->
                val (birthDate, city) = textUtils.parseCityAndBirthDate(next)
                val updates = mutableListOf<UserDataUpdate>()

                birthDate?.let { bd ->
                    if (validator.isValidBirthDate(bd)) {
                        updates.add(UserDataUpdate.BirthDate(bd))
                    }
                }

                city?.let { c ->
                    if (validator.isValidCity(c)) {
                        updates.add(UserDataUpdate.City(c))
                    }
                }

                updates.takeIf { it.isNotEmpty() }
            }
        } else null
    }

    companion object {
        const val REGEX_ID_NUMBER = """(?:NUMERO|C\.C\.|T\.?I\.?|NIUT)\s*([\d\.\s]+)"""
        const val CONTAIN_NAME = "nombres"
        const val CONTAIN_LAST_NAME = "apellidos"
        const val CONTAIN_CITY_AND_BIRTH = "fecha y lugar de expedición"
    }
}