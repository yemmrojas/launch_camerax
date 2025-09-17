package com.example.launchcamera.screen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.launchcamera.ui.theme.PurpleGrey40

@Composable
fun TextContent(
    content: String,
    color: Color = PurpleGrey40,
    textAlign: TextAlign = TextAlign.Left,
    modifier: Modifier = Modifier
) {
    Text(
        text = content,
        modifier = modifier,
        color = color,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        textAlign = textAlign
    )
}
