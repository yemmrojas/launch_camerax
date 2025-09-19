package com.example.launchcamera.domain.usescases

import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userData: UserData)  = userRepository.updateUser(userData)
}