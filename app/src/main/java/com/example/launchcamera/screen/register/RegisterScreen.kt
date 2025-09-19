package com.example.launchcamera.screen.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.launchcamera.screen.components.ButtonPrimary
import com.example.launchcamera.screen.components.ProgressBarView
import com.example.launchcamera.screen.components.TextContent
import com.example.launchcamera.screen.components.TextFieldCommon
import com.example.launchcamera.screen.components.TextTitle
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel
import com.example.launchcamera.screen.shield.ShieldResultScreen
import com.example.launchcamera.screen.shield.state.IconType
import com.example.launchcamera.screen.state.ScreenState

internal const val USER_ID_ARGUMENT = "userId"
internal const val USER_NAME_ARGUMENT = "userName"
internal const val REGISTER_ROUTE = "register"
private const val TITLE_REGISTER = "Hi %s !"
private const val DESCRIPTION_REGISTER = "Enter the data to complete the registration."
private const val REGISTER_SUCCESS = "Register success"
private const val REGISTER_ERROR = "Register error"
private const val REGISTER_MESSAGE_SUCCESS =
    "Congratulations on your registration. To continue using the app, go to login and enter your ID number."

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onNavProfile: () -> Unit
) {
    val registryState = viewModel.registryState.collectAsState()
    when (registryState.value) {
        is ScreenState.Error -> {
            val messageError = viewModel.messageError.collectAsState()
            ShieldResultScreen(
                IconType.ERROR,
                REGISTER_ERROR,
                messageError.value,
            ) { onNavProfile() }
        }
        ScreenState.Idle -> RegisterScreenContent(viewModel)
        ScreenState.Loading -> ProgressBarView()
        ScreenState.Success -> ShieldResultScreen(
            IconType.SUCCESS,
            REGISTER_SUCCESS,
            REGISTER_MESSAGE_SUCCESS
        ) {
            onNavProfile()
        }
    }
}

@Composable
fun RegisterScreenContent(
    viewModel: RegisterViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.userName?.TitleRegister()
        DescriptionRegister()
        TextFieldEmailRegister(viewModel)
        TextConfirmEmailRegister(viewModel)
        TextFieldPhoneRegister(viewModel)
        ButtonRegister(viewModel)
    }
}

@Composable
private fun String.TitleRegister() {
    TextTitle(
        title = TITLE_REGISTER.format(this),
        modifier = Modifier.padding(
            top = 32.dp,
            bottom = 8.dp,
            start = 32.dp,
            end = 32.dp
        )
    )
}

@Composable
private fun DescriptionRegister() {
    TextContent(
        content = DESCRIPTION_REGISTER,
        modifier = Modifier.padding(
            bottom = 16.dp,
            start = 32.dp,
            end = 32.dp
        )
    )
}

@Composable
private fun TextFieldEmailRegister(viewModel: RegisterViewModel) {
    val email = viewModel.email.collectAsState()
    val isErrorEmail = viewModel.errorEmail.collectAsState()
    TextFieldCommon(
        value = email.value,
        onValueChange = { viewModel.onEmailChanged(it) },
        label = { Text(text = "Email") },
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 32.dp,
                end = 32.dp
            ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = isErrorEmail.value != null,
        errorMessage = isErrorEmail.value
    )
}

@Composable
private fun TextConfirmEmailRegister(viewModel: RegisterViewModel) {
    val confirmEmail = viewModel.confirmEmail.collectAsState()
    val isErrorConfirmEmail = viewModel.errorConfirmEmail.collectAsState()
    TextFieldCommon(
        value = confirmEmail.value,
        onValueChange = { viewModel.onConfirmEmailChanged(it) },
        label = { Text(text = "Confirm Email") },
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 32.dp,
                end = 32.dp
            ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = isErrorConfirmEmail.value != null,
        errorMessage = isErrorConfirmEmail.value
    )
}

@Composable
private fun TextFieldPhoneRegister(viewModel: RegisterViewModel) {
    val phone = viewModel.phone.collectAsState()
    val isPhoneError = viewModel.errorPhone.collectAsState()
    TextFieldCommon(
        value = phone.value,
        onValueChange = { viewModel.onPhoneChanged(it) },
        label = { Text(text = "Phone number") },
        modifier = Modifier
            .padding(
                bottom = 16.dp,
                start = 32.dp,
                end = 32.dp
            ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = isPhoneError.value != null,
        errorMessage = isPhoneError.value
    )
}

@Composable
private fun ButtonRegister(viewModel: RegisterViewModel) {
    ButtonPrimary(
        text = "Register",
        onClick = {
            if (viewModel.validateFields()) {
                viewModel.updateUser()
            }
        },
        modifier = Modifier
            .padding(
                start = 32.dp,
                end = 32.dp,
                bottom = 32.dp
            )
    )
}

