package com.example.launchcamera.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.launchcamera.screen.viewModel.LoginViewModel
import com.example.launchcamera.screen.viewModel.RegisterViewModel

@Composable
fun Navigation() {
    val backStack = rememberNavBackStack(LoginScreenKey)
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<LoginScreenKey> {
                val loginViewModel: LoginViewModel = hiltViewModel()
                LoginScreen(
                    viewModel = loginViewModel,
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
                val registerViewModel: RegisterViewModel = hiltViewModel()
                RegisterScreen(
                    viewModel = registerViewModel
                ) {
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