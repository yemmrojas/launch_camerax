package com.example.launchcamera.domain.usescases

import com.example.launchcamera.domain.repository.LoginLocalDataRepository
import javax.inject.Inject

class DeleteUserIdUseCase @Inject constructor(
    private val saveLoginRepository: LoginLocalDataRepository
) {
    suspend operator fun invoke() = saveLoginRepository.deleteId()
}
