package com.example.launchcamera.domain.parser

interface ExtractedDataValidator {
    fun isValidName(name: String): Boolean
    fun isValidId(id: String): Boolean
    fun isValidBirthDate(birthDate: String): Boolean
    fun isValidCity(city: String): Boolean
}