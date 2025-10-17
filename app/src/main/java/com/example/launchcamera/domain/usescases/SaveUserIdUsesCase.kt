package com.example.launchcamera.domain.usescases

import com.example.launchcamera.domain.repository.LoginLocalDataRepository
import javax.inject.Inject

class SaveUserIdUsesCase @Inject constructor(
    private val loginLocalDataRepository: LoginLocalDataRepository
) {
    suspend operator fun invoke(id: String) = loginLocalDataRepository.saveId(id)
}
