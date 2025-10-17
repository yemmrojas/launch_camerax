package com.example.launchcamera.domain.usecase

import com.example.launchcamera.MainCoroutineRule
import com.example.launchcamera.domain.repository.LoginLocalDataRepository
import com.example.launchcamera.domain.usescases.DeleteUserIdUseCase
import com.example.launchcamera.mock.TypeMockSimulator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteUserIdUseCaseTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when deleteId is called and the id is deleted correctly then it returns Unit`() =
        runTest {
            // Given
            val loginLocalDataRepository =
                provideLoginLocalDataRepository(TypeMockSimulator.SUCCESS)
            val sut = provideDeleteUserIdUseCase(loginLocalDataRepository)
            // When
            sut()
            // Then
            coEvery { loginLocalDataRepository.deleteId() }
        }

    @Test
    fun `when deleteId is called and the repository returns an error then it throws an exception`() =
        runTest {
            // Given
            val loginLocalDataRepository = provideLoginLocalDataRepository(TypeMockSimulator.ERROR)
            val sut = provideDeleteUserIdUseCase(loginLocalDataRepository)
            // When
            try {
                sut()
            } catch (e: Exception) {
                // Then
                coEvery { loginLocalDataRepository.deleteId() }
                assert(e.message == MESSAGE_ERROR)
            }
        }

    private fun provideDeleteUserIdUseCase(loginLocalDataRepository: LoginLocalDataRepository) =
        DeleteUserIdUseCase(loginLocalDataRepository)

    private fun provideLoginLocalDataRepository(type: TypeMockSimulator) =
        mockk<LoginLocalDataRepository>().apply {
            when (type) {
                TypeMockSimulator.SUCCESS -> {
                    coEvery { deleteId() } returns Unit
                }

                TypeMockSimulator.ERROR -> {
                    coEvery { deleteId() } throws Exception(MESSAGE_ERROR)
                }

                else -> Unit
            }
        }

    private companion object {
        const val MESSAGE_ERROR = "Error deleting user ID"
    }
}
