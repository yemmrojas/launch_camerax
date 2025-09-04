package com.example.launchcamera.screen

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

@Composable
fun Navigation() {
    val backStack = rememberNavBackStack(LoginScreenKey)
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<LoginScreenKey> {
                LoginScreen(
                    onLoginClicked = {
                        backStack.clear()
                        backStack.add(ProfileScreenKey)
                    },
                    onRegisterClicked = {
                        backStack.add(CameraScannerScreenKey)
                    }
                )
            }
            entry<CameraScannerScreenKey> {
                CameraScannerScreen {
                    backStack.add(RegisterScreenKey)
                }
            }
            entry<RegisterScreenKey> {
                RegisterScreen {
                    backStack.clear()
                    backStack.add(LoginScreenKey)
                }
            }
            entry<ProfileScreenKey> {
                ProfileScreen() {
                    backStack.clear()
                    backStack.add(LoginScreenKey)
                }
            }
        }
    )
}