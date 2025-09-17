package com.example.launchcamera.domain.parser

import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.parser.data.UserDataUpdate

interface UserDataUpdater {
    fun applyUpdates(userData: UserData, updates: List<UserDataUpdate>): UserData
}