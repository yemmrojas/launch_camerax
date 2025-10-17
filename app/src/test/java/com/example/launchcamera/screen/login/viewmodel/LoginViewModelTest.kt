package com.example.launchcamera.screen.login.viewmodel

import com.example.launchcamera.MainCoroutineRule
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.usescases.GetUserByIdUseCase
import com.example.launchcamera.domain.usescases.GetUserIdUseCase
import com.example.launchcamera.domain.usescases.SaveUserIdUsesCase
import com.example.launchcamera.mock.TypeMockSimulator
import com.example.launchcamera.mock.data.getUserDataMockk
import com.example.launchcamera.screen.login.viewModel.LoginViewModel
import com.example.launchcamera.screen.login.viewModel.LoginViewModel.Companion.MESSAGE_EMPTY_ID
import com.example.launchcamera.screen.state.ScreenState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
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
    fun `onSaveSessionChanged should update value when save session is changed boolean then return true`() =
        runTest {
            // Given
            val isSaveSession = true
            val sut = providesSut(
                mockk(),
                mockk(),
                mockk()
            )
            // When
            sut.onSaveSessionChanged(isSaveSession)
            // Then
            assertEquals(isSaveSession, sut.saveSession.value)
        }

    @Test
    fun `onSaveSessionChanged should update value when save session is changed boolean then return false`() =
        runTest {
            // Given
            val isSaveSession = false
            val sut = providesSut(
                mockk(),
                mockk(),
                mockk())
            // When
            sut.onSaveSessionChanged(isSaveSession)
            // Then
            assertEquals(isSaveSession, sut.saveSession.value)
        }

    @Test
    fun `onIdChanged should update id value and clear error when new id is not blank`() = runTest {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.SUCCESS)
        val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.SUCCESS)
        val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.SUCCESS)
        val sut = providesSut(getUserByIdUseCase, saveUserIdUsesCase, getUserIdUseCase)
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
        val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.SUCCESS)
        val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.SUCCESS)
        val sut = providesSut(
            getUserByIdUseCase,
            saveUserIdUsesCase,
            getUserIdUseCase
        )
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
            val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.SUCCESS)
            val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(
                getUserByIdUseCase,
                saveUserIdUsesCase,
                getUserIdUseCase
            )
            // When
            sut.getUserById(TEST_ID)
            // Then
            coVerify { getUserByIdUseCase.invoke(TEST_ID) }
            coVerify { saveUserIdUsesCase.invoke(TEST_ID) }
            assertEquals(ScreenState.Success, sut.stateLogin.value)
        }

    @Test
    fun `getUserById should set state to Loading then Error when use case fails`() {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.ERROR)
        val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.ERROR)
        val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.ERROR)

        val sut = providesSut(
            getUserByIdUseCase,
            saveUserIdUsesCase,
            getUserIdUseCase
        )

        // When
        sut.getUserById(TEST_ID)

        // Then
        coVerify { getUserByIdUseCase.invoke(TEST_ID) }
        coVerify(exactly = 0) { saveUserIdUsesCase.invoke(TEST_ID) }
        coVerify(exactly = 0) { getUserIdUseCase.invoke() }
        assertEquals(ScreenState.Error, sut.stateLogin.value)
    }

    @Test
    fun `getUserById should set state to Loading then Error when use case returns null`() =
        runTest {
            // Given
            val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.NULL)
            val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.NULL)
            val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.NULL)
            val sut = providesSut(
                getUserByIdUseCase,
                saveUserIdUsesCase,
                getUserIdUseCase
            )

            // When
            sut.getUserById(TEST_ID)

            // Then
            coVerify { getUserByIdUseCase.invoke(TEST_ID) }
            coVerify(exactly = 0) { saveUserIdUsesCase.invoke(TEST_ID) }
            coVerify(exactly = 0) { getUserIdUseCase.invoke() }
            assertEquals(ScreenState.Error, sut.stateLogin.value)
        }

    @Test
    fun `getUserById should set state to Loading then Success when use case returns empty data`() =
        runTest {
            // Given
            val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.EMPTY)
            val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.EMPTY)
            val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.EMPTY)
            val sut = providesSut(
                getUserByIdUseCase,
                saveUserIdUsesCase,
                getUserIdUseCase
            )
            // When
            sut.getUserById(TEST_ID)
            // Then
            coVerify { getUserByIdUseCase.invoke(TEST_ID) }
            coVerify { saveUserIdUsesCase.invoke(TEST_ID) }
            assertEquals(ScreenState.Success, sut.stateLogin.value)
        }
    @Test
    fun `when getUserIdSession is not null then return value`() = runTest {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.SUCCESS)
        val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.SUCCESS)
        val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.SUCCESS)
        val sut = providesSut(
            getUserByIdUseCase,
            saveUserIdUsesCase,
            getUserIdUseCase
        )

        // When
        sut.getUserIdSession()
        // Then
        assertEquals(TEST_ID, sut.getUserIdSession())
    }

    @Test
    fun `when getUserIdSession is null then return null`() = runTest {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.NULL)
        val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.NULL)
        val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.NULL)
        val sut = providesSut(
            getUserByIdUseCase,
            saveUserIdUsesCase,
            getUserIdUseCase
        )
        // When
        sut.getUserIdSession()
        // Then
        assertEquals(null, sut.getUserIdSession())
    }

    @Test
    fun `when getUserIdSession is empty then return empty`() = runTest {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.EMPTY)
        val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.EMPTY)
        val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.EMPTY)
        val sut = providesSut(
            getUserByIdUseCase,
            saveUserIdUsesCase,
            getUserIdUseCase
        )
        // When
        sut.getUserIdSession()
        // Then
        assertEquals(EMPTY_ID, sut.getUserIdSession())
    }

    @Test(expected = Exception::class)
    fun `when getUserIdSession is error then return trow`() = runTest {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.EMPTY)
        val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.ERROR)
        val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.EMPTY)
        val sut = providesSut(
            getUserByIdUseCase,
            saveUserIdUsesCase,
            getUserIdUseCase
        )
        // When
        sut.getUserIdSession()
    }

    @Test
    fun `resetState should set stateLogin to Idle`() = runTest {
        // Given
        val getUserByIdUseCase = providesGetUserById(TypeMockSimulator.SUCCESS)
        val getUserIdUseCase = providesGetUserIdUseCase(TypeMockSimulator.SUCCESS)
        val saveUserIdUsesCase = providesSaveUserIdUseCase(TypeMockSimulator.SUCCESS)
        val sut = providesSut(
            getUserByIdUseCase,
            saveUserIdUsesCase,
            getUserIdUseCase
        )
        // When
        sut.getUserById(TEST_ID) // Set state to Success
        sut.resetState() // Reset state to Idle
        // Then
        assertEquals(ScreenState.Idle, sut.stateLogin.value)
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

    private fun providesSaveUserIdUseCase(type: TypeMockSimulator) =
        mockk<SaveUserIdUsesCase>().also {
            when (type) {
                TypeMockSimulator.SUCCESS -> coEvery { it.invoke(any()) } just runs
                TypeMockSimulator.ERROR -> coEvery { it.invoke(any()) } throws Exception("Database error")
                TypeMockSimulator.NULL -> coEvery { it.invoke(any()) } just runs
                TypeMockSimulator.EMPTY -> coEvery { it.invoke(any()) } just runs
            }
        }

    private fun providesGetUserIdUseCase(type: TypeMockSimulator) =
        mockk<GetUserIdUseCase>().also {
            when (type) {
                TypeMockSimulator.SUCCESS -> coEvery { it.invoke() } returns TEST_ID
                TypeMockSimulator.ERROR -> coEvery { it.invoke() } throws Exception("Database error")
                TypeMockSimulator.NULL -> coEvery { it.invoke() } returns null
                TypeMockSimulator.EMPTY -> coEvery { it.invoke() } returns ""
            }
        }

    private fun providesSut(
        getUserByIdUseCase: GetUserByIdUseCase,
        saveUserIdUsesCase: SaveUserIdUsesCase,
        getUserIdUseCase: GetUserIdUseCase
    ) =
        LoginViewModel(
            getUserByIdUseCase,
            saveUserIdUsesCase,
            getUserIdUseCase
        )

    companion object {
        private const val TEST_ID = "123"
        private const val EMPTY_ID = ""
    }
}