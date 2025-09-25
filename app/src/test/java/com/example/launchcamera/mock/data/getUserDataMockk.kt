package com.example.launchcamera.mock.data

import com.example.launchcamera.domain.model.UserData

fun getUserDataMockk() = UserData(
        id = "12345678",
        name = "Pablo",
        lastName = "Rosado Galán",
        city = "Bogotá D.C",
        birthDate = "19 Mayo 2021",
        email = "pablotest@user.com",
        contact = "+57 3112314567"
)