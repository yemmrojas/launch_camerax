package com.example.launchcamera.domain.usecase

import com.example.launchcamera.domain.repository.UserRepository
import com.example.launchcamera.domain.usescases.UpdateUserUseCase
import com.example.launchcamera.mock.TypeMockSimulator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdateUserUseCaseTest {

    @Test
    fun `When the use case is called to update the user data and the id exists in the database then it returns the correct data`() =
        runTest {
            // Given
            val userRepository = providesUserRepository(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userRepository)
            // When
            val result = sut.invoke(USER_ID_TEST, EMAIL_VALUE_TEST, PHONE_NUMBER_TEST)
            // Then
            coVerify(exactly = 1) {
                userRepository.updateUser(
                    USER_ID_TEST,
                    EMAIL_VALUE_TEST,
                    PHONE_NUMBER_TEST
                )
            }
            assertTrue(result.isSuccess)
            assertEquals(EXPECTED_RESULT_TRUE, result.getOrNull())
        }

    @Test
    fun `When the use case is called and the repository returns an error then it returns a failure result`() =
        runTest {
            // Given
            val userRepository = providesUserRepository(TypeMockSimulator.ERROR)
            val sut = providesSut(userRepository)
            // When
            val result = sut.invoke(USER_ID_TEST, EMAIL_VALUE_TEST, PHONE_NUMBER_TEST)
            // Then
            coVerify(exactly = 1) {
                userRepository.updateUser(
                    any(),
                    any(),
                    any()
                )
            }
            assertTrue(result.isFailure)
            assertEquals(EXPECTED_EXCEPTION_MESSAGE, result.exceptionOrNull()?.message)
        }

    @Test
    fun `When the use case is called and the user id does not exist then it returns a success result with null data`() =
        runTest {
            // Given
            val userRepository = providesUserRepository(TypeMockSimulator.NULL)
            val sut = providesSut(userRepository)
            // When
            val result = sut.invoke(USER_ID_TEST, EMAIL_VALUE_TEST, PHONE_NUMBER_TEST)
            // Then
            coVerify(exactly = 1) {
                userRepository.updateUser(
                    any(),
                    any(),
                    any()
                )
            }
            assertTrue(result.isSuccess)
            assertEquals(EXPECTED_RESULT_FALSE, result.getOrNull())
        }

    @Test
    fun `When the use case is called and the repository returns empty user data then it returns a success result with empty UserData`() =
        runTest {
            // Given
            val userRepository = providesUserRepository(TypeMockSimulator.EMPTY)
            val sut = providesSut(userRepository)
            // When
            val result = sut.invoke(USER_ID_TEST, EMAIL_VALUE_TEST, PHONE_NUMBER_TEST)
            // Then
            assertTrue(result.isSuccess)
            assertEquals(EXPECTED_RESULT_TRUE, result.getOrNull())

        }

    private fun providesSut(userRepository: UserRepository) = UpdateUserUseCase(userRepository)

    private fun providesUserRepository(type: TypeMockSimulator) = mockk<UserRepository>() {
        when (type) {
            TypeMockSimulator.SUCCESS -> coEvery {
                updateUser(USER_ID_TEST, EMAIL_VALUE_TEST, PHONE_NUMBER_TEST)
            } returns Result.success(true)

            TypeMockSimulator.ERROR -> coEvery {
                updateUser(any(), any(), any())
            } returns Result.failure(Exception(EXPECTED_EXCEPTION_MESSAGE))

            TypeMockSimulator.NULL -> coEvery {
                updateUser(any(), any(), any())
            } returns Result.success(false)

            TypeMockSimulator.EMPTY -> coEvery {
                updateUser(any(), any(), any())
            } returns Result.success(true)
        }
    }

    companion object {
        private const val PHONE_NUMBER_TEST = "3110000000"
        private const val EMAIL_VALUE_TEST = "test@user.com"
        private const val USER_ID_TEST = "1234567"
        private const val EXPECTED_EXCEPTION_MESSAGE = "Database error"
        private const val EXPECTED_RESULT_TRUE = true
        private const val EXPECTED_RESULT_FALSE = false
    }
}