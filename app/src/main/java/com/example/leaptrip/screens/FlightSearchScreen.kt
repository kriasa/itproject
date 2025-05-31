package com.example.leaptrip.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun FlightSearchScreen(navController: NavController) {
    val viewModel: FlightViewModel = viewModel()

    var fromCity by remember { mutableStateOf("") }
    var toCity by remember { mutableStateOf("") }
    var departureDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }

    val flights by viewModel.flights.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Обработка успешного поиска
    LaunchedEffect(flights) {
        if (flights.isNotEmpty()) {
            navController.navigate("results") {
                popUpTo("search") { inclusive = false }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = fromCity,
            onValueChange = { fromCity = it },
            label = { Text("Город отправления") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = toCity,
            onValueChange = { toCity = it },
            label = { Text("Город назначения") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = departureDate,
            onValueChange = { departureDate = it },
            label = { Text("Дата отправления (ГГГГ-ММ-ДД)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = returnDate,
            onValueChange = { returnDate = it },
            label = { Text("Дата возвращения (ГГГГ-ММ-ДД)") },
            modifier = Modifier.fillMaxWidth()
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

        Button(
            onClick = {
                if (fromCity.isNotBlank() && toCity.isNotBlank() && departureDate.isNotBlank()) {
                    viewModel.searchFlights(
                        fromCity = fromCity,
                        toCity = toCity,
                        departureDate = departureDate,
                        returnDate = if (returnDate.isNotBlank()) returnDate else null
                    )
                } else {
                    viewModel.setError("Заполните обязательные поля (города и дату отправления)")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isLoading
        ) {
            Text("Найти рейсы", style = MaterialTheme.typography.labelLarge)
        }
    }
}







