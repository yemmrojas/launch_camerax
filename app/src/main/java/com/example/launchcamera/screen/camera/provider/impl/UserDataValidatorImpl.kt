package com.example.launchcamera.screen.camera.provider.impl

import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.screen.camera.provider.UserDataValidator
import javax.inject.Inject


class UserDataValidatorImpl @Inject constructor() : UserDataValidator {
    override fun isUserDataComplete(userData: UserData): Boolean {
        return with(userData) {
            id.isNotEmpty() &&
            name.isNotEmpty() &&
            lastName.isNotEmpty() &&
            city.isNotEmpty() &&
            birthDate.isNotEmpty()
        }
    }
}