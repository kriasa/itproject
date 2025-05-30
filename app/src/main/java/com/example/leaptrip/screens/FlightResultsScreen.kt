@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.leaptrip.screens

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.leaptrip.network.FlightResponse
import androidx.compose.ui.res.painterResource
import com.example.leaptrip.R


@Composable
fun FlightResultsScreen(
    navController: NavController,
    viewModel: FlightViewModel = viewModel() // Важно передавать тот же ViewModel!
) {
    val flights by viewModel.flights.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Найденные рейсы") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back_icon),
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        when {
            error != null -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ошибка: $error", color = MaterialTheme.colorScheme.error)
                }
            }

            flights.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Рейсы не найдены")
                }
            }

            else -> {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(flights) { flight ->
                        FlightItem(flight)
                    }
                }
            }
        }
    }
}

@Composable
fun FlightItem(flight: FlightResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Авиакомпания: ${flight.airline}", style = MaterialTheme.typography.titleMedium)
            Text("Номер рейса: ${flight.flight_number}")
            Text("Отправление: ${flight.departure_at}")
            Text("Возвращение: ${flight.return_at ?: "—"}")
            Text("Цена: ${flight.price} руб.")
            Text("Пересадки: ${flight.transfers}")
            Text("Длительность: ${flight.duration}")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Ссылка для бронирования:", style = MaterialTheme.typography.labelSmall)
            Text(flight.booking_url, style = MaterialTheme.typography.bodySmall)
        }
    }
}



