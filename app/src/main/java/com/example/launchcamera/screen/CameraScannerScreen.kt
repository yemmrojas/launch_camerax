package com.example.launchcamera.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.launchcamera.screen.util.ButtonSecondary
import com.example.launchcamera.screen.util.TextContent
import com.example.launchcamera.screen.util.TextTitle
import com.example.launchcamera.ui.theme.Purple40
import com.example.launchcamera.ui.theme.Purple80

private const val TITLE_CAMERA_SCANNER = "Scan of identity document"
private const val DESCRIPTION_CAMERA_SCANNER = "Prepare your ID document to scan it on both sides."
private const val BUTTON_CAMERA_SCANNER = "Start Scanning"
private const val DESCRIPTION_IMAGE_CAMERA_SCANNER = "Image of camera"

@Composable
fun CameraScannerScreen() {
    val verticalArrangement: Arrangement.Vertical = Arrangement.Center
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40),
        verticalArrangement = verticalArrangement
    ) {
        ImageCamera()
        TitleCameraScanner()
        DescriptionCameraScanner()
        ButtonCameraScanner()
    }
}

@Composable
private fun ImageCamera() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = DESCRIPTION_IMAGE_CAMERA_SCANNER,
            tint = Purple80,
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
        )
    }
}

@Composable
private fun TitleCameraScanner() {
    TextTitle(
        title = TITLE_CAMERA_SCANNER,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(
                start = 32.dp,
                end = 32.dp
            )
            .fillMaxWidth()
    )
}

@Composable
private fun DescriptionCameraScanner() {
    TextContent(
        content = DESCRIPTION_CAMERA_SCANNER,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(
                top = 16.dp,
                start = 32.dp,
                end = 32.dp,
                bottom = 32.dp
            )
            .fillMaxWidth()
    )
}

@Composable
private fun ButtonCameraScanner() {
    ButtonSecondary(
        text = BUTTON_CAMERA_SCANNER,
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
    CameraScannerScreen()
}