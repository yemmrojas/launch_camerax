package com.example.launchcamera.screen.camera.provider

import com.example.launchcamera.domain.model.UserData

interface UserDataValidator {
    fun isUserDataComplete(userData: UserData): Boolean
}