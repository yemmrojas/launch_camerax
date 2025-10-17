package com.example.launchcamera.domain.usecase

import com.example.launchcamera.MainCoroutineRule
import com.example.launchcamera.domain.repository.LoginLocalDataRepository
import com.example.launchcamera.domain.usescases.SaveUserIdUsesCase
import com.example.launchcamera.mock.TypeMockSimulator
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel.Companion.EMPTY_STRING
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveUserIdUsesCaseTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when saveId is called and the id is saved correctly then it returns Unit`() =
        runTest {
            // Given
            val loginLocalDataRepository =
                provideLoginLocalDataRepository(TypeMockSimulator.SUCCESS)
            val sut = provideSaveUserIdUseCase(loginLocalDataRepository)
            // When
            sut(USER_ID)
            // Then
            coEvery { loginLocalDataRepository.saveId(USER_ID) }
        }

    @Test
    fun `when saveId is called and the repository returns an error then it throws an exception`() =
        runTest {
            // Given
            val loginLocalDataRepository = provideLoginLocalDataRepository(TypeMockSimulator.ERROR)
            val sut = provideSaveUserIdUseCase(loginLocalDataRepository)
            // When
            try {
                sut(USER_ID)
            } catch (e: Exception) {
                // Then
                coEvery { loginLocalDataRepository.saveId(eq(USER_ID)) }
                assert(e.message == MESSAGE_ERROR)
            }
        }

    @Test
    fun `when saveId is called with an empty id then it saves it correctly`() =
        runTest {
            // Given
            val loginLocalDataRepository = provideLoginLocalDataRepository(TypeMockSimulator.EMPTY)
            val sut = provideSaveUserIdUseCase(loginLocalDataRepository)
            // When
            sut(EMPTY_STRING)
            // Then
            coEvery { loginLocalDataRepository.saveId(eq(EMPTY_STRING)) }
        }

    private fun provideSaveUserIdUseCase(loginLocalDataRepository: LoginLocalDataRepository) =
        SaveUserIdUsesCase(loginLocalDataRepository)

    private fun provideLoginLocalDataRepository(type: TypeMockSimulator) =
        mockk<LoginLocalDataRepository>().apply {
            when (type) {
                TypeMockSimulator.SUCCESS -> coEvery { saveId(USER_ID) } returns Unit
                TypeMockSimulator.ERROR -> coEvery { saveId(USER_ID) } throws Exception(
                    MESSAGE_ERROR
                )
                else -> coEvery { saveId(EMPTY_STRING) } returns Unit
            }
        }

    companion object {
        const val MESSAGE_ERROR = "Error"
        const val USER_ID = "12345"
    }
}
