@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.launchcamera.screen.register.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.launchcamera.MainCoroutineRule
import com.example.launchcamera.domain.usescases.UpdateUserUseCase
import com.example.launchcamera.mock.TypeMockSimulator
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel.Companion.EMPTY_STRING
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel.Companion.MESSAGE_CONFIRM_EMAIL_EMPTY
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel.Companion.MESSAGE_ERROR_CONFIRM_EMAIL
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel.Companion.MESSAGE_ERROR_EMAIL
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel.Companion.MESSAGE_ERROR_EMAIL_EMPTY
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel.Companion.MESSAGE_ERROR_PHONE
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel.Companion.MESSAGE_ERROR_PHONE_EMPTY
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when onEmailChanged is called with a new email, email state should be updated`() =
        runTest {
            // Given

            val saveStateHandle = providesSaveStateHandle()
            val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userUseCase, saveStateHandle)
            // When
            sut.onEmailChanged(EMAIL_VALUE_TEST)
            val result = sut.validateFields()
            // Then
            assertEquals(EMAIL_VALUE_TEST, sut.email.value)
            assertEquals(null, sut.errorEmail.value)
        }

    @Test
    fun `when onEmailChanged is called with a new email, email state is nor valid should be updated`() =
        runTest {
            // Given
            val email = "email"
            val saveStateHandle = providesSaveStateHandle()
            val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userUseCase, saveStateHandle)
            // When
            sut.onEmailChanged(email)
            val result = sut.validateFields()
            // Then
            assertEquals(email, sut.email.value)
            assertEquals(MESSAGE_ERROR_EMAIL, sut.errorEmail.value)
            assertFalse(result)
        }

    @Test
    fun `When onConfirmEmailChanged is called with a new email, the email status is empty and needs to be updated`() =
        runTest {
            // Given
            val saveStateHandle = providesSaveStateHandle()
            val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userUseCase, saveStateHandle)
            // When
            sut.onEmailChanged(EMPTY_STRING)
            val result = sut.validateFields()
            // Then
            assertEquals(EMPTY_STRING, sut.email.value)
            assertEquals(MESSAGE_ERROR_EMAIL_EMPTY, sut.errorEmail.value)
            assertFalse(result)
        }

    @Test
    fun `when onConfirmEmailChanged is called with a new email, confirmEmail state should be updated`() =
        runTest {
            val saveStateHandle = providesSaveStateHandle()
            val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userUseCase, saveStateHandle)
            // When
            sut.onEmailChanged(EMAIL_VALUE_TEST)
            sut.onConfirmEmailChanged(EMAIL_VALUE_TEST)
            val result = sut.validateFields()
            // Then
            assertEquals(EMAIL_VALUE_TEST, sut.confirmEmail.value)
            assertEquals(EMAIL_VALUE_TEST, sut.email.value)
            assertEquals(null, sut.errorConfirmEmail.value)
        }

    @Test
    fun `When onConfirmEmailChanged is called but the value is different from the email then it returns an error message`() =
        runTest {
            val emailExpected = "email"
            val saveStateHandle = providesSaveStateHandle()
            val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userUseCase, saveStateHandle)
            // When
            sut.onEmailChanged(EMAIL_VALUE_TEST)
            sut.onConfirmEmailChanged(emailExpected)
            val result = sut.validateFields()
            // Then
            assertEquals(emailExpected, sut.confirmEmail.value)
            assertEquals(MESSAGE_ERROR_CONFIRM_EMAIL, sut.errorConfirmEmail.value)
            assertFalse(result)
        }

    @Test
    fun `When onConfirmEmailChanged is called but the value is empty, then it returns an error message`() =
        runTest {
            val saveStateHandle = providesSaveStateHandle()
            val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userUseCase, saveStateHandle)
            // When
            sut.onConfirmEmailChanged(EMPTY_STRING)
            val result = sut.validateFields()
            // Then
            assertEquals(EMPTY_STRING, sut.confirmEmail.value)
            assertEquals(MESSAGE_CONFIRM_EMAIL_EMPTY, sut.errorConfirmEmail.value)
            assertFalse(result)
        }

    @Test
    fun `when onPhoneChanged is called with a new phone, phone state should be updated`() =
        runTest {
            // Given
            val saveStateHandle = providesSaveStateHandle()
            val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userUseCase, saveStateHandle)
            // When
            sut.onPhoneChanged(PHONE_NUMBER_TEST)
            // Then
            assertEquals(PHONE_NUMBER_TEST, sut.phone.value)
            assertEquals(null, sut.errorPhone.value)
        }

    @Test
    fun `when onPhoneChanged is called with a new phone, phone state is nor valid should be updated`() =
        runTest {
            // Given
            val saveStateHandle = providesSaveStateHandle()
            val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userUseCase, saveStateHandle)
            // When
            sut.onPhoneChanged(EMPTY_STRING)
            val result = sut.validateFields()
            // Then
            assertEquals(EMPTY_STRING, sut.phone.value)
            assertEquals(MESSAGE_ERROR_PHONE_EMPTY, sut.errorPhone.value)
            assertFalse(result)
        }

    @Test
    fun `when onPhoneChanged is called with a new phone, phone state is not valid should be updated`() =
        runTest {
            // Given
            val phone = "123456789"
            val saveStateHandle = providesSaveStateHandle()
            val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
            val sut = providesSut(userUseCase, saveStateHandle)
            // When
            sut.onPhoneChanged(phone)
            val result = sut.validateFields()
            // Then
            assertEquals(phone, sut.phone.value)
            assertEquals(MESSAGE_ERROR_PHONE, sut.errorPhone.value)
            assertFalse(result)
        }

    @Test
    fun `When all validated fields are correct then it returns true`() = runTest {
        // Given
        val saveStateHandle = providesSaveStateHandle()
        val userUseCase = providesUserUseCase(TypeMockSimulator.SUCCESS)
        val sut = providesSut(userUseCase, saveStateHandle)
        // Given
        sut.onEmailChanged(EMAIL_VALUE_TEST)
        sut.onConfirmEmailChanged(EMAIL_VALUE_TEST)
        sut.onPhoneChanged(PHONE_NUMBER_TEST)
        val result = sut.validateFields()
        // Then
        assertTrue(result)
    }

    private fun providesSut(
        userUseCase: UpdateUserUseCase,
        handle: SavedStateHandle
    ) = RegisterViewModel(
        savedStateHandle = handle,
        updateUserUseCase = userUseCase
    )

    private fun providesSaveStateHandle() = mockk<SavedStateHandle>().also {
        every { it.get<String>(any()) } returns USER_ID_TEST
    }

    private fun providesUserUseCase(type: TypeMockSimulator) = mockk<UpdateUserUseCase>().also {
        when (type) {
            TypeMockSimulator.SUCCESS -> coEvery {
                it.invoke(any(), any(), any())
            } returns Result.success(true)

            TypeMockSimulator.ERROR -> coEvery {
                it.invoke(any(), any(), any())
            } returns Result.failure(Exception(EXPECTED_EXCEPTION_MESSAGE))

            TypeMockSimulator.NULL -> coEvery {
                it.invoke(any(), any(), any())
            } returns Result.success(false)

            TypeMockSimulator.EMPTY -> coEvery {
                it.invoke(any(), any(), any())
            } returns Result.success(true)
        }
    }

    companion object {
        private const val PHONE_NUMBER_TEST = "3110000000"
        private const val EMAIL_VALUE_TEST = "test@user.com"
        private const val USER_ID_TEST = "1234567"
        private const val EXPECTED_EXCEPTION_MESSAGE = "Database error"
    }
}