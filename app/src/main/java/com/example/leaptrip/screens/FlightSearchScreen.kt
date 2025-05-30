package com.example.leaptrip.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun FlightSearchScreen(
    navController: NavController
) {
    val viewModel: FlightViewModel = viewModel()

    var fromCity by remember { mutableStateOf("") }
    var toCity by remember { mutableStateOf("") }
    var departureDate by remember { mutableStateOf("") }
    var returnDate by remember { mutableStateOf("") }

    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
            label = { Text("Дата отправления") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = returnDate,
            onValueChange = { returnDate = it },
            label = { Text("Дата возвращения") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                Log.d("FlightSearchScreen", "Кнопка нажата")
                viewModel.searchFlights(fromCity, toCity, departureDate, returnDate)
                navController.navigate("results")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Найти рейсы")
        }

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}





