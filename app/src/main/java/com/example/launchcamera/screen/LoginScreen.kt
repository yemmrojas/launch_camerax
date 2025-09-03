package com.example.launchcamera.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.launchcamera.screen.util.ButtonPrimary
import com.example.launchcamera.screen.util.ButtonSecondary
import com.example.launchcamera.screen.util.TextContent
import com.example.launchcamera.screen.util.TextFieldCommon
import com.example.launchcamera.screen.util.TextTitle

private const val LOGIN_TITLE = "Sign In to account"
private const val LOGIN_DESCRIPTION = "Sign in with your email and password for continue"

@Composable
fun LoginScreen() {
    val verticalArrangement: Arrangement.Vertical = Arrangement.Center
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = verticalArrangement
    ) {
        TitleScreen(
            title = LOGIN_TITLE,
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    bottom = 16.dp,
                    start = 32.dp,
                    end = 32.dp
                )
        )
        DescriptionScreen(
            description = LOGIN_DESCRIPTION, modifier = Modifier
                .padding(
                    bottom = 16.dp,
                    start = 32.dp,
                    end = 32.dp
                )
        )
        TextInputUsername()
        TextInputPassword()
        ButtonLogin()
        ButtonRegister()
    }
}

@Composable
fun TitleScreen(title: String, modifier: Modifier = Modifier) {
    TextTitle(
        title = title,
        modifier = modifier
    )
}

@Composable
fun DescriptionScreen(description: String, modifier: Modifier = Modifier) {
    TextContent(
        content = description,
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
fun TextInputPassword() {
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
fun ButtonLogin() {
    ButtonPrimary(
        text = "Login",
        onClick = { },
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 32.dp,
                end = 32.dp
            )
    )
}

@Composable
fun ButtonRegister() {
    ButtonSecondary(
        text = "Register",
        onClick = { },
        modifier = Modifier
            .padding(
                bottom = 32.dp,
                start = 32.dp,
                end = 32.dp
            )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginScreen()
}
