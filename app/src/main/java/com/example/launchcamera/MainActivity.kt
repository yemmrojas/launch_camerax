package com.example.launchcamera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.launchcamera.screen.LoginScreen
import com.example.launchcamera.ui.theme.LaunchCameraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchCameraTheme {
                LoginScreen()
            }
        }
    }
}
