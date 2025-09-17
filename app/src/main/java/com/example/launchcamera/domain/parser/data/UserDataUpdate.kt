package com.example.launchcamera.domain.parser.data

sealed class UserDataUpdate {
    data class Name(val value: String) : UserDataUpdate()
    data class LastName(val value: String) : UserDataUpdate()
    data class Id(val value: String) : UserDataUpdate()
    data class BirthDate(val value: String) : UserDataUpdate()
    data class City(val value: String) : UserDataUpdate()
    data class Multiple(val updates: List<UserDataUpdate>) : UserDataUpdate()
    object None : UserDataUpdate()
}