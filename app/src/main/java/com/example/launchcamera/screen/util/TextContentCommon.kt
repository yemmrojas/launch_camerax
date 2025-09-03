package com.example.launchcamera.screen.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.launchcamera.ui.theme.PurpleGrey40

@Composable
fun TextContent(content: String, modifier: Modifier = Modifier) {
    Text(
        text = content,
        modifier = modifier,
        color = PurpleGrey40,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    )
}
