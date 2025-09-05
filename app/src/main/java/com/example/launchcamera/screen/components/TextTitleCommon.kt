package com.example.launchcamera.screen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.launchcamera.ui.theme.Purple40

@Composable
fun TextTitle(
    title: String,
    color: Color = Purple40,
    textAlign: TextAlign = TextAlign.Left,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier,
        color = color,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign
    )
}