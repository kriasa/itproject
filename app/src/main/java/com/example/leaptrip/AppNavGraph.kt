package com.example.leaptrip

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.leaptrip.screens.*
import com.example.leaptrip.viewmodel.FlightViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    val flightViewModel: FlightViewModel = viewModel() // создаём один экземпляр ViewModel

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onRegisterSuccess = {
                    navController.navigate("main") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        composable("main") {
            MainScreen(appNavController = navController, flightViewModel = flightViewModel)
        }

        composable("results") {
            FlightResultsScreen(navController = navController, flightViewModel = flightViewModel)
        }
    }
}
