package com.example.launchcamera.domain.usecase

import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.repository.UserRepository
import com.example.launchcamera.domain.usescases.GetUserByIdUseCase
import com.example.launchcamera.mock.TypeMockSimulator
import com.example.launchcamera.mock.data.getUserDataMockk
import com.example.launchcamera.mock.provides.providesGetUserByIdRepository
import io.mockk.coVerify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetUserByIdUseCaseTest {

    @Test
    fun `When the use case is called to get the user data and the id exists in the database then it returns the correct data`() =
        runTest {
            // Given
            val userId = "1234567"
            val userRepository = providesGetUserByIdRepository(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userRepository)
            // When
            val result = sut.invoke(userId)
            val data = result.getOrNull()
            // Then
            coVerify(exactly = 1) { userRepository.getUser(any()) }
            assert(result.isSuccess)
            assertEquals(getUserDataMockk().id, data?.id)
            assertEquals(getUserDataMockk().name, data?.name)
            assertEquals(getUserDataMockk().lastName, data?.lastName)
            assertEquals(getUserDataMockk().city, data?.city)
            assertEquals(getUserDataMockk().birthDate, data?.birthDate)
            assertEquals(getUserDataMockk().email, data?.email)
            assertEquals(getUserDataMockk().contact, data?.contact)
        }

    @Test
    fun `When the use case is called and the repository returns an error then it returns a failure result`() =
        runTest {
            // Given
            val userId = "errorId"
            val expectedException = Exception("Database error")
            val userRepository = providesGetUserByIdRepository(TypeMockSimulator.ERROR)
            val sut = providesSut(userRepository)
            // When
            val result = sut.invoke(userId)
            // Then
            coVerify(exactly = 1) { userRepository.getUser(userId) }
            assert(result.isFailure)
            assertEquals(expectedException.message, result.exceptionOrNull()?.message)
        }

    @Test
    fun `When the use case is called and the user id does not exist then it returns a success result with null data`() =
        runTest {
            // Given
            val userId = "nonExistingId"
            val userRepository = providesGetUserByIdRepository(TypeMockSimulator.NULL)
            val sut = providesSut(userRepository)
            // When
            val result = sut.invoke(userId)
            val data = result.getOrNull()
            // Then
            coVerify(exactly = 1) { userRepository.getUser(userId) }
            assert(result.isSuccess)
            Assert.assertNull(data)
        }

    @Test
    fun `When the use case is called and the repository returns empty user data then it returns a success result with empty UserData`() =
        runTest {
            // Given
            val userId = "emptyDataId"
            val userRepository = providesGetUserByIdRepository(TypeMockSimulator.EMPTY)
            val sut = providesSut(userRepository)
            // When
            val result = sut.invoke(userId)
            val data = result.getOrNull()
            // Then
            coVerify(exactly = 1) { userRepository.getUser(userId) }
            assert(result.isSuccess)
            assertEquals(UserData(), data)
        }

    private fun providesSut(
        userRepository: UserRepository
    ) = GetUserByIdUseCase(userRepository)
}