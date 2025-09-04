package com.example.launchcamera.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.example.launchcamera.screen.util.ButtonPrimary
import com.example.launchcamera.screen.util.ButtonSecondary
import com.example.launchcamera.screen.util.TextContent
import com.example.launchcamera.screen.util.TextFieldCommon
import com.example.launchcamera.screen.util.TextTitle
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreenKey: NavKey
private const val LOGIN_TITLE = "Sign In to account"
private const val LOGIN_DESCRIPTION = "Sign in with your email and password for continue"

@Composable
fun LoginScreen(
    onLoginClicked: () -> Unit,
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
        TextInputUsername()
        TextInputPassword()
        ButtonLogin(onLoginClicked)
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
private fun TextInputUsername() {
    TextFieldCommon(
        value = "",
        onValueChange = {},
        label = { Text(text = "User name") },
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 32.dp,
                end = 32.dp
            )
    )
}

@Composable
private fun TextInputPassword() {
    TextFieldCommon(
        value = "",
        onValueChange = {},
        label = { Text(text = "Password") },
        modifier = Modifier
            .padding(
                bottom = 16.dp,
                start = 32.dp,
                end = 32.dp
            )
    )
}

@Composable
private fun ButtonLogin(onLoginClicked: () -> Unit) {
    ButtonPrimary(
        text = "Login",
        onClick = { onLoginClicked() },
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
