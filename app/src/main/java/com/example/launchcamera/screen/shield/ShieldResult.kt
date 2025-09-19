package com.example.launchcamera.screen.shield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.launchcamera.screen.components.ButtonSecondary
import com.example.launchcamera.screen.components.TextContent
import com.example.launchcamera.screen.components.TextTitle
import com.example.launchcamera.screen.shield.state.IconType
import com.example.launchcamera.screen.shield.state.iconShield
import com.example.launchcamera.ui.theme.Purple40
import com.example.launchcamera.ui.theme.Purple80

@Composable
fun ShieldResultScreen(
    iconType: IconType,
    title: String,
    description: String,
    actionButton: () -> Unit
) {
    val verticalArrangement: Arrangement.Vertical = Arrangement.Center
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40),
        verticalArrangement = verticalArrangement
    ) {
        ImageShield(iconType)
        TitleShield(title)
        DescriptionShield(description)
        ActionButtonShield(actionButton)
    }
}

@Composable
fun ImageShield(iconType: IconType) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = iconType.iconShield(),
            contentDescription = "image shield",
            tint = Purple80,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
        )
    }
}

@Composable
fun TitleShield(title: String) {
    TextTitle(
        title = title,
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
fun DescriptionShield(description: String) {
    TextContent(
        content = description,
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
fun ActionButtonShield(actionButton: () -> Unit) {
    ButtonSecondary(
        text = "Back Login",
        onClick = {
            actionButton()
        },
        modifier = Modifier
            .padding(
                start = 32.dp,
                end = 32.dp,
                bottom = 32.dp
            )
    )
}