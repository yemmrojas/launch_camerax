package com.example.launchcamera.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.launchcamera.screen.components.ButtonPrimary
import com.example.launchcamera.screen.components.TextContent
import com.example.launchcamera.screen.components.TextTitle
import com.example.launchcamera.ui.theme.Purple40
import com.example.launchcamera.ui.theme.Purple80

internal const val PROFILE_ROUTE = "profile"

@Composable
fun ProfileScreen(
    onLogoutClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeaderSection()
        ContentSection()
        ButtonLonOut(onLogoutClicked)
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .background(Purple40)
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Icon(
                imageVector = Default.AccountCircle,
                contentDescription = "Profile Icon",
                tint = Purple80,
                modifier = Modifier
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        top = 32.dp,
                        bottom = 16.dp
                    )
                    .height(60.dp)
                    .width(60.dp)
                    .background(Purple40)
            )
        }

        TextTitle(
            title = "Hi, David Martinez",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    bottom = 32.dp,
                    start = 32.dp,
                    end = 32.dp
                ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ContentSection() {
    Row(
        modifier = Modifier
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp
            )
            .fillMaxWidth()
    ) {
        TextContent(
            content = "Id:",
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
        TextContent(
            content = "1234567899:",
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
    DiverLine()
    Row(
        modifier = Modifier
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp
            )
            .fillMaxWidth()
    ) {
        TextContent(
            content = "Email:",
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
        TextContent(
            content = "user@email.com",
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
    DiverLine()
    Row(
        modifier = Modifier
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp
            )
            .fillMaxWidth()
    ) {
        TextContent(
            content = "Contact:",
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
        TextContent(
            content = "+1 234 567 890",
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
    DiverLine()
    Row(
        modifier = Modifier
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp
            )
            .fillMaxWidth()
    ) {
        TextContent(
            content = "BirdDay:",
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
        TextContent(
            content = "10/10/1990",
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
    DiverLine()
    Row(
        modifier = Modifier
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp
            )
            .fillMaxWidth()
    ) {
        TextContent(
            content = "Age:",
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )
        TextContent(
            content = "25",
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
    DiverLine()
}

@Composable
private fun DiverLine() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 32.dp,
                end = 32.dp
            ),
        color = Color.LightGray,
        thickness = 1.dp
    )
}

@Composable
private fun ButtonLonOut(onLogoutClicked: () -> Unit) {
    ButtonPrimary(
        text = "Log out",
        onClick = { onLogoutClicked() },
        modifier = Modifier
            .padding(
                top = 32.dp,
                bottom = 32.dp,
                start = 32.dp,
                end = 32.dp
            )
            .fillMaxWidth()
    )
}