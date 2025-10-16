package com.example.launchcamera.domain.usescases

import com.example.launchcamera.domain.repository.LoginLocalDataRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val loginLocalDataRepository: LoginLocalDataRepository
) {
    suspend operator fun invoke() = loginLocalDataRepository.getId()
}
