package com.example.launchcamera.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.launchcamera.screen.components.ButtonPrimary
import com.example.launchcamera.screen.components.ButtonSecondary
import com.example.launchcamera.screen.components.TextContent
import com.example.launchcamera.screen.components.TextFieldCommon
import com.example.launchcamera.screen.components.TextTitle
import com.example.launchcamera.screen.login.viewModel.LoginViewModel
import com.example.launchcamera.ui.theme.Purple40

internal const val LOGIN_ROUTE = "login"
private const val LOGIN_TITLE = "Sign In to account"
private const val LOGIN_DESCRIPTION =
    "Enter your ID and select whether you want us to save your session"

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginClicked: (String) -> Unit,
    onRegisterClicked: () -> Unit
) {
    val verticalArrangement: Arrangement.Vertical = Arrangement.Center
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = verticalArrangement
    ) {
        TitleScreen(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    bottom = 16.dp,
                    start = 32.dp,
                    end = 32.dp
                )
        )
        DescriptionScreen(
            modifier = Modifier
                .padding(
                    bottom = 16.dp,
                    start = 32.dp,
                    end = 32.dp
                )
        )
        TextInputUsername(viewModel)
        SwitchSaveSession(viewModel)
        ButtonLogin(viewModel, onLoginClicked)
        ButtonLoginRegister(onRegisterClicked)
    }
}

@Composable
private fun TitleScreen(modifier: Modifier = Modifier) {
    TextTitle(
        title = LOGIN_TITLE,
        modifier = modifier
    )
}

@Composable
private fun DescriptionScreen(modifier: Modifier = Modifier) {
    TextContent(
        content = LOGIN_DESCRIPTION,
        modifier = modifier
    )
}

@Composable
private fun TextInputUsername(viewModel: LoginViewModel) {
    val id = viewModel.id.collectAsState()
    val idError = viewModel.idError.collectAsState()
    TextFieldCommon(
        value = id.value,
        onValueChange = { viewModel.onIdChanged(it) },
        label = { Text(text = "Enter your Id") },
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 32.dp,
                end = 32.dp
            ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = idError.value != null,
        errorMessage = idError.value
    )
}

@Composable
private fun SwitchSaveSession(viewModel: LoginViewModel) {
    Row(
        modifier = Modifier
            .padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 32.dp,
                end = 32.dp
            )
            .fillMaxWidth()
    ) {
        val checked = viewModel.saveSession.collectAsState().value
        Switch(
            checked = checked,
            onCheckedChange = {
                viewModel.onSaveSessionChanged(it)
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = Purple40
            ),
            thumbContent = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Checked",
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )

        TextContent(
            content = "Save session?",
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 8.dp,
                    end = 8.dp
                )
                .padding(top = 12.dp)
        )
    }
}

@Composable
private fun ButtonLogin(viewModel: LoginViewModel, onLoginClicked: (String) -> Unit) {
    ButtonPrimary(
        text = "Login",
        onClick = {
            if (viewModel.validateId()) {
                val userId = viewModel.id.value
                onLoginClicked(userId)
            }
        },
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 32.dp,
                end = 32.dp
            )
    )
}

@Composable
private fun ButtonLoginRegister(onRegisterClicked: () -> Unit) {
    ButtonSecondary(
        text = "Register",
        onClick = { onRegisterClicked() },
        modifier = Modifier
            .padding(
                bottom = 32.dp,
                start = 32.dp,
                end = 32.dp
            )
    )
}
