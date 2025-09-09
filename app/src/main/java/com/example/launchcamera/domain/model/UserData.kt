package com.example.launchcamera.domain.model

data class UserData(
    val name: String,
    val lastName: String,
    val id: String,
    val birthDate: String,
    val city: String,
    val email: String? = null,
    val contact: String? = null
)