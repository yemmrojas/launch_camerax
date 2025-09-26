package com.example.launchcamera.mock.provides

import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.repository.UserRepository
import com.example.launchcamera.mock.TypeMockSimulator
import com.example.launchcamera.mock.data.getUserDataMockk
import io.mockk.coEvery
import io.mockk.mockk

fun providesGetUserByIdRepository(type: TypeMockSimulator) = mockk<UserRepository>() {
        when (type) {
            TypeMockSimulator.SUCCESS -> coEvery { getUser(any()) } returns Result.success(
                getUserDataMockk()
            )

            TypeMockSimulator.ERROR -> coEvery { getUser(any()) } returns Result.failure(Exception("Database error"))
            TypeMockSimulator.NULL -> coEvery { getUser(any()) } returns Result.success(null)
            TypeMockSimulator.EMPTY -> coEvery { getUser(any()) } returns Result.success(UserData())
        }
    }