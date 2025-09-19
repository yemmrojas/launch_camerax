package com.example.launchcamera.domain.repository

import com.example.launchcamera.data.datasource.dao.UserDao
import com.example.launchcamera.data.model.UserDataEntity
import com.example.launchcamera.domain.model.UserData
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun saveUser(userData: UserData): Result<Unit> =
        try {
            val userEntity = converterUserEntity(userData)
            userDao.insertUserData(userEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getUser(id: String): Result<UserData?> =
        try {
            val result = userDao.getUserById(id)
            val resultData = result?.let { converterUser(it) }
            Result.success(resultData)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun updateUser(userData: UserData): Result<Boolean> =
        try {
            val entity = converterUserEntity(userData)
            userDao.updateUserData(entity)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun deleteUser(id: String): Result<Boolean> =
        try {
            userDao.deleteUserData(id)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    private fun converterUserEntity(userData: UserData) = UserDataEntity(
        id = userData.id,
        name = userData.name,
        lastName = userData.lastName,
        birthDate = userData.birthDate,
        city = userData.city,
        email = userData.email,
        contact = userData.contact
    )

    private fun converterUser(userDataEntity: UserDataEntity) = UserData(
        id = userDataEntity.id,
        name = userDataEntity.name,
        lastName = userDataEntity.lastName,
        birthDate = userDataEntity.birthDate,
        city = userDataEntity.city,
        email = userDataEntity.email,
        contact = userDataEntity.contact
    )
}