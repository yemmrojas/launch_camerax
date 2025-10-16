package com.example.launchcamera.domain.usecase

import com.example.launchcamera.MainCoroutineRule
import com.example.launchcamera.domain.repository.LoginLocalDataRepository
import com.example.launchcamera.domain.usescases.GetUserIdUseCase
import com.example.launchcamera.mock.TypeMockSimulator
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel.Companion.EMPTY_STRING
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserIdUseCaseTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when getId is called and the id exists in the database then it returns the correct data`() =
        runTest {
            // Given
            val loginLocalDataRepository =
                provideLoginLocalDataRepository(TypeMockSimulator.SUCCESS)
            val sut = provideGetUserIdUseCase(loginLocalDataRepository)
            // When
            val result = sut()
            // Then
            coEvery { loginLocalDataRepository.getId() }
            assertEquals(USER_ID, result)
        }

    @Test
    fun `when getId is called and the repository returns an error then it throws an exception`() =
        runTest {
            // Given
            val loginLocalDataRepository = provideLoginLocalDataRepository(TypeMockSimulator.ERROR)
            val sut = provideGetUserIdUseCase(loginLocalDataRepository)
            // When
            try {
                sut()
            } catch (e: Exception) {
                // Then
                coEvery { loginLocalDataRepository.getId() }
                assertEquals(MESSAGE_ERROR, e.message)
            }
        }

    @Test
    fun `when getId is called and the id does not exist in the database then it returns null`() =
        runTest {
            // Given
            val loginLocalDataRepository = provideLoginLocalDataRepository(TypeMockSimulator.NULL)
            val sut = provideGetUserIdUseCase(loginLocalDataRepository)
            // When
            val result = sut()
            // Then
            coEvery { loginLocalDataRepository.getId() }
            assertEquals(null, result)
        }

    @Test
    fun `when getId is called and the id is an empty string then it returns an empty string`() =
        runTest {
            // Given
            val loginLocalDataRepository = provideLoginLocalDataRepository(TypeMockSimulator.EMPTY)
            val sut = provideGetUserIdUseCase(loginLocalDataRepository)
            // When
            val result = sut()
            // Then
            coEvery { loginLocalDataRepository.getId() }
            assertEquals(EMPTY_STRING, result)
        }

    private fun provideGetUserIdUseCase(
        loginLocalDataRepository: LoginLocalDataRepository
    ) = GetUserIdUseCase(loginLocalDataRepository)

    private fun provideLoginLocalDataRepository(type: TypeMockSimulator) =
        mockk<LoginLocalDataRepository>().apply {
            when (type) {
                TypeMockSimulator.SUCCESS -> coEvery { getId() } returns USER_ID
                TypeMockSimulator.ERROR -> coEvery { getId() } throws Exception(MESSAGE_ERROR)
                TypeMockSimulator.NULL -> coEvery { getId() } returns null
                TypeMockSimulator.EMPTY -> coEvery { getId() } returns EMPTY_STRING
            }
        }

    private companion object {
        const val USER_ID = "123"
        const val MESSAGE_ERROR = "Database error"
    }
}
