package com.example.leaptrip.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.leaptrip.viewmodel.FlightViewModel
import com.example.leaptrip.viewmodel.HotelViewModel

@Composable
fun MainScreen(
    appNavController: NavHostController,
    flightViewModel: FlightViewModel = viewModel(),
    hotelViewModel: HotelViewModel = viewModel()
) {
    val bottomNavController = rememberNavController()
    val currentBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = currentRoute == "flightSearch",
                    onClick = {
                        bottomNavController.navigate("flightSearch") {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(bottomNavController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    },
                    label = { Text("Авиабилеты") },
                    icon = { Text("✈️") }
                )
                NavigationBarItem(
                    selected = currentRoute == "hotels",
                    onClick = {
                        bottomNavController.navigate("hotels") {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(bottomNavController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    },
                    label = { Text("Отели") },
                    icon = { Text("🏨") }
                )
                NavigationBarItem(
                    selected = currentRoute == "account",
                    onClick = {
                        bottomNavController.navigate("account") {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(bottomNavController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    },
                    label = { Text("Аккаунт") },
                    icon = { Text("👤") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        val telegramBotUrl = "https://t.me/NihuhuhuBot"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramBotUrl))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    },
                    label = { Text("Чат") },
                    icon = { Text("💬") }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = bottomNavController, startDestination = "flightSearch") {

                composable("flightSearch") {
                    FlightSearchScreen(
                        navController = appNavController,
                        flightViewModel = flightViewModel
                    )
                }
                composable("flightResults") {
                    FlightResultsScreen(
                        navController = appNavController,
                        flightViewModel = flightViewModel
                    )
                }

                composable("hotels") {
                    HotelSearchScreen(
                        navController = appNavController,
                        hotelViewModel = hotelViewModel
                    )
                }
                composable("hotelResults") {
                    HotelResultsScreen(
                        navController = appNavController,
                        hotelViewModel = hotelViewModel
                    )
                }

                composable("account") {
                    AccountScreen(navController = appNavController)
                }
            }
        }
    }
}
