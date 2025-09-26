package com.example.launchcamera.screen.login.viewmodel

import com.example.launchcamera.MainCoroutineRule
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.usescases.GetUserByIdUseCase
import com.example.launchcamera.mock.TypeMockSimulator
import com.example.launchcamera.mock.data.getUserDataMockk
import com.example.launchcamera.screen.login.viewModel.LoginViewModel
import com.example.launchcamera.screen.login.viewModel.LoginViewModel.Companion.MESSAGE_EMPTY_ID
import com.example.launchcamera.screen.state.ScreenState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `onSaveSessionChanged should update value when save session is changed boolean then return true`() =runTest {
        // Given
        val isSaveSession = true
        val sut = providesSut(mockk())
        // When
        sut.onSaveSessionChanged(isSaveSession)
        // Then
        assertEquals(isSaveSession, sut.saveSession.value)
    }

    @Test
    fun `onSaveSessionChanged should update value when save session is changed boolean then return false`() = runTest {
        // Given
        val isSaveSession = false
        val sut = providesSut(mockk())
        // When
        sut.onSaveSessionChanged(isSaveSession)
        // Then
        assertEquals(isSaveSession, sut.saveSession.value)
    }

    @Test
    fun `onIdChanged should update id value and clear error when new id is not blank`() = runTest {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.SUCCESS)
        val sut = providesSut(getUserByIdUseCase)
        // When
        sut.onIdChanged(TEST_ID)
        sut.validateId()
        // Then
        assertEquals(TEST_ID, sut.id.value)
        assertEquals(null, sut.idError.value)
    }

    @Test
    fun `onIdChanged should update id value but not clear error when new id is blank`() = runTest {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.SUCCESS)
        val sut = providesSut(getUserByIdUseCase)
        // When
        sut.onIdChanged(EMPTY_ID)
        sut.validateId()
        // Then
        assertEquals(EMPTY_ID, sut.id.value)
        assertEquals(MESSAGE_EMPTY_ID, sut.idError.value)
    }

    @Test
    fun `getUserById should set state to Loading then Success when use case returns data`() =
        runTest {
            // Given
            val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.SUCCESS)
            val sut = providesSut(getUserByIdUseCase)
            // When
            sut.getUserById(TEST_ID)
            // Then
            coVerify { getUserByIdUseCase.invoke(TEST_ID) }
            assertEquals(ScreenState.Success, sut.stateLogin.value)
        }

    @Test
    fun `getUserById should set state to Loading then Error when use case fails`() {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.ERROR)
        val sut = providesSut(getUserByIdUseCase)

        // When
        sut.getUserById(TEST_ID)

        // Then
        coVerify { getUserByIdUseCase.invoke(TEST_ID) }
        assertEquals(ScreenState.Error, sut.stateLogin.value)
    }

    @Test
    fun `getUserById should set state to Loading then Error when use case returns null`() =
        runTest {
            // Given
            val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.NULL)
            val sut = providesSut(getUserByIdUseCase)

            // When
            sut.getUserById(TEST_ID)

            // Then
            coVerify { getUserByIdUseCase.invoke(TEST_ID) }
            assertEquals(ScreenState.Error, sut.stateLogin.value)
        }

    @Test
    fun `getUserById should set state to Loading then Success when use case returns empty data`() =
        runTest {
            // Given
            val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.EMPTY)
            val sut = providesSut(getUserByIdUseCase)
            // When
            sut.getUserById(TEST_ID)
            // Then
            coVerify { getUserByIdUseCase.invoke(TEST_ID) }
            assertEquals(ScreenState.Success, sut.stateLogin.value)
        }

    private fun providesGetUserById(type: TypeMockSimulator) =
        mockk<GetUserByIdUseCase>().also {
            when (type) {
                TypeMockSimulator.SUCCESS -> coEvery { it.invoke(any()) } returns Result.success(
                    getUserDataMockk()
                )

                TypeMockSimulator.ERROR -> coEvery { it.invoke(any()) } returns Result.failure(
                    Exception("Database error")
                )

                TypeMockSimulator.NULL -> coEvery { it.invoke(any()) } returns Result.success(null)
                TypeMockSimulator.EMPTY -> coEvery { it.invoke(any()) } returns Result.success(
                    UserData()
                )
            }
        }

    private fun providesSut(getUserByIdUseCase: GetUserByIdUseCase) =
        LoginViewModel(getUserByIdUseCase)

    companion object {
        private const val TEST_ID = "123"
        private const val EMPTY_ID = ""
    }
}