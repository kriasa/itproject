package com.example.leaptrip.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.leaptrip.viewmodel.FlightViewModel

@Composable
fun FlightResultsScreen(navController: NavHostController, flightViewModel: FlightViewModel) {
    val flights by flightViewModel.flights.collectAsState()
    val error by flightViewModel.error.collectAsState()
    val isLoading by flightViewModel.isLoading.collectAsState()

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            error?.contains("404") == true || (error != null && error.equals("Рейсы не найдены", true)) -> {
                Text(
                    text = "Рейсы не найдены",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            error != null -> {
                Text(
                    text = error ?: "Ошибка поиска",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            flights.isEmpty() -> {
                Text(
                    text = "Рейсы не найдены",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(flights) { flight ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Авиакомпания: ${flight.airline}")
                                Text("Рейс: ${flight.flight_number}")
                                Text("Отправление: ${flight.departure_at}")
                                Text("Возвращение: ${flight.return_at ?: "-"}")
                                Text("Цена: ${flight.price} ₽")
                            }
                        }
                    }
                }
            }
        }
    }
}
