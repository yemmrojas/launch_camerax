package com.example.launchcamera.screen.shield.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info

fun IconType.iconShield() = when(this) {
    IconType.INFO -> Icons.Default.Info
    IconType.SUCCESS -> Icons.Default.CheckCircle
    IconType.ERROR -> Icons.Default.Close
}