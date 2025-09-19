package com.example.launchcamera.domain.repository

import com.example.launchcamera.domain.model.UserData

interface UserRepository {
    suspend fun saveUser(userData: UserData): Result<Unit>
    suspend fun getUser(id: String): Result<UserData?>
    suspend fun updateUser(id: String, email: String, phone: String): Result<Boolean>
    suspend fun deleteUser(id: String): Result<Boolean>
}