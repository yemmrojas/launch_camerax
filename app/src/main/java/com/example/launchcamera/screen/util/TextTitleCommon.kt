package com.example.launchcamera.screen.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.launchcamera.ui.theme.Purple40

@Composable
fun TextTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier,
        color = Purple40,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    )
}