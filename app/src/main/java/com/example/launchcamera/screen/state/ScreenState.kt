package com.example.launchcamera.screen.state

sealed class ScreenState {
    object Idle : ScreenState()
    object Loading : ScreenState()
    object Success : ScreenState()
    object Error : ScreenState()
}