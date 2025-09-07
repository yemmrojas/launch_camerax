package com.example.launchcamera.screen.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.launchcamera.screen.camera.CameraScannerScreen
import com.example.launchcamera.screen.camera.CameraScannerScreenKey
import com.example.launchcamera.screen.camera.viewModel.CameraScannerViewModel
import com.example.launchcamera.screen.login.LoginScreen
import com.example.launchcamera.screen.login.LoginScreenKey
import com.example.launchcamera.screen.login.viewModel.LoginViewModel
import com.example.launchcamera.screen.profile.ProfileScreen
import com.example.launchcamera.screen.profile.ProfileScreenKey
import com.example.launchcamera.screen.register.RegisterScreen
import com.example.launchcamera.screen.register.RegisterScreenKey
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel

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
                val cameraScannerViewModel: CameraScannerViewModel = hiltViewModel()
                CameraScannerScreen(cameraScannerViewModel) {
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