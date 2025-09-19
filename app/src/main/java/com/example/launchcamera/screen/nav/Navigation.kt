package com.example.launchcamera.screen.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.launchcamera.screen.camera.CAMERA_SCANNER_ROUTE
import com.example.launchcamera.screen.camera.CameraScannerScreen
import com.example.launchcamera.screen.camera.viewModel.CameraScannerViewModel
import com.example.launchcamera.screen.login.LOGIN_ROUTE
import com.example.launchcamera.screen.login.LoginScreen
import com.example.launchcamera.screen.login.viewModel.LoginViewModel
import com.example.launchcamera.screen.profile.PROFILE_ID_ARGUMENT
import com.example.launchcamera.screen.profile.PROFILE_ROUTE
import com.example.launchcamera.screen.profile.ProfileScreen
import com.example.launchcamera.screen.profile.viewModel.ProfileViewModel
import com.example.launchcamera.screen.register.REGISTER_ROUTE
import com.example.launchcamera.screen.register.RegisterScreen
import com.example.launchcamera.screen.register.USER_ID_ARGUMENT
import com.example.launchcamera.screen.register.viewModel.RegisterViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LOGIN_ROUTE) {
        composable(route = LOGIN_ROUTE) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = loginViewModel,
                onLoginClicked = {
                    navController.navigate("$PROFILE_ROUTE/$it") {
                        popUpTo(0)
                    }
                },
                onRegisterClicked = {
                    navController.navigate(CAMERA_SCANNER_ROUTE)
                }
            )
        }

        composable(route = CAMERA_SCANNER_ROUTE) {
            val cameraScannerViewModel: CameraScannerViewModel = hiltViewModel()
            CameraScannerScreen(
                viewModel = cameraScannerViewModel,
                onSuccessScanner = { userId ->
                    navController.navigate("$REGISTER_ROUTE/$userId")
                }
            )
        }

        composable(
            route = "$REGISTER_ROUTE/{$USER_ID_ARGUMENT}",
            arguments = listOf(navArgument(
                name= USER_ID_ARGUMENT
            ) {
                type = NavType.StringType
            })
        ) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = registerViewModel,
                onNavProfile = {
                    navController.navigate(LOGIN_ROUTE)
                }
            )
        }

        composable(
            route = "$PROFILE_ROUTE/{$PROFILE_ID_ARGUMENT}",
            arguments = listOf(navArgument(
                name = PROFILE_ID_ARGUMENT
            ){
                type = NavType.StringType
            })
        ) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = viewModel,
                onLogoutClicked = {
                    navController.navigate(LOGIN_ROUTE) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}