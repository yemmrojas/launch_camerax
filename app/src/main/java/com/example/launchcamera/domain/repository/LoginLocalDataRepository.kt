package com.example.launchcamera.domain.repository

interface LoginLocalDataRepository {
    suspend fun saveId(id: String)
    suspend fun getId(): String?
    suspend fun deleteId()
}