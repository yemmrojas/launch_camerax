package com.example.launchcamera.screen

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
import androidx.navigation3.runtime.NavKey
import com.example.launchcamera.screen.util.ButtonPrimary
import com.example.launchcamera.screen.util.TextContent
import com.example.launchcamera.screen.util.TextFieldCommon
import com.example.launchcamera.screen.util.TextTitle
import com.example.launchcamera.screen.viewModel.RegisterViewModel
import kotlinx.serialization.Serializable

@Serializable
data object RegisterScreenKey : NavKey

private const val TITLE_REGISTER = "Hi %s !"
private const val DESCRIPTION_REGISTER = "Enter the data to complete the registration."

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, onNavProfile: () -> Unit) {
    val name = "David Martinez"
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        name.TitleRegister()
        DescriptionRegister()
        TextFieldEmailRegister(viewModel)
        TextConfirmEmailRegister(viewModel)
        TextFieldPhoneRegister(viewModel)
        ButtonRegister(viewModel, onNavProfile)
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
private fun ButtonRegister(viewModel: RegisterViewModel, onNavProfile: () -> Unit) {
    ButtonPrimary(
        text = "Register",
        onClick = {
            if (viewModel.validateFields()) {
                onNavProfile()
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
