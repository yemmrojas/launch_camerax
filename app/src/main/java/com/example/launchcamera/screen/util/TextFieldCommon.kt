package com.example.launchcamera.screen.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextFieldCommon(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        isError = isError,
        supportingText = {
            if (isError && errorMessage != null) {
                Text(text = errorMessage)
            }
        }
    )
}