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
import com.example.launchcamera.screen.util.TextContent
import com.example.launchcamera.screen.util.TextFieldCommon
import com.example.launchcamera.screen.util.TextTitle

private const val TITLE_REGISTER = "Hi %s !"
private const val DESCRIPTION_REGISTER = "Enter the data to complete the registration."

@Composable
fun RegisterScreen() {
    val name = "David Martinez"
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        name.TitleRegister()
        DescriptionRegister()
        TextFieldEmailRegister()
        TextConfirmEmailRegister()
        TextFieldPasswordRegister()
        ButtonRegister()
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
private fun TextFieldEmailRegister() {
    TextFieldCommon(
        value = "",
        onValueChange = {},
        label = { Text(text = "Email") },
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 32.dp,
                end = 32.dp
            )
    )
}

@Composable
private fun TextConfirmEmailRegister() {
    TextFieldCommon(
        value = "",
        onValueChange = {},
        label = { Text(text = "Confirm Email") },
        modifier = Modifier
            .padding(
                bottom = 8.dp,
                start = 32.dp,
                end = 32.dp
            )
    )
}

@Composable
private fun TextFieldPasswordRegister() {
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
private fun ButtonRegister() {
    ButtonPrimary(
        text = "Register",
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(
                start = 32.dp,
                end = 32.dp,
                bottom = 32.dp
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    RegisterScreen()
}
