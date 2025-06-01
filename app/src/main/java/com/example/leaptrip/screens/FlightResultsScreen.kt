package com.example.leaptrip.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.leaptrip.viewmodel.FlightViewModel

@Composable
fun FlightResultsScreen(navController: NavHostController, flightViewModel: FlightViewModel) {
    val flights by flightViewModel.flights.collectAsState()
    val error by flightViewModel.error.collectAsState()
    val isLoading by flightViewModel.isLoading.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text(text = error ?: "Ошибка поиска", color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn {
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
