package com.example.launchcamera.data.parser

import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.parser.UserDataUpdater
import com.example.launchcamera.domain.parser.data.UserDataUpdate
import javax.inject.Inject

class UserDataUpdaterImpl @Inject constructor() : UserDataUpdater {
    override fun applyUpdates(
        userData: UserData,
        updates: List<UserDataUpdate>
    ): UserData =
        updates.fold(userData) { acc, update ->
            applyUpdate(acc, update)
        }

    private fun applyUpdate(userData: UserData, update: UserDataUpdate): UserData =
        when (update) {
            is UserDataUpdate.Name -> userData.copy(name = update.value)
            is UserDataUpdate.LastName -> userData.copy(lastName = update.value)
            is UserDataUpdate.Id -> userData.copy(id = update.value)
            is UserDataUpdate.BirthDate -> userData.copy(birthDate = update.value)
            is UserDataUpdate.City -> userData.copy(city = update.value)
            is UserDataUpdate.Multiple -> {
                update.updates.fold(userData) { acc, singleUpdate ->
                    applyUpdate(acc, singleUpdate)
                }
            }

            is UserDataUpdate.None -> userData
        }
}