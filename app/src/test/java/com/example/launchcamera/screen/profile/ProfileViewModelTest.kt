package com.example.launchcamera.screen.profile

import androidx.lifecycle.SavedStateHandle
import com.example.launchcamera.MainCoroutineRule
import com.example.launchcamera.domain.model.UserData
import com.example.launchcamera.domain.usescases.DeleteUserIdUseCase
import com.example.launchcamera.domain.usescases.GetUserByIdUseCase
import com.example.launchcamera.mock.TypeMockSimulator
import com.example.launchcamera.mock.data.getUserDataMockk
import com.example.launchcamera.screen.profile.viewModel.ProfileViewModel
import com.example.launchcamera.screen.state.ScreenState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `When getUserById is called and the id exists in the database then it returns the correct data`() =
        runTest {
            // Given
            val getUserByIdUseCase = providesGetUserByIdUseCase(TypeMockSimulator.SUCCESS)
            val saveStateHandle = providesSaveStateHandle()
            val deleteUserIdUseCase = providesDeleteUserIdUseCase()
            val sut = providesSut(getUserByIdUseCase, saveStateHandle, deleteUserIdUseCase)
            // When
            sut.getUserById(USER_ID_TEST)
            // Then
            coVerify(exactly = 1) { getUserByIdUseCase.invoke(USER_ID_TEST) }
            assertEquals(ScreenState.Success, sut.stateProfile.value)
            assertEquals(getUserDataMockk(), sut.userData.value)
        }

    @Test
    fun `When getUserById is called and the repository returns an error then it returns a failure result`() =
        runTest {
            // Given
            val getUserByIdUseCase = providesGetUserByIdUseCase(TypeMockSimulator.ERROR)
            val saveStateHandle = providesSaveStateHandle()
            val deleteUserIdUseCase = providesDeleteUserIdUseCase()
            val sut = providesSut(getUserByIdUseCase, saveStateHandle, deleteUserIdUseCase)
            // When
            sut.getUserById(USER_ID_TEST)
            // Then
            coVerify(exactly = 1) { getUserByIdUseCase.invoke(USER_ID_TEST) }
            assertEquals(ScreenState.Error, sut.stateProfile.value)
            assertEquals(EXPECTED_EXCEPTION_MESSAGE, sut.messageError.value)
        }

    @Test
    fun `When getUserById is called and the user id does not exist then it returns a success result with null data`() =
        runTest {
            // Given
            val getUserByIdUseCase = providesGetUserByIdUseCase(TypeMockSimulator.NULL)
            val saveStateHandle = providesSaveStateHandle()
            val deleteUserIdUseCase = providesDeleteUserIdUseCase()
            val sut = providesSut(getUserByIdUseCase, saveStateHandle, deleteUserIdUseCase)
            // When
            sut.getUserById(USER_ID_TEST)
            // Then
            coVerify(exactly = 1) { getUserByIdUseCase.invoke(USER_ID_TEST) }
            assertEquals(ScreenState.Success, sut.stateProfile.value)
            assertEquals(null, sut.userData.value)
        }

    @Test
    fun `When getUserById is called and the repository returns empty user data then it returns a success result with empty UserData`() =
        runTest {
            // Give
            val getUserByIdUseCase = providesGetUserByIdUseCase(TypeMockSimulator.EMPTY)
            val saveStateHandle = providesSaveStateHandle()
            val deleteUserIdUseCase = providesDeleteUserIdUseCase()
            val sut = providesSut(getUserByIdUseCase, saveStateHandle, deleteUserIdUseCase)
            // When
            sut.getUserById(USER_ID_TEST)
            // Then
            coVerify(exactly = 1) { getUserByIdUseCase.invoke(USER_ID_TEST) }
            assertEquals(ScreenState.Success, sut.stateProfile.value)
            assertEquals(UserData(), sut.userData.value)
        }

    @Test
    fun `When deleteUserId is called then the use case is invoked`() = runTest {
        // Given
        val getUserByIdUseCase = providesGetUserByIdUseCase(TypeMockSimulator.SUCCESS)
        val saveStateHandle = providesSaveStateHandle()
        val deleteUserIdUseCase = providesDeleteUserIdUseCase()
        val sut = providesSut(getUserByIdUseCase, saveStateHandle, deleteUserIdUseCase)
        // When
        sut.deleteUserId()
        // Then
        coVerify(exactly = 1) { deleteUserIdUseCase.invoke() }
    }

    private fun providesSut(
        getUserByIdUseCase: GetUserByIdUseCase,
        handle: SavedStateHandle,
        deleteUserIdUseCase: DeleteUserIdUseCase
    ) = ProfileViewModel(getUserByIdUseCase, handle, deleteUserIdUseCase)

    private fun providesGetUserByIdUseCase(type: TypeMockSimulator) =
        mockk<GetUserByIdUseCase>().also {
            when (type) {
                TypeMockSimulator.SUCCESS -> coEvery {
                    it.invoke(any())
                } returns Result.success(
                    getUserDataMockk()
                )

                TypeMockSimulator.ERROR -> coEvery { it.invoke(any()) } returns Result.failure(
                    Exception(EXPECTED_EXCEPTION_MESSAGE)
                )

                TypeMockSimulator.NULL -> coEvery { it.invoke(any()) } returns Result.success(null)
                TypeMockSimulator.EMPTY -> coEvery { it.invoke(any()) } returns Result.success(
                    UserData()
                )
            }
        }

    private fun providesSaveStateHandle() = mockk<SavedStateHandle>().also {
        every { it.get<String>(any()) } returns USER_ID_TEST
    }

    private fun providesDeleteUserIdUseCase() =
        mockk<DeleteUserIdUseCase>().also {
            coEvery { it.invoke() } returns Unit
        }

    companion object {
        private const val USER_ID_TEST = "1234567"
        private const val EXPECTED_EXCEPTION_MESSAGE = "Database error"
    }
}