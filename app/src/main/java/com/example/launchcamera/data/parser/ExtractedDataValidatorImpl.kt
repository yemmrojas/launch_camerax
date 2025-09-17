package com.example.launchcamera.data.parser

import com.example.launchcamera.domain.parser.ExtractedDataValidator
import javax.inject.Inject

class ExtractedDataValidatorImpl @Inject constructor() : ExtractedDataValidator {

    override fun isValidName(name: String): Boolean =
        name.isNotBlank() && name.length >= 2 && name.all { it.isLetter() || it.isWhitespace() }

    override fun isValidId(id: String): Boolean =
        id.isNotBlank() && id.replace(".", "").replace(" ", "").all { it.isDigit() }

    override fun isValidBirthDate(birthDate: String): Boolean =
        birthDate.isNotBlank() && birthDate.length >= 8

    override fun isValidCity(city: String): Boolean =
        city.isNotBlank() && city.length >= 2
}