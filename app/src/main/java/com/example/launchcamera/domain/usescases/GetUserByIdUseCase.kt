package com.example.launchcamera.domain.usescases

import com.example.launchcamera.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: String) = userRepository.getUser(id)
}